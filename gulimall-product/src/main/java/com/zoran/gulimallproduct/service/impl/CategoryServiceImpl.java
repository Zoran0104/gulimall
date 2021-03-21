package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.CategoryDao;
import com.zoran.gulimallproduct.entity.CategoryEntity;
import com.zoran.gulimallproduct.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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

    private List<CategoryEntity> getSubTree(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .filter(entity -> entity.getParentCid().equals(categoryEntity.getCatId()))
                .peek(entity -> entity.setSubTree(getSubTree(entity, categoryEntities)))
                .sorted(Comparator.comparingInt(m -> Optional.ofNullable(m.getSort()).orElse(0)))
                .collect(Collectors.toList());
    }
}
