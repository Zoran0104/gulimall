package com.zoran.gulimallcoupon.dao;

import com.zoran.gulimallcoupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:26:41
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
