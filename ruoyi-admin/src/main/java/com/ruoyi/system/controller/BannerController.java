package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.system.domain.Banner;
import com.ruoyi.system.service.IBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * banner管理Controller
 *
 * @author carol
 * @date 2024-03-06
 */
@RestController
@RequestMapping("/api/banner")
public class BannerController extends BaseController
{
    @Autowired
    private IBannerService bannerService;

    /**
     * 查询banner管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(Banner banner)
    {
        startPage();
        List<Banner> list = bannerService.selectBannerList(banner);
        return getDataTable(list);
    }

    /**
     * 导出banner管理列表
     */
    @PreAuthorize("@ss.hasPermi('api:banner:export')")
    @Log(title = "banner管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Banner banner)
    {
        List<Banner> list = bannerService.selectBannerList(banner);
        ExcelUtil<Banner> util = new ExcelUtil<Banner>(Banner.class);
        util.exportExcel(response, list, "banner管理数据");
    }

    /**
     * 获取banner管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('api:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bannerService.selectBannerById(id));
    }

    /**
     * 新增banner管理
     */
    @PreAuthorize("@ss.hasPermi('api:banner:add')")
    @Log(title = "banner管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner)
    {
        return toAjax(bannerService.insertBanner(banner));
    }

    /**
     * 修改banner管理
     */
    @PreAuthorize("@ss.hasPermi('api:banner:edit')")
    @Log(title = "banner管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Banner banner)
    {
        return toAjax(bannerService.updateBanner(banner));
    }

    /**
     * 删除banner管理
     */
    @PreAuthorize("@ss.hasPermi('api:banner:remove')")
    @Log(title = "banner管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bannerService.deleteBannerByIds(ids));
    }


    @GetMapping("/getByScene/{scene}")
    @ApiOperation("根据场景获取banner")
    public R<List<Banner>> getBannerByScene(@PathVariable("scene") int scene){
        List<Banner> banners = bannerService.selectBannerByScene(scene);
        return R.ok();
    }
}
