package com.ruoyi.web.controller.gauge;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.gauge.domain.PsyGaugeLabel;
import com.ruoyi.gauge.service.IPsyGaugeLabelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测评标签Controller
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/gauge/label")
public class PsyGaugeLabelController extends BaseController
{
    @Autowired
    private IPsyGaugeLabelService psyGaugeLabelService;

    /**
     * 查询测评标签列表
     */
    @PreAuthorize("@ss.hasPermi('gauge:label:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyGaugeLabel psyGaugeLabel)
    {
        startPage();
        List<PsyGaugeLabel> list = psyGaugeLabelService.selectPsyGaugeLabelList(psyGaugeLabel);
        return getDataTable(list);
    }

    /**
     * 导出测评标签列表
     */
    @PreAuthorize("@ss.hasPermi('gauge:label:export')")
    @Log(title = "测评标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyGaugeLabel psyGaugeLabel)
    {
        List<PsyGaugeLabel> list = psyGaugeLabelService.selectPsyGaugeLabelList(psyGaugeLabel);
        ExcelUtil<PsyGaugeLabel> util = new ExcelUtil<PsyGaugeLabel>(PsyGaugeLabel.class);
        util.exportExcel(response, list, "测评标签数据");
    }

    /**
     * 获取测评标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('gauge:label:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(psyGaugeLabelService.selectPsyGaugeLabelById(id));
    }

    /**
     * 新增测评标签
     */
    @PreAuthorize("@ss.hasPermi('gauge:label:add')")
    @Log(title = "测评标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyGaugeLabel psyGaugeLabel)
    {
        return toAjax(psyGaugeLabelService.insertPsyGaugeLabel(psyGaugeLabel));
    }

    /**
     * 修改测评标签
     */
    @PreAuthorize("@ss.hasPermi('gauge:label:edit')")
    @Log(title = "测评标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyGaugeLabel psyGaugeLabel)
    {
        return toAjax(psyGaugeLabelService.updatePsyGaugeLabel(psyGaugeLabel));
    }

    /**
     * 删除测评标签
     */
    @PreAuthorize("@ss.hasPermi('gauge:label:remove')")
    @Log(title = "测评标签", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(psyGaugeLabelService.deletePsyGaugeLabelByIds(ids));
    }
}
