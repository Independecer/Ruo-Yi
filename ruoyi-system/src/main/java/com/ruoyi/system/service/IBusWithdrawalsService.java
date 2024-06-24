package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BusWithdrawals;

/**
 * 记录用户的提现信息Service接口
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
public interface IBusWithdrawalsService 
{
    /**
     * 查询记录用户的提现信息
     * 
     * @param withdrawalId 记录用户的提现信息主键
     * @return 记录用户的提现信息
     */
    public BusWithdrawals selectBusWithdrawalsByWithdrawalId(Long withdrawalId);

    /**
     * 查询记录用户的提现信息列表
     * 
     * @param busWithdrawals 记录用户的提现信息
     * @return 记录用户的提现信息集合
     */
    public List<BusWithdrawals> selectBusWithdrawalsList(BusWithdrawals busWithdrawals);

    /**
     * 新增记录用户的提现信息
     * 
     * @param busWithdrawals 记录用户的提现信息
     * @return 结果
     */
    public int insertBusWithdrawals(BusWithdrawals busWithdrawals);

    /**
     * 修改记录用户的提现信息
     * 
     * @param busWithdrawals 记录用户的提现信息
     * @return 结果
     */
    public int updateBusWithdrawals(BusWithdrawals busWithdrawals);

    /**
     * 批量删除记录用户的提现信息
     * 
     * @param withdrawalIds 需要删除的记录用户的提现信息主键集合
     * @return 结果
     */
    public int deleteBusWithdrawalsByWithdrawalIds(Long[] withdrawalIds);

    /**
     * 删除记录用户的提现信息信息
     * 
     * @param withdrawalId 记录用户的提现信息主键
     * @return 结果
     */
    public int deleteBusWithdrawalsByWithdrawalId(Long withdrawalId);
}
