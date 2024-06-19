package com.renxin.psychology.vo;

import com.renxin.common.core.domain.BaseValObj;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 咨询类型对象 psy_consult_class
 * 
 * @author renxin
 * @date 2023-06-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultClassVO extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = 2656547953531703124L;
    /** 名称 */
    private String name;

    /** 排序 */
    private Integer sort;

    /** 图标路径 */
    private String url;

    /** 跳转url */
    private String linkUrl;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 状态（0正常 1停用） */
    private String status;

    /** 0-跳转 1-筛选 */
    private String type;

    /** 满足条件 0-and 1-or */
    private String nand;

    /** 服务（0开启 1禁用） */
    private String serve;

    /** 服务 */
    private Long serveId;

    /** 价格 */
    private String price;

    /** 当日可约（0可约 1不可约） */
    private String buy;

    /** 是否单次（0是 1否） */
    private String single;

    /** 价格 */
    private BigDecimal lowPrice;

    /** 价格 */
    private BigDecimal highPrice;

    /** 擅长领域 */
    private String way;
    private String wayStr;

}
