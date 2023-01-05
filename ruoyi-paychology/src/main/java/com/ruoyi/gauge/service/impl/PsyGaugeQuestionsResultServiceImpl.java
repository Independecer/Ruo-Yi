package com.ruoyi.gauge.service.impl;

import com.ruoyi.common.core.domain.dto.GaugeCommitResultDTO;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.gauge.domain.*;
import com.ruoyi.gauge.mapper.PsyGaugeQuestionsOptionsMapper;
import com.ruoyi.gauge.mapper.PsyGaugeQuestionsResultMapper;
import com.ruoyi.gauge.mapper.PsyGaugeScoreSettingMapper;
import com.ruoyi.gauge.mapper.PsyOrderMapper;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 心理测评问题结果Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Service
public class PsyGaugeQuestionsResultServiceImpl implements IPsyGaugeQuestionsResultService 
{
    @Resource
    private PsyGaugeQuestionsResultMapper psyGaugeQuestionsResultMapper;

    @Resource
    private PsyGaugeQuestionsOptionsMapper psyGaugeQuestionsOptionsMapper;

    @Resource
    private PsyOrderMapper psyOrderMapper;

    @Resource
    private PsyGaugeScoreSettingMapper psyGaugeScoreSettingMapper;

    /**
     * 查询心理测评问题结果
     * 
     * @param id 心理测评问题结果主键
     * @return 心理测评问题结果
     */
    @Override
    public PsyGaugeQuestionsResult selectPsyGaugeQuestionsResultById(Long id)
    {
        return psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultById(id);
    }

    /**
     * 查询心理测评问题结果列表
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 心理测评问题结果
     */
    @Override
    public List<PsyGaugeQuestionsResult> selectPsyGaugeQuestionsResultList(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        return psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultList(psyGaugeQuestionsResult);
    }

    /**
     * 新增心理测评问题结果
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int answer(PsyGaugeQuestionsResult psyGaugeQuestionsResult ,LoginDTO loginDTO)
    {
        //先删除该问题的答案
        psyGaugeQuestionsResult.setUserId(loginDTO.getUserId());
        psyGaugeQuestionsResultMapper.deleteResult(psyGaugeQuestionsResult);

        //查询问题分数，进行数据绑定，插入数据
        List<PsyGaugeQuestionsOptions> psyGaugeQuestionsOptions = psyGaugeQuestionsOptionsMapper.queryOptionsByIds(psyGaugeQuestionsResult.getQuestionsOptionsIdList());
        Map<Long, Long> collect = psyGaugeQuestionsOptions.stream().collect(Collectors.toMap(PsyGaugeQuestionsOptions::getId, PsyGaugeQuestionsOptions::getValue));

        List<PsyGaugeQuestionsResult> results = Lists.newArrayList();
        for (Long id : psyGaugeQuestionsResult.getQuestionsOptionsIdList()) {
            PsyGaugeQuestionsResult build = PsyGaugeQuestionsResult.builder()
                    .gaugeId(psyGaugeQuestionsResult.getGaugeId())
                    .questionsId(psyGaugeQuestionsResult.getQuestionsId())
                    .questionsOptionsId(id)
                    .score(collect.get(id).toString())
                    .userId(loginDTO.getUserId())
                    .orderId(psyGaugeQuestionsResult.getOrderId())
                    .build();
            build.setCreateTime(DateUtils.getNowDate());
            results.add(build);
        }
        return psyGaugeQuestionsResultMapper.batchInsert(results);
    }

    /**
     * 修改心理测评问题结果
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 结果
     */
    @Override
    public int updatePsyGaugeQuestionsResult(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        return psyGaugeQuestionsResultMapper.updatePsyGaugeQuestionsResult(psyGaugeQuestionsResult);
    }

