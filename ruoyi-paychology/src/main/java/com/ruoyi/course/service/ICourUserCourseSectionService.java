package com.ruoyi.course.service;

import java.util.List;

import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourUserCourseSection;

/**
 * 用户-课程-章节关系Service接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface ICourUserCourseSectionService 
{
    /**
     * 查询用户-课程-章节关系
     * 
     * @param id 用户-课程-章节关系主键
     * @return 用户-课程-章节关系
     */
    public CourUserCourseSection selectCourUserCourseSectionById(Integer id);

    /**
     * 查询用户-课程-章节关系列表
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 用户-课程-章节关系集合
     */
    public List<CourUserCourseSection> selectCourUserCourseSectionList(CourUserCourseSection courUserCourseSection);

    /**
     * 根据用户ID查询课程列表
     *
     * @param userId 用户ID
     * @return 课程集合
     */
    public List<CourCourse> getCourseListByUserId(Integer userId);

    /**
     * 新增用户-课程-章节关系
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 结果
     */
    public int insertCourUserCourseSection(CourUserCourseSection courUserCourseSection);

    /**
     * 修改用户-课程-章节关系
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 结果
     */
    public int updateCourUserCourseSection(CourUserCourseSection courUserCourseSection);

    /**
     * 批量删除用户-课程-章节关系
     * 
     * @param ids 需要删除的用户-课程-章节关系主键集合
     * @return 结果
     */
    public int deleteCourUserCourseSectionByIds(Integer[] ids);

    /**
     * 删除用户-课程-章节关系信息
     * 
     * @param id 用户-课程-章节关系主键
     * @return 结果
     */
    public int deleteCourUserCourseSectionById(Integer id);
}
