package com.zoran.gulimallorder.controller;

import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.R;
import com.zoran.gulimallorder.entity.PmsAttrAttrgroupRelationEntity;
import com.zoran.gulimallorder.service.PmsAttrAttrgroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 属性&属性分组关联
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 10:50:28
 */
@RestController
@RequestMapping("gulimallorder/pmsattrattrgrouprelation")
public class PmsAttrAttrgroupRelationController {
    @Autowired
    private PmsAttrAttrgroupRelationService pmsAttrAttrgroupRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
        public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmsAttrAttrgroupRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
        public R info(@PathVariable("id") Long id){
		PmsAttrAttrgroupRelationEntity pmsAttrAttrgroupRelation = pmsAttrAttrgroupRelationService.getById(id);

        return R.ok().put("pmsAttrAttrgroupRelation", pmsAttrAttrgroupRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
        public R save(@RequestBody PmsAttrAttrgroupRelationEntity pmsAttrAttrgroupRelation){
		pmsAttrAttrgroupRelationService.save(pmsAttrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
        public R update(@RequestBody PmsAttrAttrgroupRelationEntity pmsAttrAttrgroupRelation){
		pmsAttrAttrgroupRelationService.updateById(pmsAttrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
        public R delete(@RequestBody Long[] ids){
		pmsAttrAttrgroupRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
