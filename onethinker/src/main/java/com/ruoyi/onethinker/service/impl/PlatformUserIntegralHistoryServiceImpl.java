package com.ruoyi.onethinker.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;

import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.onethinker.mapper.PlatformUserIntegralHistoryMapper;
import com.ruoyi.onethinker.domain.PlatformUserIntegralHistory;
import com.ruoyi.onethinker.service.IPlatformUserIntegralHistoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

/**
 * 平台用户积分流水记录Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Service
@Log4j2
public class PlatformUserIntegralHistoryServiceImpl implements IPlatformUserIntegralHistoryService {
    @Autowired
    private PlatformUserIntegralHistoryMapper platformUserIntegralHistoryMapper;

    /**
     * 查询平台用户积分流水记录列表
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 平台用户积分流水记录
     */
    @Override
    public List<PlatformUserIntegralHistory> selectPlatformUserIntegralHistoryList(PlatformUserIntegralHistory platformUserIntegralHistory) {
        return platformUserIntegralHistoryMapper.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
    }

    /**
     * 新增平台用户积分流水记录
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 结果
     */
    @Override
    public int insertPlatformUserIntegralHistory(PlatformUserIntegralHistory platformUserIntegralHistory) {
        platformUserIntegralHistory.setCreateTime(DateUtils.getNowDate());
        if (ObjectUtils.isEmpty(platformUserIntegralHistory.getEnabled())) {
            platformUserIntegralHistory.setEnabled(PlatformUserIntegralHistory.STATE_TYPE_ENABLED);
        }
        platformUserIntegralHistory.setSysUserId(SecurityUtils.getUserId());
        return platformUserIntegralHistoryMapper.insertPlatformUserIntegralHistory(platformUserIntegralHistory);
    }

    /**
     * 修改平台用户积分流水记录
     *
     * @param platformUserIntegralHistory 平台用户积分流水记录
     * @return 结果
     */
    @Override
    public int updatePlatformUserIntegralHistory(PlatformUserIntegralHistory platformUserIntegralHistory) {
        platformUserIntegralHistory.setUpdateTime(DateUtils.getNowDate());
        return platformUserIntegralHistoryMapper.updatePlatformUserIntegralHistory(platformUserIntegralHistory);
    }
}
