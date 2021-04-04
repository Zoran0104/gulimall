package com.zoran.gulimallproduct.vo;

import com.zoran.gulimallproduct.entity.AttrEntity;
import com.zoran.gulimallproduct.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrVo extends AttrGroupEntity {
    private List<AttrEntity> attrs;
}
