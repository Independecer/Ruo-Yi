package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 测评分类对象 psy_gauge_class
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@Data
public class PsyGaugeClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Excel(name = "分类名")
    private String name;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    @Excel(name = "图标路径")
    private String url;

}
