package com.onethinker.wechat.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author : yangyouqi
 * @date : 2024/1/12 11:37
 * 对接小程序相关接口能力
 */
public interface IMinWechatService {

    /**
     * 获取登录后的session信息.
     *
     * @param code 登录时获取的 code
     * @return .
     */
    WxMaJscode2SessionResult getSessionInfo(String code);


    /**
     * 解密用户敏感数据.
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param ivStr         加密算法的初始向量
     */
    WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr);

    /**
     * <pre>
     * 发送订阅消息
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
     * </pre>
     *
     * @param subscribeMessage 订阅消息
     * @throws WxErrorException .
     */
    void sendSubscribeMsg(WxMaSubscribeMessage subscribeMessage) throws WxErrorException;
}
