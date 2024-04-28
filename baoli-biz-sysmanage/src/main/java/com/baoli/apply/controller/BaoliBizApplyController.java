package com.baoli.apply.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baoli.apply.domain.BaoliBizApply;
import com.baoli.apply.service.IBaoliBizApplyService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请管理Controller
 * 
 * @author niujs
 * @date 2024-04-21
 */
@RestController
@RequestMapping("/apply/apply")
public class BaoliBizApplyController extends BaseController
{
    @Autowired
    private IBaoliBizApplyService baoliBizApplyService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询申请管理列表
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizApply baoliBizApply)
    {
        startPage();
        List<BaoliBizApply> list = baoliBizApplyService.selectBaoliBizApplyList(baoliBizApply);
        return getDataTable(list);
    }

    /**
     * 导出申请管理列表
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:export')")
    @Log(title = "申请管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizApply baoliBizApply)
    {
        List<BaoliBizApply> list = baoliBizApplyService.selectBaoliBizApplyList(baoliBizApply);
        ExcelUtil<BaoliBizApply> util = new ExcelUtil<BaoliBizApply>(BaoliBizApply.class);
        util.exportExcel(response, list, "申请管理数据");
    }

    /**
     * 获取申请管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizApplyService.selectBaoliBizApplyById(id));
    }

    /**
     * 新增申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:add')")
    @Log(title = "申请管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizApply baoliBizApply)
    {
        baoliBizApply.setApplicantId(getUserId());
        if(baoliBizApply.getStatus() == null){
            baoliBizApply.setStatus("02");
        }
        JSONObject request = JSONObject.parseObject("{\"processDefinitionId\":\"Flowable1784092644025155584:6:1784114952487907328\",\"formData\":{},\"processUsers\":{},\"startUserInfo\":{\"id\":\"1\",\"name\":\"admin\",\"type\":\"user\"}}");
        String response = restTemplate.postForObject("http://192.168.10.37:9999/workspace/process/start", request, String.class);
        return toAjax(baoliBizApplyService.insertBaoliBizApply(baoliBizApply));
    }

    /**
     * 修改申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:edit')")
    @Log(title = "申请管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizApply baoliBizApply)
    {
        return toAjax(baoliBizApplyService.updateBaoliBizApply(baoliBizApply));
    }

    /**
     * 删除申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:remove')")
    @Log(title = "申请管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizApplyService.deleteBaoliBizApplyByIds(ids));
    }
}
