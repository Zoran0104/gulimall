package com.zoran.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/4 15:54
 * @description：
 * @modified By：
 * @version:
 */
@Data
public class SkuReductionDto {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
