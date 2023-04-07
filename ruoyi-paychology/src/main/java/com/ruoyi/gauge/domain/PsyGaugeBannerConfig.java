package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 测评banner配置对象 psy_gauge_banner_config
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
@Data
public class PsyGaugeBannerConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** banner图片地址 */
    @Excel(name = "banner图片地址")
    private String bannerUrl;

    /** 跳转url */
    @Excel(name = "跳转url")
    private String linkUrl;

    /** banner分类(0-首页一级banner页，1-首页二级banner页，2-限时福利，3-全面评估) */
    @Excel(name = "banner分类(0-首页一级banner页，1-首页二级banner页，2-限时福利，3-全面评估)")
    private Integer bannerType;

}
