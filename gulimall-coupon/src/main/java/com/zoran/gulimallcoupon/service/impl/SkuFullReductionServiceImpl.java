package com.zoran.gulimallcoupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.dto.MemberPrice;
import com.zoran.common.dto.SkuReductionDto;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallcoupon.dao.SkuFullReductionDao;
import com.zoran.gulimallcoupon.entity.MemberPriceEntity;
import com.zoran.gulimallcoupon.entity.SkuFullReductionEntity;
import com.zoran.gulimallcoupon.entity.SkuLadderEntity;
import com.zoran.gulimallcoupon.service.MemberPriceService;
import com.zoran.gulimallcoupon.service.SkuFullReductionService;
import com.zoran.gulimallcoupon.service.SkuLadderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
@AllArgsConstructor
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    private final SkuLadderService skuLadderService;
    private final MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionDto skuReductionDto) {
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuReductionDto.getSkuId());
        skuLadderEntity.setFullCount(skuReductionDto.getFullCount());
        skuLadderEntity.setDiscount(skuReductionDto.getDiscount());
        skuLadderEntity.setAddOther(skuReductionDto.getCountStatus());
        if (skuReductionDto.getFullCount() > 0) {
            skuLadderService.save(skuLadderEntity);
        }


        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionDto, skuFullReductionEntity);
        if (skuFullReductionEntity.getFullPrice().compareTo(new BigDecimal("0")) > 0) {
            this.save(skuFullReductionEntity);
        }


        List<MemberPrice> memberPrice = skuReductionDto.getMemberPrice();
        List<MemberPriceEntity> collect = memberPrice.stream().map(item -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(skuReductionDto.getSkuId());
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(item -> item.getMemberPrice().compareTo(new BigDecimal("0")) > 0).collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }

}
