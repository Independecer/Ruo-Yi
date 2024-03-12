package com.baoli.sysmanage.service;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizCarmodel;

/**
 * 汽车型号Service接口
 * 
 * @author niujs
 * @date 2024-03-12
 */
public interface IBaoliBizCarmodelService 
{
    /**
     * 查询汽车型号
     * 
     * @param id 汽车型号主键
     * @return 汽车型号
     */
    public BaoliBizCarmodel selectBaoliBizCarmodelById(Long id);

    /**
     * 查询汽车型号列表
     * 
     * @param baoliBizCarmodel 汽车型号
     * @return 汽车型号集合
     */
    public List<BaoliBizCarmodel> selectBaoliBizCarmodelList(BaoliBizCarmodel baoliBizCarmodel);

    /**
     * 新增汽车型号
     * 
     * @param baoliBizCarmodel 汽车型号
     * @return 结果
     */
    public int insertBaoliBizCarmodel(BaoliBizCarmodel baoliBizCarmodel);

    /**
     * 修改汽车型号
     * 
     * @param baoliBizCarmodel 汽车型号
     * @return 结果
     */
    public int updateBaoliBizCarmodel(BaoliBizCarmodel baoliBizCarmodel);

    /**
     * 批量删除汽车型号
     * 
     * @param ids 需要删除的汽车型号主键集合
     * @return 结果
     */
    public int deleteBaoliBizCarmodelByIds(Long[] ids);

    /**
     * 删除汽车型号信息
     * 
     * @param id 汽车型号主键
     * @return 结果
     */
    public int deleteBaoliBizCarmodelById(Long id);
}
