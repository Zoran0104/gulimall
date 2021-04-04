package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.ProductAttrValueDao;
import com.zoran.gulimallproduct.entity.ProductAttrValueEntity;
import com.zoran.gulimallproduct.service.AttrService;
import com.zoran.gulimallproduct.service.ProductAttrValueService;
import com.zoran.gulimallproduct.vo.BaseAttrs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("productAttrValueService")
@AllArgsConstructor
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    private final AttrService attrService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttr(Long id, List<BaseAttrs> baseAttrs) {
        List<ProductAttrValueEntity> list = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity entity = new ProductAttrValueEntity();
            entity.setAttrId(attr.getAttrId());
            entity.setAttrName(attrService.getById(attr.getAttrId()).getAttrName());
            entity.setAttrValue(attr.getAttrValues());
            entity.setQuickShow(attr.getShowDesc());
            entity.setSpuId(id);
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(list);
    }

}
