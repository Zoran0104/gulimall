package com.zoran.gulimallorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallorder.entity.PmsBrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 10:50:28
 */
public interface PmsBrandService extends IService<PmsBrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

