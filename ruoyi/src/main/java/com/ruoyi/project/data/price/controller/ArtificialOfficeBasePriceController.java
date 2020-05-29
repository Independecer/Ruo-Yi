//package com.ruoyi.project.data.price.controller;
//
//import com.ruoyi.common.utils.ServletUtils;
//import com.ruoyi.common.utils.poi.ExcelUtil;
//import com.ruoyi.framework.aspectj.lang.annotation.Log;
//import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
//import com.ruoyi.framework.security.LoginUser;
//import com.ruoyi.framework.security.service.TokenService;
//import com.ruoyi.framework.web.controller.BaseController;
//import com.ruoyi.framework.web.domain.AjaxResult;
//import com.ruoyi.framework.web.page.TableDataInfo;
//import com.ruoyi.framework.web.page.TableSupport;
//import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
//import com.ruoyi.project.data.price.service.IOfficeBasePriceUltimateService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
///**
// * 【请填写功能名称】Controller
// *
// * @author ruoyi
// * @date 2020-05-20
// */
//@RestController
//@RequestMapping("/data/compute/price/office")
//public class ArtificialOfficeBasePriceController extends BaseController {
//    @Autowired
//    private IOfficeBasePriceUltimateService officeBasePriceUltimateService;
//    @Autowired
//    private TokenService tokenService;
//
//    /**
//     * 查询【请填写功能名称】列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(OfficeBasePriceUltimate officeBasePriceUltimate) {
//        int pageIndex = ServletUtils.getParameterToInt(TableSupport.PAGE_NUM);
//        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
//        officeBasePriceUltimate.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
//        officeBasePriceUltimate.setPageSize(pageSize);
//        List<OfficeBasePriceUltimate> list =
//                officeBasePriceUltimateService.selectOfficeBasePriceUltimateList(officeBasePriceUltimate);
//        int total = officeBasePriceUltimateService.selectOfficeBasePriceUltimateListCount(officeBasePriceUltimate);
//        return getDataTable(list, total);
//    }
//
//
//    /**
//     * 获取【请填写功能名称】详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") String id) {
//        return AjaxResult.success(officeBasePriceUltimateService.selectOfficeBasePriceUltimateById(id));
//    }
//
//    /**
//     * 修改【请填写功能名称】
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:edit')")
//    @Log(title = "办公基价", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody OfficeBasePriceUltimate officeBasePriceUltimate) {
//        return toAjax(officeBasePriceUltimateService.updateOfficeBasePriceUltimate(officeBasePriceUltimate));
//    }
//
//    /**
//     * 导出【请填写功能名称】列表
//     */
//    @PreAuthorize("@ss.hasPermi('system:user:export')")
//    @Log(title = "办公基价", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(OfficeBasePriceUltimate officeBasePriceUltimate) {
//        int total = officeBasePriceUltimateService.selectOfficeBasePriceUltimateListCount(officeBasePriceUltimate);
//        officeBasePriceUltimate.setPageIndex(0);
//        officeBasePriceUltimate.setPageSize(total);
//        List<OfficeBasePriceUltimate> list =
//                officeBasePriceUltimateService.selectOfficeBasePriceUltimateList(officeBasePriceUltimate);
//        ExcelUtil<OfficeBasePriceUltimate> util = new ExcelUtil<OfficeBasePriceUltimate>(OfficeBasePriceUltimate.class);
//        return util.exportExcel(list, "办公基价");
//    }
//
//    /**
//     * 办公基价导入
//     * @param file
//     * @return
//     * @throws Exception
//     */
//    @Log(title = "办公基价", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:import')")
//    @PostMapping("/importData")
//    public AjaxResult importData(MultipartFile file) throws Exception {
//        ExcelUtil<OfficeBasePriceUltimate> util = new ExcelUtil<>(OfficeBasePriceUltimate.class);
//        List<OfficeBasePriceUltimate> officeBasePriceUltimates = util.importExcel(file.getInputStream());
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        String operName = loginUser.getUsername();
//        String message = officeBasePriceUltimateService.batchImport(officeBasePriceUltimates, operName);
//        return AjaxResult.success(message);
//    }
//
//}
//
