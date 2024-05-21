package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DiIntegrityArchives;

/**
 * 廉政档案Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public interface DiIntegrityArchivesMapper 
{
    /**
     * 查询廉政档案
     * 
     * @param id 廉政档案主键
     * @return 廉政档案
     */
    public DiIntegrityArchives selectDiIntegrityArchivesById(Long id);

    /**
     * 查询廉政档案列表
     * 
     * @param diIntegrityArchives 廉政档案
     * @return 廉政档案集合
     */
    public List<DiIntegrityArchives> selectDiIntegrityArchivesList(DiIntegrityArchives diIntegrityArchives);

    /**
     * 新增廉政档案
     * 
     * @param diIntegrityArchives 廉政档案
     * @return 结果
     */
    public int insertDiIntegrityArchives(DiIntegrityArchives diIntegrityArchives);

    /**
     * 修改廉政档案
     * 
     * @param diIntegrityArchives 廉政档案
     * @return 结果
     */
    public int updateDiIntegrityArchives(DiIntegrityArchives diIntegrityArchives);

    /**
     * 删除廉政档案
     * 
     * @param id 廉政档案主键
     * @return 结果
     */
    public int deleteDiIntegrityArchivesById(Long id);

    /**
     * 批量删除廉政档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDiIntegrityArchivesByIds(Long[] ids);
}
