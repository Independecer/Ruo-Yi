package com.ruoyi.app;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.constant.IntegralRecordConstants;
import com.ruoyi.common.event.publish.IntegralPublisher;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@Slf4j
public class IntegralDemo {

    @Resource
    private IntegralPublisher integralPublisher;

    @Test
    public void doOrderPublish() {
        PsyUserIntegralRecord record = new PsyUserIntegralRecord();
        record.setId(IDhelper.getNextId());
        record.setCreateBy("system");
        record.setUpdateBy("system");
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setLinkId("1234");
        record.setLinkType(IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER);
        record.setUid(84);
        record.setBalance(100);
        record.setIntegral(100);
        record.setDelFlag(0);
        record.setFrozenTime(0);
        record.setMark(StrUtil.format("用户付款成功,订单增加{}积分", record.getIntegral()));
        record.setType(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
        record.setTitle(IntegralRecordConstants.BROKERAGE_RECORD_TITLE_ORDER);
        record.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);

        integralPublisher.publish(record);
    }

}
