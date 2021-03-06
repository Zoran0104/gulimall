package com.zoran.gulimallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallcoupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:26:40
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

