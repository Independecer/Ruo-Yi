package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.GaugeCommitResultDTO;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResultAll;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 心理测评问题结果Controller
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@RestController
@RequestMapping("/app/gauge/result")
@Api(value = "AppPsyGaugeQuestionsResultController" ,tags = {"测评结果提交"})
public class AppPsyGaugeQuestionsResultController extends BaseController
{
    @Autowired
    private IPsyGaugeQuestionsResultService psyGaugeQuestionsResultService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 新增心理测评问题结果
     */
    @PostMapping
    @ApiOperation("答题")
    public AjaxResult add(@RequestBody @Validated PsyGaugeQuestionsResult psyGaugeQuestionsResult )
    {
        String userId = psyGaugeQuestionsResult.getUserId();
        return toAjax(psyGaugeQuestionsResultService.answer(psyGaugeQuestionsResult ,userId));
    }


    /**
     * 新增心理测评问题结果
     */
    @PostMapping("/commit")
    @ApiOperation("普通计算提交测评并生成结果")
    public AjaxResult commitResult(@RequestBody @Validated GaugeCommitResultDTO gaugeCommitResultDTO)
    {
        String userId = gaugeCommitResultDTO.getUserId();
        String result = psyGaugeQuestionsResultService.commitResult(gaugeCommitResultDTO ,userId);
        return AjaxResult.success(RespMessageConstants.OPERATION_SUCCESS ,result);
    }

    /**
     * 测评结果保存
     */
    @PostMapping("/addList")
    @ApiOperation("测评结果保存")
    public AjaxResult addList(@RequestBody @Validated List<PsyGaugeQuestionsResultAll> psyGaugeQuestionsResultAlls)
    {
        return toAjax(psyGaugeQuestionsResultService.addList(psyGaugeQuestionsResultAlls));
    }
}
