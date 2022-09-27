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
import com.ruoyi.gauge.domain.PsyGaugeClass;
import com.ruoyi.gauge.service.IPsyGaugeClassService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测评分类Controller
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/psychology/gaugeClass")
public class PsyGaugeClassController extends BaseController
{
    @Autowired
    private IPsyGaugeClassService psyGaugeClassService;

    /**
     * 查询测评分类列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyGaugeClass psyGaugeClass)
    {
        startPage();
        List<PsyGaugeClass> list = psyGaugeClassService.selectPsyGaugeClassList(psyGaugeClass);
        return getDataTable(list);
    }

    /**
     * 导出测评分类列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:export')")
    @Log(title = "测评分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyGaugeClass psyGaugeClass)
    {
        List<PsyGaugeClass> list = psyGaugeClassService.selectPsyGaugeClassList(psyGaugeClass);
        ExcelUtil<PsyGaugeClass> util = new ExcelUtil<PsyGaugeClass>(PsyGaugeClass.class);
        util.exportExcel(response, list, "测评分类数据");
    }

    /**
     * 获取测评分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyGaugeClassService.selectPsyGaugeClassById(id));
    }

    /**
     * 新增测评分类
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:add')")
    @Log(title = "测评分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyGaugeClass psyGaugeClass)
    {
        return toAjax(psyGaugeClassService.insertPsyGaugeClass(psyGaugeClass));
    }

    /**
     * 修改测评分类
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:edit')")
    @Log(title = "测评分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyGaugeClass psyGaugeClass)
    {
        return toAjax(psyGaugeClassService.updatePsyGaugeClass(psyGaugeClass));
    }

    /**
     * 删除测评分类
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:remove')")
    @Log(title = "测评分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyGaugeClassService.deletePsyGaugeClassByIds(ids));
    }
}
