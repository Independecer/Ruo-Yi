package com.ruoyi.consultant.controller;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.service.ConsultantTokenService;
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import com.ruoyi.psychology.service.IPsyConsultPartnerService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/consultant/partner")
@Api(value = "ConsultantWorkController" ,tags = {"咨询师入驻Api"})
public class ConsultantPartnerController {

    @Resource
    private ConsultantTokenService consultantTokenService;

    @Resource
    private IPsyConsultPartnerService partnerService;

    @PostMapping(value = "/addItem")
    @RateLimiter
    public AjaxResult addItem(@RequestBody PsyConsultPartnerItem item)
    {
        return AjaxResult.success(partnerService.addItem(item));
    }

    @PostMapping(value = "/editItem")
    @RateLimiter
    public AjaxResult editItem(@RequestBody PsyConsultPartnerItem item)
    {
        return AjaxResult.success(partnerService.editItem(item));
    }

    @PostMapping(value = "/delItem/{id}")
    @RateLimiter
    public AjaxResult delItem(@PathVariable("id") Long id)
    {
        return AjaxResult.success(partnerService.delItem(id));
    }

    @PostMapping(value = "/draft")
    @RateLimiter
    public AjaxResult draft(HttpServletRequest request)
    {
        Integer userId = consultantTokenService.getUserId(request);
        partnerService.draft(userId);
        return AjaxResult.success();
    }

    @PostMapping(value = "/save")
    @RateLimiter
    public AjaxResult save(@RequestBody PsyConsultPartner entity, HttpServletRequest request)
    {
        Integer userId = consultantTokenService.getUserId(request);
        entity.setUserId(userId);
        return AjaxResult.success(partnerService.save(entity));
    }

    @PostMapping(value = "/getInfo")
    @RateLimiter
    public AjaxResult getInfo(HttpServletRequest request)
    {
        Integer userId = consultantTokenService.getUserId(request);
        return AjaxResult.success(partnerService.getInfoByUserId(userId));
    }

}