    /**
     * 批量删除心理测评问题结果
     * 
     * @param ids 需要删除的心理测评问题结果主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsResultByIds(Long[] ids)
    {
        return psyGaugeQuestionsResultMapper.deletePsyGaugeQuestionsResultByIds(ids);
    }

    /**
     * 删除心理测评问题结果信息
     * 
     * @param id 心理测评问题结果主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsResultById(Long id)
    {
        return psyGaugeQuestionsResultMapper.deletePsyGaugeQuestionsResultById(id);
    }

    @Override
    public String commitResult(GaugeCommitResultDTO gaugeCommitResultDTO ,LoginDTO loginDTO) {
        //获取订单分值
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId",gaugeCommitResultDTO.getOrderId());
        paramMap.put("userId",loginDTO.getUserId());
        List<PsyGaugeQuestionsResultAll> psyGaugeQuestionsResults = psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultAll(paramMap);
        int sum=0;
        if(CollectionUtils.isNotEmpty(psyGaugeQuestionsResults)){
            for (PsyGaugeQuestionsResultAll psyGaugeQuestionsResultAll:psyGaugeQuestionsResults) {
                sum+=psyGaugeQuestionsResultAll.getValue();
            }
        }
        paramMap.put("score",sum);
        //获取当前得分匹配结果
        PsyGaugeScoreSetting psyGaugeScoreSetting = psyGaugeScoreSettingMapper.selectPsyGaugeScoreSettingByGaugeId(paramMap);
        if(psyGaugeScoreSetting!=null){
            //将该订单答题情况改为已完成
            psyOrderMapper.updatePsyOrder(PsyOrder.builder()
                    .orderId(gaugeCommitResultDTO.getOrderId()).gaugeStatus(GaugeStatus.FINISHED.getValue())
                    .score(String.valueOf(sum)).resultUrl(psyGaugeScoreSetting.getProposal())
                    .build());

            return psyGaugeScoreSetting.getResult();
        }

        //计算得分总和 得出结论
        /*gaugeCommitResultDTO.setUserId(loginDTO.getUserId());
        String result = psyGaugeQuestionsResultMapper.getSimpleResultByScores(gaugeCommitResultDTO);*/
        return null;
    }

    /**
     * 新增心理测评问题结果
     *
     * @param psyGaugeQuestionsResultAlls 心理测评问题结果
     * @return 结果
     */

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addList(List<PsyGaugeQuestionsResultAll> psyGaugeQuestionsResultAlls, LoginDTO loginUser) {
        if(CollectionUtils.isNotEmpty(psyGaugeQuestionsResultAlls)){
            //先删除当前订单所有问题的答案
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("orderId",psyGaugeQuestionsResultAlls.get(0).getOrderId());
            paramMap.put("userId",loginUser.getUserId());
            //psyGaugeQuestionsResultMapper.deleteAllResult(paramMap);
            List<PsyGaugeQuestionsResultAll> results = Lists.newArrayList();
            int sum=0;
            for (PsyGaugeQuestionsResultAll psyGaugeQuestionsResultAll:psyGaugeQuestionsResultAlls) {
                sum+=psyGaugeQuestionsResultAll.getValue();
                psyGaugeQuestionsResultAll.setCreateTime(DateUtils.getNowDate());
                psyGaugeQuestionsResultAll.setUserId(loginUser.getUserId());
                results.add(psyGaugeQuestionsResultAll);
            }
            paramMap.put("score",sum);
            //获取当前得分匹配结果
            PsyGaugeScoreSetting psyGaugeScoreSetting = psyGaugeScoreSettingMapper.selectPsyGaugeScoreSettingByGaugeId(paramMap);
            if(psyGaugeScoreSetting!=null){
                paramMap.put("proposal",psyGaugeScoreSetting.getProposal());
                //将该订单答题结果同步到订单表
                psyOrderMapper.updatePsyOrderByOrderId(paramMap);
            }
            //保存订单选项及结果
            //return psyGaugeQuestionsResultMapper.batchAllInsert(results);
        }
        return 0;
    }
}
