package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusCbszdxx;

/**
 * 承包商站点信息Service接口
 * 
 * @author yaowei
 * @date 2021-02-22
 */
public interface IBusCbszdxxService 
{
    /**
     * 查询承包商站点信息
     * 
     * @param id 承包商站点信息ID
     * @return 承包商站点信息
     */
    public BusCbszdxx selectBusCbszdxxById(Long id);

    /**
     * 查询承包商站点信息列表
     * 
     * @param busCbszdxx 承包商站点信息
     * @return 承包商站点信息集合
     */
    public List<BusCbszdxx> selectBusCbszdxxList(BusCbszdxx busCbszdxx);

    /**
     * 新增承包商站点信息
     * 
     * @param busCbszdxx 承包商站点信息
     * @return 结果
     */
    public int insertBusCbszdxx(BusCbszdxx busCbszdxx);

    /**
     * 修改承包商站点信息
     * 
     * @param busCbszdxx 承包商站点信息
     * @return 结果
     */
    public int updateBusCbszdxx(BusCbszdxx busCbszdxx);

    /**
     * 批量删除承包商站点信息
     * 
     * @param ids 需要删除的承包商站点信息ID
     * @return 结果
     */
    public int deleteBusCbszdxxByIds(Long[] ids);

    /**
     * 删除承包商站点信息信息
     * 
     * @param id 承包商站点信息ID
     * @return 结果
     */
    public int deleteBusCbszdxxById(Long id);
}
