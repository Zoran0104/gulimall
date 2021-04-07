package com.zoran.common.dto.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/5 14:54
 * @description：
 * @modified By：
 * @version:
 */
@Data
public class SkuEsModel {
    private Long skuId;
    private Long spuId;
    private String skuTitle;
    private BigDecimal skuPrice;
    private String skuImg;
    private Long saleCount;
    private Boolean hasStock;
    private Long hotScore;
    private Long brandId;
    private Long catalogId;
    private String brandName;
    private String brandImg;
    private String catalogName;
    private List<Attrs> attrs;

    public static class Attrs{
        private Long attrId;
        private String attrName;
        private String attrValue;
    }
}
