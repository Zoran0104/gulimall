package com.zoran.gulimallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.dto.SkuReductionDto;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallcoupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:26:40
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionDto skuReductionDto);
}

