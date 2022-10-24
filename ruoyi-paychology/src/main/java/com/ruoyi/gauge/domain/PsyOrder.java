package com.ruoyi.gauge.domain;

import java.math.BigDecimal;

import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 心理测评订单信息对象 psy_order
 * 
 * @author ruoyi
 * @date 2022-10-12
 */
@Builder
public class PsyOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderId;

    /** 测评编号 */
    @Excel(name = "测评编号")
    private Long gaugeId;

    /** 订单状态(1-创建,2-完成,3-关闭) */
    @Excel(name = "订单状态(1-创建,2-完成,3-关闭)")
    private Integer orderStatus;

    /** 应付金额 */
    @Excel(name = "应付金额")
    private BigDecimal amount;

    /** 测评完成情况(1-已完成，2-未完成) */
    @Excel(name = "测评完成情况(1-已完成，2-未完成)")
    private Integer gaugeStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setGaugeId(Long gaugeId) 
    {
        this.gaugeId = gaugeId;
    }

    public Long getGaugeId() 
    {
        return gaugeId;
    }
    public void setOrderStatus(Integer orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus() 
    {
        return orderStatus;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setGaugeStatus(Integer gaugeStatus) 
    {
        this.gaugeStatus = gaugeStatus;
    }

    public Integer getGaugeStatus() 
    {
        return gaugeStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("gaugeId", getGaugeId())
            .append("orderStatus", getOrderStatus())
            .append("amount", getAmount())
            .append("gaugeStatus", getGaugeStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
