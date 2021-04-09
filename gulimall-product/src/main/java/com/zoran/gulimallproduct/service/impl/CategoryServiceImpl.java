package com.zoran.gulimallproduct.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.CategoryDao;
import com.zoran.gulimallproduct.entity.CategoryEntity;
import com.zoran.gulimallproduct.service.CategoryBrandRelationService;
import com.zoran.gulimallproduct.service.CategoryService;
import com.zoran.gulimallproduct.vo.Catalog2Vo;
import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("categoryService")
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    private final CategoryBrandRelationService categoryBrandRelationService;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> getTreeList() {
        List<CategoryEntity> categoryEntities = list();
        return categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .peek(categoryEntity ->
                        categoryEntity.setSubTree(getSubTree(categoryEntity, categoryEntities)))
                .sorted(Comparator.comparingInt(m -> Optional.ofNullable(m.getSort()).orElse(0)))
                .collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //todo 判断是否有子菜单，有的话不允许删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> path = new ArrayList<>();
        CategoryEntity categoryEntity = this.getById(catelogId);
        path.add(categoryEntity.getCatId());
        while (categoryEntity.getParentCid() != 0) {
            categoryEntity = this.getById(categoryEntity.getParentCid());
            path.add(categoryEntity.getCatId());
        }
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if (!StringUtils.isEmpty(category.getName().isEmpty())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Override
    @Cacheable(value = "catagory",key = "'level1Catalog'")
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            return getCatalogJsonWithRedisLock();
        }
        return JSONUtil.toBean(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        }, true);
    }

    public Map<String, List<Catalog2Vo>> getCatalogJsonWithRedissonLock() {
        RLock lock = redissonClient.getLock("catalogJson-lock");
        lock.lock();
        try {
            return getCatelogFromDb();
        }finally {
            lock.unlock();
        }
    }

    @SuppressWarnings({"AliControlFlowStatementWithoutBraces", "StatementWithEmptyBody"})
    public Map<String, List<Catalog2Vo>> getCatalogJsonWithRedisLock() {
        String lock = UUID.fastUUID().toString(true);
        while (!Optional.ofNullable(stringRedisTemplate.opsForValue().setIfAbsent("lock", lock, 10, TimeUnit.SECONDS))
                .orElse(false)) ;
        Map<String, List<Catalog2Vo>> catelogFromDb = getCatelogFromDb();
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        stringRedisTemplate.execute(new DefaultRedisScript<>(script), Arrays.asList("lock"), lock);
        return catelogFromDb;
    }

    private Map<String, List<Catalog2Vo>> getCatelogFromDb() {
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        if (StringUtils.isEmpty(catalogJson)) {
            System.out.println("进行查询数据库");
            List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
            Map<String, List<CategoryEntity>> map = new HashMap<>(100);
            categoryEntities.forEach(item -> {
                if (!map.containsKey(item.getParentCid().toString())) {
                    map.put(item.getParentCid().toString(), new ArrayList<>());
                }
                map.get(item.getParentCid().toString()).add(item);
            });
            Map<String, List<Catalog2Vo>> collect = categoryEntities.stream().filter(item -> item.getParentCid() == 0)
                    .collect(Collectors.toMap(item -> item.getCatId().toString(),
                            item -> map.get(item.getCatId().toString()).stream()
                                    .map(l2 -> new Catalog2Vo(item.getCatId().toString(), l2.getCatId().toString(), l2
                                            .getName(),
                                            map.get(l2.getCatId().toString()).stream()
                                                    .map(l3 -> new Catalog2Vo.Catalog3Vo(l2.getCatId().toString(), l3
                                                            .getCatId().toString(), l3.getName()))
                                                    .collect(Collectors.toList()))).collect(Collectors.toList())));
            stringRedisTemplate.opsForValue()
                    .set("catalogJson", JSONUtil.toJsonStr(collect), 1, TimeUnit.DAYS);
        }
        return JSONUtil.toBean(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        }, true);
    }

    public synchronized Map<String, List<Catalog2Vo>> getCatelogJsonFromDbLocalLock() {
        return getCatelogFromDb();
    }

    private List<CategoryEntity> getSubTree(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .filter(entity -> entity.getParentCid().equals(categoryEntity.getCatId()))
                .peek(entity -> entity.setSubTree(getSubTree(entity, categoryEntities)))
                .sorted(Comparator.comparingInt(m -> Optional.ofNullable(m.getSort()).orElse(0)))
                .collect(Collectors.toList());
    }
}
