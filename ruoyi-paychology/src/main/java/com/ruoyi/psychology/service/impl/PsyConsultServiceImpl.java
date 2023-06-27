package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.mapper.PsyConsultMapper;
import com.ruoyi.psychology.service.IPsyConsultServeService;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PsyConsultServiceImpl implements IPsyConsultService {

    @Resource
    private PsyConsultMapper psyConsultMapper;

    @Resource
    private ISysDictTypeService sysDictTypeService;

    @Resource
    private IPsyConsultServeService psyConsultServeService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysConfigService configService;

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

        // 默认初始化所有服务
        List<PsyConsultServe> serves = new ArrayList<>();
        List<SysDictData> dictData = sysDictTypeService.selectDictDataByType("consult_type");
        dictData.forEach(i -> {
            PsyConsultServe serve = new PsyConsultServe();
            serve.setConsultId(id);
            serve.setName(i.getDictLabel());
            serve.setInfo(i.getRemark());
            serve.setPrice(new BigDecimal("100"));
            serve.setDelFlag("0");
            serve.setStatus("1");
            serves.add(serve);
        });
        psyConsultServeService.save(serves);

        return AjaxResult.success(psyConsultMapper.insert(BeanUtil.toBean(req, PsyConsult.class)));
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
