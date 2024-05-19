package com.onethinker.user.service.impl;

import com.onethinker.user.domain.PlatformUserIntegralHistory;
import com.onethinker.user.mapper.PlatformUserIntegralHistoryMapper;
import com.onethinker.user.service.IPlatformUserIntegralHistoryService;
import com.onethinker.common.enums.IntegralTypeEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台用户积分流水记录Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Service
@Log4j2
public class PlatformUserIntegralHistoryServiceImpl implements IPlatformUserIntegralHistoryService {
    @Resource
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

    @Override
    public List<PlatformUserIntegralHistory> withdrawalIntegralList(PlatformUserIntegralHistory platformUserIntegralHistory) {
        platformUserIntegralHistory.setType(IntegralTypeEnum.CASH_WITHDRAWAL.getCode());
        return platformUserIntegralHistoryMapper.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
    }

    @Override
    public int updateWithdrawalIntegral(PlatformUserIntegralHistory platformUserIntegralHistory) {
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserIntegralHistory.getId()), "参数缺失");
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserIntegralHistory.getEnabled()), "参数缺失");
        PlatformUserIntegralHistory saveParams = new PlatformUserIntegralHistory();
        saveParams.setSysUserId(SecurityUtils.getUserId());
        saveParams.setId(platformUserIntegralHistory.getId());
        saveParams.setEnabled(platformUserIntegralHistory.getEnabled());
        saveParams.setUpdateTime(platformUserIntegralHistory.getUpdateTime());
        saveParams.setUpdateBy(SecurityUtils.getUsername());
        if (!ObjectUtils.isEmpty(platformUserIntegralHistory.getRemark())) {
            saveParams.setRemark(platformUserIntegralHistory.getRemark());
        }
        return platformUserIntegralHistoryMapper.updatePlatformUserIntegralHistory(saveParams);
    }
}
