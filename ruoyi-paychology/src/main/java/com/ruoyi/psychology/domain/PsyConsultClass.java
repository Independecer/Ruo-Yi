package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BasePlusEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询类型对象 psy_consult_class
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_class")
public class PsyConsultClass extends BasePlusEntity implements Serializable
{

    private static final long serialVersionUID = -7533197580699098875L;
    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 图标路径 */
    @Excel(name = "图标路径")
    private String url;

    /** 跳转url */
    @Excel(name = "跳转url")
    private String linkUrl;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

}
