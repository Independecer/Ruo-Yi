package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.BusPostOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2024
 * Company:
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2024-06-19 10:14:14
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2024-06-19     张李鑫                     1.0         1.0 Version
 */
public interface BusPostOrderExtraMapper extends BusPostOrderMapper{
    List<BusPostOrder> findOrderListByUserId(@Param("statusList") List<Integer> statusList, @Param("userId") Long merchantId);

    List<BusPostOrder> findSampleOrder(@Param("busPostOrder")BusPostOrder busPostOrder, @Param("statusList")List<Integer> statusList);
}
