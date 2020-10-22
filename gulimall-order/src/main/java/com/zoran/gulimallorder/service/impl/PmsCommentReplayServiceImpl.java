package com.zoran.gulimallorder.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;

import com.zoran.gulimallorder.dao.PmsCommentReplayDao;
import com.zoran.gulimallorder.entity.PmsCommentReplayEntity;
import com.zoran.gulimallorder.service.PmsCommentReplayService;


@Service("pmsCommentReplayService")
public class PmsCommentReplayServiceImpl extends ServiceImpl<PmsCommentReplayDao, PmsCommentReplayEntity> implements PmsCommentReplayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsCommentReplayEntity> page = this.page(
                new Query<PmsCommentReplayEntity>().getPage(params),
                new QueryWrapper<PmsCommentReplayEntity>()
        );

        return new PageUtils(page);
    }

}