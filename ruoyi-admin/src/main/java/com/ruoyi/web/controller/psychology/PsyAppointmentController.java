package com.ruoyi.web.controller.psychology;

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
import com.ruoyi.psychology.domain.PsyAppointment;
import com.ruoyi.psychology.service.IPsyAppointmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 咨询师预约Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/psychology/appointment")
public class PsyAppointmentController extends BaseController
{
    @Autowired
    private IPsyAppointmentService psyAppointmentService;

    /**
     * 查询咨询师预约列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:appointment:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyAppointment psyAppointment)
    {
        startPage();
        List<PsyAppointment> list = psyAppointmentService.selectPsyAppointmentList(psyAppointment);
        return getDataTable(list);
    }

    /**
     * 导出咨询师预约列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:appointment:export')")
    @Log(title = "咨询师预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyAppointment psyAppointment)
    {
        List<PsyAppointment> list = psyAppointmentService.selectPsyAppointmentList(psyAppointment);
        ExcelUtil<PsyAppointment> util = new ExcelUtil<PsyAppointment>(PsyAppointment.class);
        util.exportExcel(response, list, "咨询师预约数据");
    }

    /**
     * 获取咨询师预约详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:appointment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyAppointmentService.selectPsyAppointmentById(id));
    }

    /**
     * 新增咨询师预约
     */
    @PreAuthorize("@ss.hasPermi('psychology:appointment:add')")
    @Log(title = "咨询师预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyAppointment psyAppointment)
    {
        return toAjax(psyAppointmentService.insertPsyAppointment(psyAppointment));
    }

    /**
     * 修改咨询师预约
     */
    @PreAuthorize("@ss.hasPermi('psychology:appointment:edit')")
    @Log(title = "咨询师预约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyAppointment psyAppointment)
    {
        return toAjax(psyAppointmentService.updatePsyAppointment(psyAppointment));
    }

    /**
     * 删除咨询师预约
     */
    @PreAuthorize("@ss.hasPermi('psychology:appointment:remove')")
    @Log(title = "咨询师预约", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyAppointmentService.deletePsyAppointmentByIds(ids));
    }
}
