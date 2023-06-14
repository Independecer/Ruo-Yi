package com.ruoyi.psychology.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.psychology.mapper.PsyAppointmentMapper;
import com.ruoyi.psychology.domain.PsyAppointment;
import com.ruoyi.psychology.service.IPsyAppointmentService;

/**
 * 咨询师预约Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@Service
public class PsyAppointmentServiceImpl implements IPsyAppointmentService 
{
    @Autowired
    private PsyAppointmentMapper psyAppointmentMapper;

    /**
     * 查询咨询师预约
     * 
     * @param id 咨询师预约主键
     * @return 咨询师预约
     */
    @Override
    public PsyAppointment selectPsyAppointmentById(Integer id)
    {
        return psyAppointmentMapper.selectPsyAppointmentById(id);
    }

    /**
     * 查询咨询师预约列表
     * 
     * @param psyAppointment 咨询师预约
     * @return 咨询师预约
     */
    @Override
    public List<PsyAppointment> selectPsyAppointmentList(PsyAppointment psyAppointment)
    {
        return psyAppointmentMapper.selectPsyAppointmentList(psyAppointment);
    }

    /**
     * 新增咨询师预约
     * 
     * @param psyAppointment 咨询师预约
     * @return 结果
     */
    @Override
    public int insertPsyAppointment(PsyAppointment psyAppointment)
    {
        return psyAppointmentMapper.insertPsyAppointment(psyAppointment);
    }

    /**
     * 修改咨询师预约
     * 
     * @param psyAppointment 咨询师预约
     * @return 结果
     */
    @Override
    public int updatePsyAppointment(PsyAppointment psyAppointment)
    {
        return psyAppointmentMapper.updatePsyAppointment(psyAppointment);
    }

    /**
     * 批量删除咨询师预约
     * 
     * @param ids 需要删除的咨询师预约主键
     * @return 结果
     */
    @Override
    public int deletePsyAppointmentByIds(Integer[] ids)
    {
        return psyAppointmentMapper.deletePsyAppointmentByIds(ids);
    }

    /**
     * 删除咨询师预约信息
     * 
     * @param id 咨询师预约主键
     * @return 结果
     */
    @Override
    public int deletePsyAppointmentById(Integer id)
    {
        return psyAppointmentMapper.deletePsyAppointmentById(id);
    }
}
