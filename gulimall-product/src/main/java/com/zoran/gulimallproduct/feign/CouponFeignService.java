package com.zoran.gulimallproduct.feign;

import com.zoran.common.dto.SkuReductionDto;
import com.zoran.common.dto.SpuBoundDto;
import com.zoran.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/4 15:12
 * @description：
 * @modified By：
 * @version:
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundDto spuBoundDto);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionDto skuReductionDto);
}
