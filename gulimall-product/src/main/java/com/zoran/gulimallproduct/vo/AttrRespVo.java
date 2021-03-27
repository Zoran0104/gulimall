package com.zoran.gulimallproduct.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zoran
 * @date ：Created in 2021/3/27 12:03
 * @description：
 * @modified By：
 * @version:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttrRespVo extends AttrVo {
    private String catelogName;
    private String groupName;
}
