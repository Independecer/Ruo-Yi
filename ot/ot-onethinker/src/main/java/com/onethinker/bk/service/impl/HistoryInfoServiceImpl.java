package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.HistoryInfo;
import com.onethinker.bk.mapper.HistoryInfoMapper;
import com.onethinker.bk.service.IHistoryInfoService;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.onethinker.common.constant.BkConstants;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 历史信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class HistoryInfoServiceImpl extends ServiceImpl<HistoryInfoMapper, HistoryInfo> implements IHistoryInfoService {
    @Resource
    private HistoryInfoMapper historyInfoMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IPlatformUserDetailService platformUserDetailService;

    private static final String REDIS_KEY = "HistoryInfo";

    /**
     * 新增历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    @Override
    public int insertHistoryInfo(HistoryInfo historyInfo) {
        historyInfo.setCreateTime(DateUtils.getNowDate());
        int i = historyInfoMapper.insertHistoryInfo(historyInfo);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 修改历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    @Override
    public int updateHistoryInfo(HistoryInfo historyInfo) {
        int i = historyInfoMapper.updateHistoryInfo(historyInfo);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    @Override
    public Map<String, Object> getHistoryInfo() {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheMap(redisKey);
        }
        Map<String, Object> result = new HashMap<>();
        // 从缓存中获取统计数
        Map<String, Object> history = queryHistoryInfoByRedisCache();

        List<HistoryInfo> infoList = historyInfoMapper.selectHistoryInfoList(new HistoryInfo());

        result.put(BkConstants.IP_HISTORY_PROVINCE, history.get(BkConstants.IP_HISTORY_PROVINCE));
        result.put(BkConstants.IP_HISTORY_IP, history.get(BkConstants.IP_HISTORY_IP));
        result.put(BkConstants.IP_HISTORY_COUNT, history.get(BkConstants.IP_HISTORY_COUNT));
        List<Map<String, Object>> ipHistoryCount = (List<Map<String, Object>>) history.get(BkConstants.IP_HISTORY_HOUR);
        result.put("ip_count_yest", ipHistoryCount.stream().map(m -> m.get("ip")).distinct().count());
        result.put("username_yest", ipHistoryCount.stream().map(m -> {
            Object userId = m.get("user_id");
            if (userId != null) {
                PlatformUserDetail user = platformUserDetailService.getPlatFormUserDetailByUserId(Long.valueOf(userId.toString()));
                if (user != null) {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("avatar", user.getAvatarUrl());
                    userInfo.put("username", user.getUsername());
                    return userInfo;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()));
        result.put("ip_count_today", infoList.stream().map(HistoryInfo::getIp).distinct().count());
        result.put("username_today", infoList.stream().map(m -> {
            Long userId = m.getUserId();
            if (userId != null) {
                PlatformUserDetail user = platformUserDetailService.getPlatFormUserDetailByUserId(userId);
                if (user != null) {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("avatar", user.getAvatarUrl());
                    userInfo.put("username", user.getUsername());
                    return userInfo;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList()));

        List<Map<String, Object>> list = infoList.stream()
                .map(HistoryInfo::getProvince).filter(Objects::nonNull)
                .collect(Collectors.groupingBy(m -> m, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("province", entry.getKey());
                    map.put("num", entry.getValue());
                    return map;
                })
                .sorted((o1, o2) -> Long.valueOf(o2.get("num").toString()).compareTo(Long.valueOf(o1.get("num").toString())))
                .collect(Collectors.toList());

        result.put("province_today", list);
        redisCache.setCacheMap(redisKey, result);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return result;
    }

    private Map<String, Object> queryHistoryInfoByRedisCache() {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "_statistics";
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheMap(redisKey);
        }
        Map<String, Object> history = new HashMap<>();
        history.put(BkConstants.IP_HISTORY_PROVINCE, historyInfoMapper.getHistoryByProvince());
        history.put(BkConstants.IP_HISTORY_IP, historyInfoMapper.getHistoryByIp());
        history.put(BkConstants.IP_HISTORY_HOUR, historyInfoMapper.getHistoryBy24Hour());
        history.put(BkConstants.IP_HISTORY_COUNT, historyInfoMapper.getHistoryCount());
        redisCache.setCacheObject(redisKey, history, 1, TimeUnit.DAYS);
        return history;
    }
}
