package com.ruoyi.wechat.service;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.dto.WxPayDTO;

/**
 * @User hogan
 * @Time 2022/10/24 17:19
 * @e-mail hkcugwh@163.com
 **/
public interface IWxpayService {

    /**
     * 微信支付
     * @param wxPayDTO
     */
    String pay(WxPayDTO wxPayDTO , LoginDTO loginUser);


}
