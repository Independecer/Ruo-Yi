package com.ruoyi.system.service;

import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 15:25
 * @Description :
 */
public interface PlayExecutionLogService {

	/**
	 * 查询剧本执行日志列表
	 *
	 * @param playId
	 * @return
	 */
	List<PlayExecutionLog> listByPlayId(String playId);

	/**
	 * 保存日志
	 *
	 * @param executionLog
	 */
	void saveLog(PlayExecutionLog log);
	
	
	public static void robotPackLog(String playId, String groupId, String robotId ,String conetent , String errMsg){
		PlayExecutionLog log = new PlayExecutionLog();
		log.setGroupId(groupId);
		log.setPlayId(playId);
		// 状态 0-成功（默认） 1-失败
		log.setState(StringUtils.isEmpty(errMsg) ? 0 : 1);
		log.setType(PlayLogTyper.Robot_Settings);
		log.setRobotId(robotId);
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}

	/**
	 * title: 发言人包装日志记录
	 * 
	 * @param playId
	 * @param groupId
	 * @param robotId
	 * @param success
	 * @param funcAlias
	 */
	public static void robotPackLog(String playId, String groupId, String robotId, String errMsg, String opt,
			String funcAlias, boolean isSync) {
		PlayExecutionLog log = new PlayExecutionLog();
		log.setGroupId(groupId);
		log.setPlayId(playId);
		log.setOpt(opt);
		// 状态 0-成功（默认） 1-失败
		log.setState(StringUtils.isEmpty(errMsg) ? 0 : 1);
		log.setType(PlayLogTyper.Robot_Settings);
		log.setRobotId(robotId);
		if (isSync && !StringUtils.isEmpty(errMsg)) {
			// 同步请求 && 失败
			log.setContent(String.format("【发言人包装-%s】 群%s 号%s 同步请求失败，原因：%s", funcAlias, groupId, robotId, errMsg));
		}

		if (isSync && StringUtils.isEmpty(errMsg)) {
			// 同步请求 & 成功
			log.setContent(String.format("【发言人包装-%s】 群%s 号%s 请求成功，操作码：%s", funcAlias, groupId, robotId, opt));
		}

		if (!isSync && !StringUtils.isEmpty(errMsg)) {
			// 回调请求 && 失败
			log.setContent(
					String.format("【发言人包装-%s】 群%s 号%s 回调失败，操作码： %s，原因：%s", funcAlias, groupId, robotId, opt, errMsg));
		}

		if (!isSync && StringUtils.isEmpty(errMsg)) {
			// 回调请求 && 成功
			log.setContent(String.format("【发言人包装-%s】 群%s 号%s 回调成功，操作码： %s", funcAlias, groupId, robotId, opt));
		}
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}
}
