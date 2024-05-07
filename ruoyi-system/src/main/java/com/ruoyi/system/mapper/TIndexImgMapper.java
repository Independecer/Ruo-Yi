package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TIndexImg;

/**
 * 首页图片配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-24
 */
public interface TIndexImgMapper 
{
    /**
     * 查询首页图片配置
     * 
     * @param id 首页图片配置主键
     * @return 首页图片配置
     */
    public TIndexImg selectTIndexImgById(Long id);

    /**
     * 查询首页图片配置列表
     * 
     * @param tIndexImg 首页图片配置
     * @return 首页图片配置集合
     */
    public List<TIndexImg> selectTIndexImgList(TIndexImg tIndexImg);

    /**
     * 新增首页图片配置
     * 
     * @param tIndexImg 首页图片配置
     * @return 结果
     */
    public int insertTIndexImg(TIndexImg tIndexImg);

    /**
     * 修改首页图片配置
     * 
     * @param tIndexImg 首页图片配置
     * @return 结果
     */
    public int updateTIndexImg(TIndexImg tIndexImg);

    /**
     * 删除首页图片配置
     * 
     * @param id 首页图片配置主键
     * @return 结果
     */
    public int deleteTIndexImgById(Long id);

    /**
     * 批量删除首页图片配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTIndexImgByIds(Long[] ids);
}
