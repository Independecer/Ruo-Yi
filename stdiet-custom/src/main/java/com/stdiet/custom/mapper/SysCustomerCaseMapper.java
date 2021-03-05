package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysCustomerCase;

/**
 * 客户案例管理Mapper接口
 *
 * @author xiezhijun
 * @date 2021-03-04
 */
public interface SysCustomerCaseMapper
{
    /**
     * 查询客户案例管理
     *
     * @param id 客户案例管理ID
     * @return 客户案例管理
     */
    public SysCustomerCase selectSysCustomerCaseById(Long id);

    /**
     * 查询客户案例管理列表
     *
     * @param sysCustomerCase 客户案例管理
     * @return 客户案例管理集合
     */
    public List<SysCustomerCase> selectSysCustomerCaseList(SysCustomerCase sysCustomerCase);

    /**
     * 新增客户案例管理
     *
     * @param sysCustomerCase 客户案例管理
     * @return 结果
     */
    public int insertSysCustomerCase(SysCustomerCase sysCustomerCase);

    /**
     * 修改客户案例管理
     *
     * @param sysCustomerCase 客户案例管理
     * @return 结果
     */
    public int updateSysCustomerCase(SysCustomerCase sysCustomerCase);

    /**
     * 删除客户案例管理
     *
     * @param id 客户案例管理ID
     * @return 结果
     */
    public int deleteSysCustomerCaseById(Long id);

    /**
     * 批量删除客户案例管理
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCustomerCaseByIds(Long[] ids);
}