package com.ruoyi.course.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.mapper.CourOrderMapper;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.course.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 课程订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-17
 */
@Service
public class CourOrderServiceImpl implements ICourOrderService 
{
    @Autowired
    private CourOrderMapper courOrderMapper;

    /**
     * 查询课程订单
     * 
     * @param id 课程订单主键
     * @return 课程订单
     */
    @Override
    public CourOrder selectCourOrderById(Integer id)
    {
        return courOrderMapper.selectCourOrderById(id);
    }

    /**
     * 查询课程订单
     *
     * @param orderId 课程订单编号
     * @return 课程订单
     */
    @Override
    public CourOrder selectCourOrderByOrderId(String orderId)
    {
        return courOrderMapper.selectCourOrderByOrderId(orderId);
    }

    /**
     * 查询课程订单列表
     * 
     * @param courOrder 课程订单
     * @return 课程订单
     */
    @Override
    public List<CourOrder> selectCourOrderList(CourOrder courOrder)
    {
        return courOrderMapper.selectCourOrderList(courOrder);
    }

    /**
     * 新增课程订单
     * 
     * @param courOrder 课程订单
     * @return 结果
     */
    @Override
    public int insertCourOrder(CourOrder courOrder)
    {
        courOrder.setCreateTime(DateUtils.getNowDate());
        return courOrderMapper.insertCourOrder(courOrder);
    }

    /**
     * 修改课程订单
     * 
     * @param courOrder 课程订单
     * @return 结果
     */
    @Override
    public int updateCourOrder(CourOrder courOrder)
    {
        return courOrderMapper.updateCourOrder(courOrder);
    }

    /**
     * 批量删除课程订单
     * 
     * @param ids 需要删除的课程订单主键
     * @return 结果
     */
    @Override
    public int deleteCourOrderByIds(Integer[] ids)
    {
        return courOrderMapper.deleteCourOrderByIds(ids);
    }

    /**
     * 删除课程订单信息
     * 
     * @param id 课程订单主键
     * @return 结果
     */
    @Override
    public int deleteCourOrderById(Integer id)
    {
        return courOrderMapper.deleteCourOrderById(id);
    }

    /**
     * 查询用户是否购买课程
     *
     * @param userId 用户ID
     * @param courseId 课程订单主键
     * @return 结果
     */
    @Override
    public List<CourOrder> selectCourOrderByUser(Integer userId, Integer courseId) {

        CourOrder courOrder = CourOrder.builder()
                .courseId(courseId)
                .userId(userId)
                .status(CourConstant.COUR_ORDER_STATUE_FINISHED)
                .build();

        return courOrderMapper.selectCourOrderList(courOrder);
    }

    /**
     * 根据订单ID查询课程订单详情
     *
     * @param id 课程订单ID
     * @return 课程订单详情
     */
    @Override
    public OrderVO getOrderDetailById(Integer id) {
        return courOrderMapper.getOrderDetailById(id);
    }

    /**
     * 生成课程订单
     *
     * @param courOrder 课程订单
     * @return 生成的订单对象
     */
    public CourOrder generateCourOrder(CourOrder courOrder) {
        courOrder.setCreateTime(DateUtils.getNowDate()); // 下单时间
        int code = courOrderMapper.insertCourOrder(courOrder);
        if (code == 1) {
            return selectCourOrderByOrderId(courOrder.getOrderId());
        }
        return null;
    }
}
