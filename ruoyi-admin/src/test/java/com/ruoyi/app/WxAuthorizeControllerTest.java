package com.ruoyi.app;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.vo.LoginVO;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.framework.web.service.AppTokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@Slf4j
public class WxAuthorizeControllerTest {

    @Autowired
    private AppTokenService appTokenService;

    @Test
    public void deGet() {
        LoginDTO loginUser = new LoginDTO();
        loginUser.setWxOpenId("oviCP5w-2yeCASS2sZWs9f4xJO90");
        loginUser.setAccount("oviCP5w-2yeCASS2sZWs9f4xJO90");
        loginUser.setLoginType(LoginType.WX);
        loginUser.setUserId(84);

        LoginVO loginVO = LoginVO.builder().userId(84).token(appTokenService.createToken(loginUser, null)).name("进").avatar("https://thirdwx.qlogo.cn/mmopen/vi_32/3SMCDnLMChqt0Flb0icHvwPQmxnrMEtM7jxV6VMLRibY5oA8BhicqCR3RSwvjdZia2ERA2broS7XLOqSmNfpXolbPw/132").build();
        log.info("用户登录模拟::::::userInfo::" + JSONObject.toJSONString(loginVO));
        log.info("token::::::" + JSONObject.toJSONString(loginVO.getToken()));
//        return AjaxResult.success(RespMessageConstants.APP_LOGIN_SUCCESS, loginVO);

    }

}
