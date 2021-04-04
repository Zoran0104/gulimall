package com.zoran.gulimallproduct.controller;

import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.R;
import com.zoran.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.zoran.gulimallproduct.entity.AttrEntity;
import com.zoran.gulimallproduct.entity.AttrGroupEntity;
import com.zoran.gulimallproduct.service.AttrAttrgroupRelationService;
import com.zoran.gulimallproduct.service.AttrGroupService;
import com.zoran.gulimallproduct.service.AttrService;
import com.zoran.gulimallproduct.service.CategoryService;
import com.zoran.gulimallproduct.vo.AttrGroupWithAttrsVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 00:03:52
 */
@RestController
@RequestMapping("product/attrgroup")
@AllArgsConstructor
public class AttrGroupController {
    private final AttrGroupService attrGroupService;

    private final CategoryService categoryService;

    private final AttrService attrService;

    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    @GetMapping("/{attrGroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId) {
        List<AttrEntity> attrEntities = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data", attrEntities);
    }

    @GetMapping("/{attrGroupId}/noattr/relation")
    public R attrNotRelation(@PathVariable("attrGroupId") Long attrGroupId, @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNotRelationAttr(attrGroupId, params);
        return R.ok().put("data", page);
    }

    @PostMapping("/attr/deleteRelation")
    public R deleteRelation(@RequestBody AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities) {
        attrGroupService.deleteRelation(attrAttrgroupRelationEntities);
        return R.ok();
    }

    @PostMapping("/attr/addRelation")
    public R addRelation(@RequestBody List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities) {
        attrAttrgroupRelationService.saveBatch(attrAttrgroupRelationEntities);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catalogId) {

        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catalogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatalogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data", vos);
    }
}
