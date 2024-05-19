package com.onethinker.onethinker.service;

import com.onethinker.onethinker.domain.Activity;
import com.onethinker.onethinker.dto.ActivityReqDTO;
import com.onethinker.onethinker.dto.ActivityResDTO;

import java.util.List;

/**
 * 活动Service接口
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
public interface IActivityService {
    /**
     * 查询活动
     *
     * @param id 活动主键
     * @return 活动
     */
    public ActivityResDTO selectActivityById(Long id);

    /**
     * 查询活动列表
     *
     * @param activity 活动
     * @return 活动集合
     */
    public List<Activity> selectActivityList(Activity activity);

    /**
     * 新增活动
     *
     * @param activity 活动
     * @return 结果
     */
    public int insertActivity(ActivityReqDTO activity);

    /**
     * 修改活动
     *
     * @param activity 活动
     * @return 结果
     */
    public int updateActivity(Activity activity);

    /**
     * 批量删除活动
     *
     * @param ids 需要删除的活动主键集合
     * @return 结果
     */
    public int deleteActivityByIds(Long[] ids);

    /**
     * 删除活动信息
     *
     * @param id 活动主键
     * @return 结果
     */
    public int deleteActivityById(Long id);

    ActivityResDTO queryActivityMyBatisPuls(Long id);
}
