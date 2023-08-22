package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.GaugeCommitResultDTO;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResultAll;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import com.ruoyi.gauge.service.IPsyOrderService;
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
    private IPsyOrderService psyOrderService;

    @Autowired
    private IPsyGaugeQuestionsResultService psyGaugeQuestionsResultService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 新增心理测评问题结果
     */
    @PostMapping
    @ApiOperation("答题")
    @RateLimiter(limitType = LimitType.IP)
    public AjaxResult add(@RequestBody @Validated PsyGaugeQuestionsResult psyGaugeQuestionsResult, HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        System.out.println(loginUser);
        Integer userId = loginUser.getUserId();
        return toAjax(psyGaugeQuestionsResultService.answer(psyGaugeQuestionsResult ,userId));
    }


    /**
     * 新增心理测评问题结果
     */
    @PostMapping("/commit")
    @ApiOperation("普通计算提交测评并生成结果")
    @RateLimiter(limitType = LimitType.IP)
    public AjaxResult commitResult(@RequestBody @Validated GaugeCommitResultDTO gaugeCommitResultDTO, HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        Integer userId = loginUser.getUserId();
        String result = psyGaugeQuestionsResultService.commitResult(gaugeCommitResultDTO ,userId);

        // 测评完成更新测评状态
        PsyOrder psyOrder = new PsyOrder();
        psyOrder.setUserId(userId);
        psyOrder.setId(gaugeCommitResultDTO.getOrderId());
        psyOrder.setGaugeStatus(GaugeStatus.FINISHED.getValue());
        psyOrderService.updatePsyOrder(psyOrder);
        return AjaxResult.success(RespMessageConstants.OPERATION_SUCCESS ,result);
    }

    /**
     * 测评结果保存
     */
    @PostMapping("/addList")
    @ApiOperation("测评结果保存")
    @RateLimiter(limitType = LimitType.IP)
    public AjaxResult addList(@RequestBody @Validated List<PsyGaugeQuestionsResultAll> psyGaugeQuestionsResultAlls)
    {
        return toAjax(psyGaugeQuestionsResultService.addList(psyGaugeQuestionsResultAlls));
    }
}
