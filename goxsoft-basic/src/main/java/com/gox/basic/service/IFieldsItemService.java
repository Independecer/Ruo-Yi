package com.gox.basic.service;

import com.gox.basic.domain.form.FieldsItem;
import com.gox.basic.domain.vo.TableFieldVo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IFieldsItemService {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public FieldsItem selectFieldsItemById(Long id);

    public List<TableFieldVo> selectTableFieldByNodeIdAndDeptId(Long nodeId, Long deptId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param fieldsItem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<FieldsItem> selectFieldsItemList(FieldsItem fieldsItem);

    int updateTableFieldsBatch(Iterable<TableFieldVo> fieldVos);

    /**
     * 新增【请填写功能名称】
     *
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    public int insertFieldsItem(FieldsItem fieldsItem);

    /**
     * 批量插入
     *
     * @param fieldsItems
     */
    public int insertFieldsItems(Iterable<FieldsItem> fieldsItems, Long formId);

    /**
     * 修改【请填写功能名称】
     *
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    public int updateFieldsItem(FieldsItem fieldsItem);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    public int deleteFieldsItemByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteFieldsItemById(Long id);

    int deleteFieldsByFormId(Long id);

    List<TableFieldVo> selectTableTitle(Long nodeId, Long deptId);
}
