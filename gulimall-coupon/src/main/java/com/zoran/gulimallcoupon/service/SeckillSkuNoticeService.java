package com.zoran.gulimallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallcoupon.entity.SeckillSkuNoticeEntity;

import java.util.Map;

/**
 * 秒杀商品通知订阅
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:26:40
 */
public interface SeckillSkuNoticeService extends IService<SeckillSkuNoticeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

