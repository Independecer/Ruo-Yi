package com.ruoyi.psychology.mapper;

import com.ruoyi.psychology.vo.PsyConsultConfigByGroupVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 心理咨询师Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
public interface PsyConsultConfigMapper
{
    /**
     * 查询字典
     */
    List<PsyConsultConfigByGroupVO> getConfigByTypes(@Param("types") String[] dictTypes);

}
