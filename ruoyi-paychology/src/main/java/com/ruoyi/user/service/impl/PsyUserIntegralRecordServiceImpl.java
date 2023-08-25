package com.ruoyi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.PsyConstants;
import com.ruoyi.common.constant.IntegralRecordConstants;
import com.ruoyi.common.constant.NewConstants;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.NewDateUtil;
import com.ruoyi.common.vo.DateLimitUtilVO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
import com.ruoyi.user.mapper.PsyUserIntegralRecordMapper;
import com.ruoyi.user.request.IntegralSearchReq;
import com.ruoyi.user.service.IPsyUserIntegralRecordService;
import com.ruoyi.user.vo.PsyUserIntegralRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用户积分记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-10
 */
@Service
public class PsyUserIntegralRecordServiceImpl extends ServiceImpl<PsyUserIntegralRecordMapper, PsyUserIntegralRecord> implements IPsyUserIntegralRecordService
{

    @Resource
    private AppTokenService appTokenService;

    @Resource
    private ISysConfigService configService;

    @Resource
    private IPsyUserService psyUserService;

    @Resource
    private PsyUserIntegralRecordMapper psyUserIntegralRecordMapper;

    @Override
    public List<PsyUserIntegralRecordVO> getList(IntegralSearchReq req) {
        if (StringUtils.isNotBlank(req.getDateLimit())) {
            DateLimitUtilVO dateLimit = NewDateUtil.getDateLimit(req.getDateLimit());
            int compareDateResult = NewDateUtil.compareDate(dateLimit.getEndTime(), dateLimit.getStartTime(), NewConstants.DATE_FORMAT);
            if (compareDateResult == -1) {
                throw new UtilException("开始时间不能大于结束时间！");
            }
            req.setStartTime(dateLimit.getStartTime());
            req.setEndTime(dateLimit.getEndTime());
        }

        req.setDelFlag(0);
        req.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
        return psyUserIntegralRecordMapper.getList(req);
    }

    @Override
    public Integer getUserIntegral(HttpServletRequest request) {
        Integer id = appTokenService.getUserId(request);
        if (id == -1) {
            return 0;
        }

        PsyUser psyUser = psyUserService.selectPsyUserById(id);
        return psyUser.getIntegral();
    }

    @Override
    public int getIntegral(BigDecimal amount, Integer type) {
        int integral = 0;
        String give = configService.selectConfigByKey("integral.give");
        // 未开启功能,返回0
        if (StringUtils.isEmpty(give) || "2".equals(give)) {
            return integral;
        }

        // 注册送积分
        if (IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_REGISTER.equals(type)) {
            String register = configService.selectConfigByKey("integral.give.register");
            integral = Math.min(Integer.parseInt(register), 500);
            return integral;
        }

        // 支付送积分
        String order = configService.selectConfigByKey("integral.give.order");
        if (StringUtils.isEmpty(order)) {
            return integral;
        }

        BigDecimal config = new BigDecimal(order);
        BigDecimal rate = new BigDecimal(PsyConstants.INTEGRAL_RATE_ORDER_GIVE);
        rate = config.compareTo(rate) >= 0 ? rate : config;

        integral = rate.multiply(amount).setScale(0, BigDecimal.ROUND_DOWN).intValue();
        return integral;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PsyUserIntegralRecord data) {
        System.out.println("PsyUserIntegralRecord");
        if (data.getIntegral() > 0 ) {
            PsyUser psyUser = psyUserService.selectPsyUserById(data.getUid());
            Integer integral = psyUser.getIntegral();
            integral = IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD.equals(data.getType()) ? integral + data.getIntegral() : integral - data.getIntegral();
            integral = Math.max(integral, 0);
            psyUser.setIntegral(integral);

            psyUserService.updatePsyUser(psyUser);
            psyUserIntegralRecordMapper.insert(data);
        }
    }











}
