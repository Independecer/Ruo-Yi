package com.renxin.psychology.mapper;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantOrder;

/**
 * 咨询师团督订单Mapper接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface PsyConsultantOrderMapper 
{
    /**
     * 查询咨询师团督订单
     * 
     * @param orderNo 咨询师团督订单主键
     * @return 咨询师团督订单
     */
    public PsyConsultantOrder selectPsyConsultantOrderByOrderNo(String orderNo);

    /**
     * 查询咨询师团督订单列表
     * 
     * @param psyConsultantOrder 咨询师团督订单
     * @return 咨询师团督订单集合
     */
    public List<PsyConsultantOrder> selectPsyConsultantOrderList(PsyConsultantOrder psyConsultantOrder);

    /**
     * 新增咨询师团督订单
     * 
     * @param psyConsultantOrder 咨询师团督订单
     * @return 结果
     */
    public int insertPsyConsultantOrder(PsyConsultantOrder psyConsultantOrder);

    /**
     * 修改咨询师团督订单
     * 
     * @param psyConsultantOrder 咨询师团督订单
     * @return 结果
     */
    public int updatePsyConsultantOrder(PsyConsultantOrder psyConsultantOrder);

    /**
     * 删除咨询师团督订单
     * 
     * @param orderNo 咨询师团督订单主键
     * @return 结果
     */
    public int deletePsyConsultantOrderByOrderNo(String orderNo);

    /**
     * 批量删除咨询师团督订单
     * 
     * @param orderNos 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantOrderByOrderNos(String[] orderNos);
}
