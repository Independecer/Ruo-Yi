package com.onethinker.web.controller.onethinker;

import com.onethinker.activity.domain.RedEnvelopeDtl;
import com.onethinker.activity.service.IRedEnvelopeDtlService;
import com.onethinker.common.annotation.Log;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.BusinessType;
import com.onethinker.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 红包明细
 * @author yangyouqi
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/onethinker/dtl")
public class RedEnvelopeDtlController extends BaseController {
    @Autowired
    private IRedEnvelopeDtlService redEnvelopeDtlService;

    /**
     * 查询红包明细列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:list')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/list")
    public TableDataInfo list(RedEnvelopeDtl redEnvelopeDtl) {
        startPage();
        List<RedEnvelopeDtl> list = redEnvelopeDtlService.selectRedEnvelopeDtlList(redEnvelopeDtl);
        return getDataTable(list);
    }

    /**
     * 导出红包明细列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:export')")
    @Log(title = "红包明细", businessType = BusinessType.EXPORT)
    @PostMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/export")
    public void export(HttpServletResponse response, RedEnvelopeDtl redEnvelopeDtl) {
        List<RedEnvelopeDtl> list = redEnvelopeDtlService.selectRedEnvelopeDtlList(redEnvelopeDtl);
        ExcelUtil<RedEnvelopeDtl> util = new ExcelUtil<RedEnvelopeDtl>(RedEnvelopeDtl.class);
        util.exportExcel(response, list, "红包明细数据");
    }

    /**
     * 获取红包明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:query')")
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH +  "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(redEnvelopeDtlService.selectRedEnvelopeDtlById(id));
    }

    /**
     * 删除红包明细
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:remove')")
    @Log(title = "红包明细", businessType = BusinessType.DELETE)
    @DeleteMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(redEnvelopeDtlService.deleteRedEnvelopeDtlByIds(ids));
    }
}
