package com.ruoyi.gauge.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gauge.mapper.PsyOrderMapper;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.service.IPsyOrderService;

/**
 * 心理测评订单信息Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-12
 */
@Service
public class PsyOrderServiceImpl implements IPsyOrderService {
    @Autowired
    private PsyOrderMapper psyOrderMapper;

    /**
     * 查询心理测评订单信息
     *
     * @param id 心理测评订单信息主键
     * @return 心理测评订单信息
     */
    @Override
    public PsyOrder selectPsyOrderById(Long id) {
        return psyOrderMapper.selectPsyOrderById(id);
    }

    /**
     * 查询心理测评订单信息列表
     *
     * @param psyOrder 心理测评订单信息
     * @return 心理测评订单信息
     */
    @Override
    public List<PsyOrder> selectPsyOrderList(PsyOrder psyOrder) {
        return psyOrderMapper.selectPsyOrderList(psyOrder);
    }

    /**
     * 新增心理测评订单信息
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    @Override
    public int insertPsyOrder(PsyOrder psyOrder) {
        psyOrder.setCreateTime(DateUtils.getNowDate());
        return psyOrderMapper.insertPsyOrder(psyOrder);
    }

    /**
     * 修改心理测评订单信息
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    @Override
    public int updatePsyOrder(PsyOrder psyOrder) {
        return psyOrderMapper.updatePsyOrder(psyOrder);
    }

    /**
     * 批量删除心理测评订单信息
     *
     * @param ids 需要删除的心理测评订单信息主键
     * @return 结果
     */
    @Override
    public int deletePsyOrderByIds(Long[] ids) {
        return psyOrderMapper.deletePsyOrderByIds(ids);
    }

    /**
     * 删除心理测评订单信息信息
     *
     * @param id 心理测评订单信息主键
     * @return 结果
     */
    @Override
    public int deletePsyOrderById(Long id) {
        return psyOrderMapper.deletePsyOrderById(id);
    }
}
