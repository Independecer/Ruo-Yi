package com.onethinker.mail.service;

/**
 * @author : yangyouqi
 * @date : 2024/1/16 11:20
 */
public interface IMailService {

    /**
     * 发送验证码处理
     *
     * @param place
     */
    void sendMailCode(String place, int code);
}
