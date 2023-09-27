package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;
import com.ruoyi.gauge.service.IPsyGaugeService;
import com.ruoyi.gauge.service.IPsyOrderService;
import com.ruoyi.gauge.vo.GaugeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 心理测评Controller
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/app/home/gauge")
@Api(value = "AppPsyGaugeController" ,tags = {"测评量表api"})
public class AppPsyGaugeController extends BaseController
{
    @Autowired
    private IPsyGaugeService psyGaugeService;

    @Autowired
    private IPsyOrderService psyOrderService;

    @Autowired
    private AppTokenService appTokenService;

    @Autowired
    private IPsyGaugeQuestionsResultService psyGaugeQuestionsResultService;
    /**
     * 查询心理测评列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:list')")
    @GetMapping("/list")
    @ApiOperation("查询量表数据列表")
    @RateLimiter
    public TableDataInfo list(PsyGauge psyGauge)
    {
        startPage();
        List<PsyGauge> list = psyGaugeService.selectPsyGaugeList(psyGauge);
        return getDataTable(list);
    }

    /**
     * 获取心理测评详细信息
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:query')")
    @PostMapping(value = "/getInfo")
    @ApiOperation("查询量表详细信息")
    @RateLimiter
    public AjaxResult getInfo(@RequestParam(value = "id") Integer id, HttpServletRequest request)
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        Integer userId = loginUser.getUserId();
        PsyGauge psyGauge = psyGaugeService.selectPsyGaugeById(id);
        GaugeVO gaugeVO = new GaugeVO();
        BeanUtils.copyProperties(psyGauge, gaugeVO);
        List<PsyOrder> psyOrder = psyOrderService.getPsyOrder(userId, id);

        // 未完成测试，待测试 orderId 优先级最高
        // 全部测试完成，查看报告 orderId
        // 再次购买
        if (psyOrder.size() > 0) {
            Optional<PsyOrder> first = psyOrder.stream().filter(order -> order.getGaugeStatus() == GaugeStatus.UNFINISHED.getValue()).findFirst();
            if (first.isPresent()) {
                gaugeVO.setOrderId(first.get().getId());
                gaugeVO.setOrderNo(first.get().getOrderId());
                gaugeVO.setIsCompleted(GaugeStatus.UNFINISHED.getValue());
            } else {
                gaugeVO.setOrderId(psyOrder.get(0).getId());
                gaugeVO.setOrderNo(psyOrder.get(0).getOrderId());
                gaugeVO.setIsCompleted(psyOrder.get(0).getGaugeStatus());
            }

            gaugeVO.setIsBuy(GaugeConstant.GAUGE_IS_BUY);
        } else {
            gaugeVO.setIsBuy(GaugeConstant.GAUGE_NOT_BUY);
            gaugeVO.setIsCompleted(GaugeStatus.UNFINISHED.getValue());
        }

        gaugeVO.setSize(psyOrder.size());
        PsyGaugeQuestionsResult psyGaugeQuestionsResult = new PsyGaugeQuestionsResult();
        psyGaugeQuestionsResult.setUserId(userId);
        psyGaugeQuestionsResult.setGaugeId(id);
        if (gaugeVO.getOrderId() != null) {
            psyGaugeQuestionsResult.setOrderId(gaugeVO.getOrderId());
        }
        List<PsyGaugeQuestionsResult> psyGaugeQuestionsResultList = psyGaugeQuestionsResultService.selectPsyGaugeQuestionsResultList(psyGaugeQuestionsResult);
        // 将多选题的答案选项分组归并
//        Map<Integer, Long> result  = psyGaugeQuestionsResultList.stream().collect(Collectors.groupingBy(PsyGaugeQuestionsResult::getQuestionsId, Collectors.counting()));
        gaugeVO.setFinishedNum(psyGaugeQuestionsResultList.size());

        return AjaxResult.success(gaugeVO);
    }

    /**
     * 根据搜素条件查询课程列表
     */
    @PostMapping("/search")
    @ApiOperation("查询课程列表")
    @RateLimiter
    public AjaxResult getList(@RequestParam String searchValue)    {
        List<PsyGauge> list = psyGaugeService.selectPsyGaugeList(null);
        list = list.stream()
                .filter(item -> item.getTitle().contains(searchValue))
                .collect(Collectors.toList());
        return AjaxResult.success(list);
    }

}
