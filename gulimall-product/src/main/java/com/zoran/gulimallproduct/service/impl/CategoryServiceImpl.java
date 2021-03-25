package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.CategoryDao;
import com.zoran.gulimallproduct.entity.CategoryEntity;
import com.zoran.gulimallproduct.service.CategoryBrandRelationService;
import com.zoran.gulimallproduct.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("categoryService")
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    private CategoryBrandRelationService categoryBrandRelationService;
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

    private List<CategoryEntity> getSubTree(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .filter(entity -> entity.getParentCid().equals(categoryEntity.getCatId()))
                .peek(entity -> entity.setSubTree(getSubTree(entity, categoryEntities)))
                .sorted(Comparator.comparingInt(m -> Optional.ofNullable(m.getSort()).orElse(0)))
                .collect(Collectors.toList());
    }
}
