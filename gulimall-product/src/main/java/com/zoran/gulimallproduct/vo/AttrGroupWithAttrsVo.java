package com.zoran.gulimallproduct.vo;

import com.zoran.gulimallproduct.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author ：zoran
 * @date ：Created in 2021/3/27 22:14
 * @description：
 * @modified By：
 * @version:
 */
@Data
public class AttrGroupWithAttrsVo {
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<AttrEntity> attrs;
}
