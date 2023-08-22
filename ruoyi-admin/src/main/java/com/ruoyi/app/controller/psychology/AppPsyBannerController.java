package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.psychology.domain.PsyConsultBannerConfig;
import com.ruoyi.psychology.service.IPsyConsultBannerConfigService;
import com.ruoyi.psychology.vo.PsyConsultBannerConfigVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consult/banner")
public class AppPsyBannerController extends BaseController
{
    @Resource
    private IPsyConsultBannerConfigService PsyConsultBannerConfigService;

    /**
     * 查询banner列表
     */
    @GetMapping("/list")
    @RateLimiter(count = 50, limitType = LimitType.IP)
    public TableDataInfo list(PsyConsultBannerConfigVO req)
    {
        startPage();
        List<PsyConsultBannerConfig> list = PsyConsultBannerConfigService.getList(req);
        return getDataTable(list);
    }

}
