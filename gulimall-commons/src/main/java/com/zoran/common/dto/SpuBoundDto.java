package com.zoran.common.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/4 15:17
 * @description：
 * @modified By：
 * @version:
 */
@Data
public class SpuBoundDto {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
