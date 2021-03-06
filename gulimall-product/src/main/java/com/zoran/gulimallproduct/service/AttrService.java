package com.zoran.gulimallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallproduct.entity.AttrEntity;
import com.zoran.gulimallproduct.vo.AttrRespVo;
import com.zoran.gulimallproduct.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 00:03:52
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    PageUtils getNotRelationAttr(Long attrGroupId, Map<String, Object> params);

    List<Long> selectSearchAttrs(List<Long> attrIds);
}

