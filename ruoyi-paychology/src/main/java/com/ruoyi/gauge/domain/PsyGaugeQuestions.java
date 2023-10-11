package com.ruoyi.gauge.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评问题对象 psy_gauge_questions
 * 
 * @author ruoyi
 * @date 2022-09-06
 */
@Data
public class PsyGaugeQuestions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;

    /** 测评表单ID */
    @Excel(name = "测评表单ID")
    private Integer gaugeId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /**
     * 选择类型
     * 0-单选
     * 1-多选
     */
    private Integer selectType;

    @Excel(name = "选择类型")
    private String selectTypeName;

    /** 序号 */
    @Excel(name = "序号")
    private Integer no;

    private Integer userId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 纬度 0-无,1-躯体不适,2-综合,3-社会适应性多,4-运动性不安,5-神经系统反应,6-莫名恐惧
     */
    @Excel(name = "纬度")
    private Integer lat;

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
        this.selectTypeName = this.selectType == 0 ? "单选" : "多选";
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gaugeId", getGaugeId())
            .append("title", getTitle())
            .append("no", getNo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("lat", getLat())
            .toString();
    }
}
