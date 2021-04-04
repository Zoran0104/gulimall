package com.zoran.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.dto.SkuReductionDto;
import com.zoran.common.dto.SpuBoundDto;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallproduct.dao.SpuInfoDao;
import com.zoran.gulimallproduct.entity.*;
import com.zoran.gulimallproduct.feign.CouponFeignService;
import com.zoran.gulimallproduct.service.*;
import com.zoran.gulimallproduct.vo.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
@AllArgsConstructor
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    private final SpuInfoDescService spuInfoDescService;
    private final SpuImagesService imagesService;
    private final ProductAttrValueService productAttrValueService;
    private final SkuInfoService skuInfoService;
    private final SkuImagesService skuImagesService;
    private final SkuSaleAttrValueService skuSaleAttrValueService;
    private final CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.save(spuInfoDescEntity);
        List<String> images = vo.getImages();
        imagesService.saveImages(spuInfoEntity.getId(), images);
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        productAttrValueService.saveProductAttr(spuInfoEntity.getId(), baseAttrs);

        Bounds bounds = vo.getBounds();
        SpuBoundDto spuBoundDto = new SpuBoundDto();
        BeanUtils.copyProperties(bounds, spuBoundDto);
        spuBoundDto.setSpuId(spuInfoEntity.getId());
        couponFeignService.saveSpuBounds(spuBoundDto);

        List<Skus> skus = vo.getSkus();
        if (!CollectionUtils.isEmpty(skus)) {
            skus.forEach(item -> {
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity entity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, entity);
                entity.setBrandId(spuInfoEntity.getBrandId());
                entity.setCatalogId(spuInfoEntity.getCatalogId());
                entity.setSaleCount(0L);
                entity.setSpuId(spuInfoEntity.getId());
                entity.setSkuDefaultImg(defaultImg);
                skuInfoService.save(entity);
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity imagesEntity = new SkuImagesEntity();
                    imagesEntity.setSkuId(entity.getSkuId());
                    imagesEntity.setImgUrl(img.getImgUrl());
                    imagesEntity.setDefaultImg(img.getDefaultImg());
                    return imagesEntity;
                }).filter(image -> !StringUtils.isEmpty(image.getImgUrl())).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);


                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(item2 -> {
                    SkuSaleAttrValueEntity saleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(item2, saleAttrValueEntity);
                    saleAttrValueEntity.setSkuId(entity.getSkuId());
                    return saleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                SkuReductionDto skuReductionDto = new SkuReductionDto();
                BeanUtils.copyProperties(item, skuReductionDto);
                skuReductionDto.setSkuId(entity.getSkuId());
                if (skuReductionDto.getFullCount() > 0 || skuReductionDto.getFullPrice().compareTo(new BigDecimal("0")) > 0) {
                    couponFeignService.saveSkuReduction(skuReductionDto);
                }


            });
        }
    }

}
