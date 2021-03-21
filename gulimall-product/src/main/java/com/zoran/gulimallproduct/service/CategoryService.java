package com.zoran.gulimallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallproduct.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 00:03:52
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> getTreeList();

    void removeMenuByIds(List<Long> asList);
}

