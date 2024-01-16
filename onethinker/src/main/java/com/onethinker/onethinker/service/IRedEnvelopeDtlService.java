package com.onethinker.onethinker.service;

import java.util.List;
import com.onethinker.onethinker.domain.RedEnvelopeDtl;
import com.onethinker.onethinker.dto.RedEnvelopeCtrlDTO;

/**
 * 红包明细Service接口
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
public interface IRedEnvelopeDtlService {
    /**
     * 查询红包明细
     *
     * @param id 红包明细主键
     * @return 红包明细
     */
    public RedEnvelopeDtl selectRedEnvelopeDtlById(Long id);

    /**
     * 查询红包明细列表
     *
     * @param redEnvelopeDtl 红包明细
     * @return 红包明细集合
     */
    public List<RedEnvelopeDtl> selectRedEnvelopeDtlList(RedEnvelopeDtl redEnvelopeDtl);

    /**
     * 新增红包明细
     *
     * @param redEnvelopeDtl 红包明细
     * @return 结果
     */
    public int insertRedEnvelopeDtl(RedEnvelopeCtrlDTO redEnvelopeDtl);

    /**
     * 修改红包明细
     *
     * @param redEnvelopeDtl 红包明细
     * @return 结果
     */
    public int updateRedEnvelopeDtl(RedEnvelopeDtl redEnvelopeDtl,Integer qrCodeStatus);

    /**
     * 批量删除红包明细
     *
     * @param ids 需要删除的红包明细主键集合
     * @return 结果
     */
    public int deleteRedEnvelopeDtlByIds(Long[] ids);

    /**
     * 删除红包明细信息
     *
     * @param id 红包明细主键
     * @return 结果
     */
    public int deleteRedEnvelopeDtlById(Long id);

    /**
     * 创建二维码内容
     * @param redEnvelopeDtl
     * @return
     */
    String createQrCodeBaseInfo(RedEnvelopeDtl redEnvelopeDtl);
}
