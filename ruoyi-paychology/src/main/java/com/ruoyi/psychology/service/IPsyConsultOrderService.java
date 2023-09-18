package com.ruoyi.psychology.service;

import java.util.List;
import com.ruoyi.common.domain.PsyOrderLog;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.dto.OrderDTO;
import com.ruoyi.psychology.dto.OrderListDTO;
import com.ruoyi.psychology.request.PsyAdminOrderReq;
import com.ruoyi.psychology.request.PsyHxOrderReq;
import com.ruoyi.psychology.request.PsyRefOrderReq;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;

/**
 * 咨询订单Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface IPsyConsultOrderService 
{

    OrderDTO getOrderDetail(Long id);

    OrderDTO getOrderDetailByNo(String orderNo);

    boolean checkNewByServe(Long orderId, Long serveId, Integer userId);

    PsyConsultOrder getOrderById(Long id);

    PsyConsultOrderVO getOne(Long id);

    String getOpenId(Long cId);

    List<PsyOrderLog> getLogs(String orderNo);

    List<PsyConsultOrder> getList(PsyAdminOrderReq req);

    List<PsyConsultOrder> getListForNotice(String last);

    List<PsyConsultOrder> getCancelList(int num);

    List<OrderListDTO> getOrderList(PsyConsultOrderVO req);

    String hx(PsyHxOrderReq req);

    String modifyRef(PsyRefOrderReq req);

    String modifyPrice(PsyConsultOrderVO req);

    void updatePayOrder(PsyConsultOrderVO req);

    Boolean sendPublicMsg(PsyConsultOrderVO psyOrder);

    void wechatPayNotify(PsyConsultOrderVO req);

    int doConsult(Long id, Long workId, Integer time);

    int add(PsyConsultOrderVO req);

    void cancel(PsyConsultOrder order, String createBy);

    int update(PsyConsultOrderVO req);

    int remark(PsyConsultOrderVO req);

    int deleteAll(Long[] ids);

    int delete(Long id);
}
