package com.ruoyi.onethinker.service;

import java.util.List;
import com.ruoyi.onethinker.domain.ActivityType;

/**
 * 活动类型Service接口
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
public interface IActivityTypeService {
    /**
     * 查询活动类型
     *
     * @param id 活动类型主键
     * @return 活动类型
     */
    public ActivityType selectActivityTypeById(Long id);

    /**
     * 查询活动类型列表
     *
     * @param activityType 活动类型
     * @return 活动类型集合
     */
    public List<ActivityType> selectActivityTypeList(ActivityType activityType);

    /**
     * 新增活动类型
     *
     * @param activityType 活动类型
     * @return 结果
     */
    public int insertActivityType(ActivityType activityType);

    /**
     * 修改活动类型
     *
     * @param activityType 活动类型
     * @return 结果
     */
    public int updateActivityType(ActivityType activityType);

    /**
     * 批量删除活动类型
     *
     * @param ids 需要删除的活动类型主键集合
     * @return 结果
     */
    public int deleteActivityTypeByIds(Long[] ids);

    /**
     * 删除活动类型信息
     *
     * @param id 活动类型主键
     * @return 结果
     */
    public int deleteActivityTypeById(Long id);
}
