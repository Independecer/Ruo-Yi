package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.service.IPsyGaugeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查询心理测评列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:list')")
    @GetMapping("/list")
    @ApiOperation("查询量表数据列表")
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
    public AjaxResult getInfo(@RequestParam(value = "id") Long id)
    {
        return AjaxResult.success(psyGaugeService.selectPsyGaugeById(id));
    }

}
