package com.onethinker.bk.service;

import com.onethinker.bk.domain.ImChatGroupUser;

import java.util.List;

/**
 * 聊天群成员Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IImChatGroupUserService {
    /**
     * 查询聊天群成员
     *
     * @param id 聊天群成员主键
     * @return 聊天群成员
     */
    ImChatGroupUser selectImChatGroupUserById(Long id);

    /**
     * 查询聊天群成员列表
     *
     * @param imChatGroupUser 聊天群成员
     * @return 聊天群成员集合
     */
    List<ImChatGroupUser> selectImChatGroupUserList(ImChatGroupUser imChatGroupUser);

    /**
     * 新增聊天群成员
     *
     * @param imChatGroupUser 聊天群成员
     * @return 结果
     */
    int insertImChatGroupUser(ImChatGroupUser imChatGroupUser);

    /**
     * 修改聊天群成员
     *
     * @param imChatGroupUser 聊天群成员
     * @return 结果
     */
    int updateImChatGroupUser(ImChatGroupUser imChatGroupUser);

    /**
     * 批量删除聊天群成员
     *
     * @param ids 需要删除的聊天群成员主键集合
     * @return 结果
     */
    int deleteImChatGroupUserByIds(Long[] ids);

    /**
     * 删除聊天群成员信息
     *
     * @param id 聊天群成员主键
     * @return 结果
     */
    int deleteImChatGroupUserById(Long id);

    /**
     * 新增聊天群成员信息
     *
     * @param plUserId
     */
    void insertImChatGroupByPlUserId(Long id);
}
