package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.domain.PsyGaugeQuestions;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsService;
import com.ruoyi.gauge.vo.PsyQuestionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 心理测评问题Controller
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
@RestController
@RequestMapping("/app/gauge/questions")
@Api(value = "AppPsyGaugeQuestionsController" ,tags = {"测评问题api"})
public class AppPsyGaugeQuestionsController extends BaseController
{
    @Autowired
    private IPsyGaugeQuestionsService psyGaugeQuestionsService;

    @Autowired
    private AppTokenService appTokenService;

    /**
     * 查询心理测评问题列表
     */
//    @PreAuthorize("@ss.hasPermi('gauge:questions:list')")
    @GetMapping("/list")
    @ApiOperation("查询测评问题列表")
    @RepeatSubmit
    public AjaxResult list(PsyGaugeQuestions psyGaugeQuestions ,HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        Integer userId = psyGaugeQuestions.getUserId();
        List<PsyQuestionVO> list = psyGaugeQuestionsService.appQueryQuesList(psyGaugeQuestions ,userId);
        return AjaxResult.success(list);
    }


}
