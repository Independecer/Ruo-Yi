package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.event.publish.ConsultServePublisher;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.vo.DateLimitUtilVO;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.domain.PsyConsultWork;
import com.ruoyi.psychology.dto.PsyConsultInfoDTO;
import com.ruoyi.psychology.mapper.PsyConsultMapper;
import com.ruoyi.psychology.request.PsyAdminConsultReq;
import com.ruoyi.psychology.request.PsyConsultReq;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.service.IPsyConsultServeConfigService;
import com.ruoyi.psychology.service.IPsyConsultServeService;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.psychology.service.IPsyConsultWorkService;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PsyConsultServiceImpl implements IPsyConsultService {

    @Resource
    private PsyConsultMapper psyConsultMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private ConsultServePublisher consultServePublisher;

    @Resource
    private IPsyConsultServeService psyConsultServeService;

    @Resource
    private IPsyConsultServeConfigService psyConsultServeConfigService;

    @Resource
    private IPsyConsultWorkService psyConsultWorkService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysConfigService configService;


    @Override
    public PsyConsultInfoDTO getConsultInfoByServe(Long cId, Long sId) {
        PsyConsultInfoDTO vo = new PsyConsultInfoDTO();
        PsyConsultServeConfigVO serve = psyConsultServeConfigService.getOne(sId);
        PsyConsultVO consult = getOne(cId);

        PsyConsultWorkVO req = new PsyConsultWorkVO();
        req.setConsultId(cId);
        req.setStatus("0");
        // t+6
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        calendar.add(Calendar.DATE, 7);
        Date end = calendar.getTime();
//        req.setTimeStart(start);
//        req.setTimeEnd(end);
        List<PsyConsultWork> works = psyConsultWorkService.getList(req);
//        List<PsyConsultWork> collect = works.stream().filter(i -> i.getNum() > 0).collect(Collectors.toList());

//        vo.setWorks(collect);
        vo.setServe(serve);
        vo.setConsult(consult);
        return vo;
    }

    @Override
    public List<PsyConsult> search(PsyConsultReq req) {
        List<PsyConsult> list = psyConsultMapper.search(req);
        // 处理way
        if (!CollectionUtils.isEmpty(req.getWay()) && !CollectionUtils.isEmpty(list)) {
            list = list.stream().filter(i -> {
                if (StringUtils.isNotEmpty(i.getWayStr())) {
                    Set<String> now = Stream.of(StringUtils.split(i.getWayStr().trim(), ",")).collect(Collectors.toSet());
                    now.retainAll(req.getWay());
                    return !now.isEmpty();
                }
                return true;
            }).collect(Collectors.toList());
        }

        return list;
    }

    @Override
    public PsyConsultVO getOne(Long id) {
        return BeanUtil.toBean(psyConsultMapper.selectById(id), PsyConsultVO.class);
    }

    @Override
    public List<PsyConsult> getList(PsyConsultVO req) {
        if (SecurityUtils.getUserIdByNotAdmin() != 0L) {
            req.setUserId(SecurityUtils.getUserIdByNotAdmin());
        }
        req.setDelFlag("0");
        return psyConsultMapper.getList(req);
    }

    @Override
    public List<PsyConsult> getList(PsyAdminConsultReq req) {
        LambdaQueryWrapper<PsyConsult> wp = Wrappers.lambdaQuery();
        wp.eq(PsyConsult::getDelFlag, "0");
        if (StringUtils.isNotEmpty(req.getUserName())) {
            wp.eq(PsyConsult::getUserName, req.getUserName());
        }

        if (StringUtils.isNotEmpty(req.getStatus())) {
            wp.eq(PsyConsult::getStatus, req.getStatus());
        }

        if (StrUtil.isNotBlank(req.getDateLimit())) {
            DateLimitUtilVO dateLimit = NewDateUtil.getDateLimit(req.getDateLimit());
            wp.between(PsyConsult::getCreateTime, dateLimit.getStartTime(), dateLimit.getEndTime());
        }

        return psyConsultMapper.selectList(wp);
    }

    @Override
    public AjaxResult refConsultServe(PsyRefConsultServeReq req) {
        PsyConsult consult = psyConsultMapper.selectById(req.getConsultId());
        if (consult == null) {
            return AjaxResult.error("关联服务失败,咨询师信息异常");
        }

        Boolean execute = transactionTemplate.execute(e -> {
            if (psyConsultServeConfigService.refConsultServe(req) == 0 || psyConsultServeService.batchServeRef(req) == 0) {
                return Boolean.FALSE;
            }

            int count = psyConsultServeService.getRefCountByConsultId(req.getConsultId());
            consult.setServe(count);
            psyConsultMapper.updateById(consult);
            return Boolean.TRUE;
        });
        if (!execute) {
            return AjaxResult.error("关联服务失败,服务信息异常");
        }

        consultServePublisher.publish(req);
        return AjaxResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult add(PsyConsultVO req) {
        // 新增用户
        SysUser user = convertToUser(req);
        user.setPassword(SecurityUtils.encryptPassword(configService.selectConfigByKey("sys.user.initPassword")));

        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        // 初始化id
        Long id = IDhelper.getNextId();
        req.setId(id);

        // 新增用户
        userService.insertUser(user);
        req.setUserId(user.getUserId());

        converToStr(req);
        return AjaxResult.success(psyConsultMapper.insert(BeanUtil.toBean(req, PsyConsult.class)));
    }

    @Override
    public void updateNum(Long id, int num) {
        PsyConsultVO one = getOne(id);
        int i = one.getWorkNum() + num;
        one.setWorkNum(Math.max(i, 0));
        updateByApp(one);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByApp(PsyConsultVO req) {
        psyConsultMapper.updateById(BeanUtil.toBean(req, PsyConsult.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult update(PsyConsultVO req) {

        SysUser sysUser = userService.selectUserByUserName(req.getUserName());
        if (StringUtils.isNotEmpty(req.getPhonenumber()) && !req.getPhonenumber().equals(sysUser.getPhonenumber())) {
            sysUser.setPhonenumber(req.getPhonenumber());
            if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(sysUser))) {
                return AjaxResult.error("修改用户'" + req.getUserName() + "'失败，手机号码已存在");
            }
        }
        else if (StringUtils.isNotEmpty(req.getEmail()) && !req.getEmail().equals(sysUser.getEmail()))
        {
            sysUser.setEmail(req.getUserName());
            if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(sysUser))) {
                return AjaxResult.error("修改用户'" + req.getUserName() + "'失败，邮箱账号已存在");
            }
        }
        sysUser.setSex(req.getSex());
        sysUser.setAvatar(req.getAvatar());
        sysUser.setUpdateBy(SecurityUtils.getUsername());

        converToStr(req);
        return AjaxResult.success(psyConsultMapper.updateById(BeanUtil.toBean(req, PsyConsult.class)));
    }

    private SysUser convertToUser(PsyConsultVO req) {
        SysUser user = new SysUser();
        user.setNickName(req.getUserName());
        user.setUserName(req.getUserName());
        user.setSex(req.getSex());
        user.setEmail(req.getEmail());
        user.setPhonenumber(req.getPhonenumber());
        user.setAvatar(req.getAvatar());
        user.setUpdateTime(DateUtils.getNowDate());
        user.setCreateBy(SecurityUtils.getUsername());
        user.setUpdateBy(SecurityUtils.getUsername());
        return user;
    }

    private void converToStr(PsyConsultVO req) {
        HashSet<String> tab = new HashSet<>();
        HashSet<String> way = new HashSet<>();
        if (StringUtils.isNotEmpty(req.getWay())) {
            List<String> jsonArray = JSON.parseArray(req.getWay(), String.class);
            jsonArray.forEach(a -> {
                List<String> json = JSON.parseArray(a, String.class);
                tab.add(json.get(0));
                way.add(json.get(1));
            });
        }
        req.setTabs(String.join(",", tab));
        req.setWayStr(String.join(",", way));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultMapper.tombstonedByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultMapper.deleteById(id);
    }
}
