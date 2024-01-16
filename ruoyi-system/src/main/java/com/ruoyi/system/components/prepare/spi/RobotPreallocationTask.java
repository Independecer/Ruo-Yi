package com.ruoyi.system.components.prepare.spi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.impl.PreRobotSpeakAllocator;
import com.ruoyi.system.components.prepare.ExecutionParamContext;
import com.ruoyi.system.components.prepare.ExecutionResultContext;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.components.spi.RobotInfoQuery.RobotInfoVO;
import com.ruoyi.system.domain.dto.play.PlayRobotGroupRelation;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import com.ruoyi.system.mapper.PlayRobotGroupRelationMapper;
import com.ruoyi.system.service.PlayMessagePushDetailService;
import lombok.extern.slf4j.Slf4j;

/**
 * 机器人预分配
 * 
 * @author Administrator
 *
 */
@SPI("RobotPreallocationTask")
@Slf4j
public class RobotPreallocationTask implements TaskExecution {

	@Override
	public ExecutionResultContext doExecute(ExecutionParamContext context) {
		final PlayMessagePush messagePush = context.getMessagePush();

		// 查询群内机器人
		final LambdaQueryWrapper<PlayRobotGroupRelation> queryWrapper = new QueryWrapper<PlayRobotGroupRelation>()
				.lambda();
		queryWrapper.eq(PlayRobotGroupRelation::getGroupId, context.getChatroomId());
		queryWrapper.eq(PlayRobotGroupRelation::getState, 1).eq(PlayRobotGroupRelation::getIsDelete, 0);
		List<PlayRobotGroupRelation> relationList = SpringUtils.getBean(PlayRobotGroupRelationMapper.class)
				.selectList(queryWrapper);

		if (CollectionUtils.isEmpty(relationList)) {
			// 群内 查询 不到 演员
			SpringUtils.getBean(PlayMessagePushMapper.class).updateById(messagePush.setRobotAllocationFlag(2));
			context.log(context, PlayLogTyper.Robot_Pre_Alloc, false, "[号预分配] 群("+context.getChatroomId()+")内无演员");
			return ExecutionResultContext.buildError(context, "群内无演员");
		}
		final Map<String, RobotInfoVO> map = new HashMap<>();
		List<String> source = ListTools.extract(relationList, f -> f.getRobotId());
		// 查询所有的detail
		List<PlayMessagePushDetail> details = context.getPushDetails();

		final RobotInfoQuery robotInfoQuery = ServiceLoader.load(RobotInfoQuery.class, "TgRobotInfoQuery");

		for (PlayMessagePushDetail detail : details) {
			RobotInfoVO robot = map.get(detail.getSpokesmanNickname());
			if (robot != null) {
				// 这个发言人 已经分配过了 ， 直接用
				detail.setRobotId(robot.getRobotId());
				detail.setRobotImgUrl(robot.getHeadImg());
				detail.setRobotNickname(robot.getNickName());
				detail.setRobotAcct(robot.getAcct());

				PreRobotSpeakAllocator.Cache.set(messagePush.getPlayId(), messagePush.getGroupId(),
						detail.getSpokesmanNickname(), detail.getRobotId());
				continue;
			}
			if (!CollectionUtils.isEmpty(source)) {
				detail.setRobotId(source.get(0));
				List<RobotInfoVO> robots = robotInfoQuery
						.listById(Arrays.asList(new RobotInfoQuery.RobotInfoDTO(detail.getRobotId())));
				if (!CollectionUtils.isEmpty(robots)) {
					detail.setRobotImgUrl(robots.get(0).getHeadImg());
					detail.setRobotNickname(robots.get(0).getNickName());
					detail.setRobotAcct(robots.get(0).getAcct());
					map.put(detail.getSpokesmanNickname(), robots.get(0));
				} else {
					RobotInfoVO vo = new RobotInfoVO();
					vo.setRobotId(detail.getRobotId());
					map.put(detail.getSpokesmanNickname(), vo);
				}
				source.remove(0);
				log.info("预分配到机器人 {} {}", messagePush, detail);
				PreRobotSpeakAllocator.Cache.set(messagePush.getPlayId(), messagePush.getGroupId(),
						detail.getSpokesmanNickname(), detail.getRobotId());
			}
		}
		// 批量更新
		SpringUtils.getBean(PlayMessagePushDetailService.class).updateBatchById(details);
		// 更新进度
		SpringUtils.getBean(PlayMessagePushMapper.class).updateById(messagePush.setRobotAllocationFlag(1));
		context.log(context, PlayLogTyper.Robot_Pre_Alloc, false, "[号预分配] 群("+context.getChatroomId()+")分配成功");
		return ExecutionResultContext.buildSync(context);
	}

}
