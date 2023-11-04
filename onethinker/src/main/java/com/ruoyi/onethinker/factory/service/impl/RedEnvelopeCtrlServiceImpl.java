package com.ruoyi.onethinker.factory.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;
import com.ruoyi.onethinker.dto.ActivityReqDTO;
import com.ruoyi.onethinker.dto.RedEnvelopeCtrlDTO;
import com.ruoyi.onethinker.factory.service.IActivityDetailService;
import com.ruoyi.onethinker.mapper.RedEnvelopeCtrlMapper;
import lombok.extern.log4j.Log4j2;

/**
 * 红包批控制Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class RedEnvelopeCtrlServiceImpl implements IActivityDetailService {
    @Resource
    private RedEnvelopeCtrlMapper redEnvelopeCtrlMapper;

    @Override
    public int saveEntry(ActivityReqDTO activityReqDTO) {
        // 校验入参明细是否有效
        activityReqDTO.getRedEnvelopeCtrlDTO().existsReqParams();
        // 入批控表
        RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = activityReqDTO.getRedEnvelopeCtrlDTO();
        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(redEnvelopeCtrlDTO, redEnvelopeCtrl);
        redEnvelopeCtrl.setCreateTime(new Date());
        redEnvelopeCtrl.setWeight(System.currentTimeMillis());
        int i = redEnvelopeCtrlMapper.insertRedEnvelopeCtrl(redEnvelopeCtrl);
        return i;
    }

    @Override
    public List<RedEnvelopeCtrlDTO> queryRedEnvelopeCtrlByParams(Object reqDTO) {
        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(reqDTO, redEnvelopeCtrl);
        List<RedEnvelopeCtrl> redEnvelopeCtrls = redEnvelopeCtrlMapper.selectRedEnvelopeCtrlList(redEnvelopeCtrl);
        if (ObjectUtils.isEmpty(redEnvelopeCtrls) || redEnvelopeCtrls.isEmpty()) {
            return Lists.newArrayList();
        }
        return redEnvelopeCtrls.stream().map(e -> {
            RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = new RedEnvelopeCtrlDTO();
            BeanUtils.copyProperties(e, redEnvelopeCtrlDTO);
            return redEnvelopeCtrlDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public int updateEntry(Object doTemp, Integer createQrCodeStatus) {
        RedEnvelopeCtrlDTO redEnvelopeCtrl = (RedEnvelopeCtrlDTO) doTemp;
        redEnvelopeCtrl.setOrgStatus(redEnvelopeCtrl.getStatus());
        redEnvelopeCtrl.setStatus(createQrCodeStatus);
        return redEnvelopeCtrlMapper.updateRedEnvelopeCtrlAndStatus(redEnvelopeCtrl);
    }
}
