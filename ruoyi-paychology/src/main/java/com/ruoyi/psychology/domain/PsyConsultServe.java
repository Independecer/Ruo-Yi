package com.ruoyi.psychology.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 咨询服务对象 psy_consult_serve
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_consult_serve")
public class PsyConsultServe implements Serializable
{
    private static final long serialVersionUID = 5551542030169381349L;

    /** 服务 */
    private Long serveId;

    /** 咨询师 */
    private Long consultId;

}
