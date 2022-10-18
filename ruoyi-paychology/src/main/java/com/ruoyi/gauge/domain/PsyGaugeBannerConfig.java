package com.ruoyi.gauge.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测评banner配置对象 psy_gauge_banner_config
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
public class PsyGaugeBannerConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** banner图片地址 */
    @Excel(name = "banner图片地址")
    private String bannerUrl;

    /** 跳转url */
    @Excel(name = "跳转url")
    private String linkUrl;

    /** banner分类(0-首页一级banner页，1-首页二级banner页，2-限时福利，3-全面评估) */
    @Excel(name = "banner分类(0-首页一级banner页，1-首页二级banner页，2-限时福利，3-全面评估)")
    private Integer bannerType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBannerUrl(String bannerUrl) 
    {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerUrl() 
    {
        return bannerUrl;
    }
    public void setLinkUrl(String linkUrl) 
    {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() 
    {
        return linkUrl;
    }
    public void setBannerType(Integer bannerType) 
    {
        this.bannerType = bannerType;
    }

    public Integer getBannerType() 
    {
        return bannerType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bannerUrl", getBannerUrl())
            .append("linkUrl", getLinkUrl())
            .append("bannerType", getBannerType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
