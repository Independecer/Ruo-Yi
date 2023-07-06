package com.ruoyi.course.task;

import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("courseOrderTask")
public class OrderTask {

    @Resource
    private ISysConfigService configService;

    @Resource
    private ICourOrderService courOrderService;

    public void cancel()
    {
        String val = configService.selectConfigByKey("order.cancel.time");
        int num = StringUtils.isNotEmpty(val) ? Integer.parseInt(val) : 15;
        List<CourOrder> cancelList = courOrderService.getCancelList(num);
        if (CollectionUtils.isNotEmpty(cancelList)) {
            cancelList.forEach(order -> {
                order.setStatus(CourConstant.COUR_ORDER_STATUE_CANCELED);
                courOrderService.updateCourOrder(order);
            });
            List<Integer> collect = cancelList.stream().map(CourOrder::getId).collect(Collectors.toList());
            log.info("课程订单取消, 订单id={} 自动修改订单状态为已取消，操作已完成", collect);
        }
    }

}
