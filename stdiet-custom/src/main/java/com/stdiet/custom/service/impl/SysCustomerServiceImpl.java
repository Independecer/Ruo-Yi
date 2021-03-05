package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerHealthy;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.mapper.SysCustomerMapper;
import com.stdiet.custom.mapper.SysCustomerPhysicalSignsMapper;
import com.stdiet.custom.service.ISysCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户信息Service业务层处理
 *
 * @author xzj
 * @date 2020-12-31
 */
@Service
@Transactional
public class SysCustomerServiceImpl implements ISysCustomerService {
    @Autowired
    private SysCustomerMapper sysCustomerMapper;

    @Autowired
    private SysCustomerPhysicalSignsMapper sysCustomerPhysicalSignsMapper;

    @Autowired
    private SysCustomerHealthyServiceImpl sysCustomerHealthyService;

    @Autowired
    private SysCustomerPhysicalSignsServiceImpl sysCustomerPhysicalSignsService;

    /**
     * 查询客户信息
     *
     * @param id 客户信息ID
     * @return 客户信息
     */
    @Override
    public SysCustomer selectSysCustomerById(Long id) {
        return sysCustomerMapper.selectSysCustomerById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param sysCustomer 客户信息
     * @return 客户信息
     */
    @Override
    public List<SysCustomer> selectSysCustomerList(SysCustomer sysCustomer) {
        return sysCustomerMapper.selectSysCustomerList(sysCustomer);
    }

    /**
     * 新增客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    @Override
    public int insertSysCustomer(SysCustomer sysCustomer) {
        sysCustomer.setCreateTime(DateUtils.getNowDate());
        return sysCustomerMapper.insertSysCustomer(sysCustomer);
    }

    /**
     * 修改客户信息
     *
     * @param sysCustomer 客户信息
     * @return 结果
     */
    @Override
    public int updateSysCustomer(SysCustomer sysCustomer) {
        sysCustomer.setUpdateTime(DateUtils.getNowDate());
        return sysCustomerMapper.updateSysCustomer(sysCustomer);
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerByIds(Long[] ids) {
        return sysCustomerMapper.deleteSysCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysCustomerById(Long id) {
        return sysCustomerMapper.deleteSysCustomerById(id);
    }

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return 结果
     */
    public SysCustomer getCustomerByPhone(String phone) {
        return sysCustomerMapper.getCustomerByPhone(phone);
    }

    /**
     * 判断客户手机号是否已存在
     *
     * @param sysCustomer
     * @return
     */
    public boolean isCustomerExistByPhone(SysCustomer sysCustomer) {
        if (sysCustomer.getId() != null) {
            if (StringUtils.isNotEmpty(sysCustomer.getPhone())) {
                SysCustomer phoneCustomer = getCustomerByPhone(sysCustomer.getPhone());
                return phoneCustomer != null && phoneCustomer.getId().intValue() != sysCustomer.getId().intValue();
            }
        } else {
            if (StringUtils.isNotEmpty(sysCustomer.getPhone())) {
                return getCustomerByPhone(sysCustomer.getPhone()) != null;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getPhysicalSignsById(Long id) {
        Map<String, Object> result = new HashMap<>();
        String key = "customerHealthy";
        result.put("type", 0);
        //查询健康评估信息
        SysCustomerHealthy sysCustomerHealthy = sysCustomerHealthyService.selectSysCustomerHealthyByCustomerId(id);
        if (sysCustomerHealthy != null) {
           /* if (StringUtils.isNotEmpty(sysCustomerHealthy.getPhone())) {
                sysCustomerHealthy.setPhone(StringUtils.hiddenPhoneNumber(sysCustomerHealthy.getPhone()));
            }*/
            result.put(key, sysCustomerHealthy);
        } else {
            //查询体征信息
            SysCustomerPhysicalSigns sysCustomerPhysicalSigns = sysCustomerPhysicalSignsService.selectSysCustomerPhysicalSignsByCusId(id);
            if (sysCustomerPhysicalSigns != null) {
               /* if (StringUtils.isNotEmpty(sysCustomerPhysicalSigns.getPhone())) {
                    sysCustomerPhysicalSigns.setPhone(StringUtils.hiddenPhoneNumber(sysCustomerPhysicalSigns.getPhone()));
                }*/
                result.put("type", 1);
            }
            result.put(key, sysCustomerPhysicalSigns);
        }
        //对ID进行加密
        result.put("enc_id", id != null ? AesUtils.encrypt(id + "", null) : "");
        return result;
    }

    @Override
    public Map<String, Object> getPhysicalSignsByOutId(String id) {
        return null;
    }
}