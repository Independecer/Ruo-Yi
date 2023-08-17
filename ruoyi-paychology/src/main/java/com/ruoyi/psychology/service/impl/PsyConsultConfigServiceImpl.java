package com.ruoyi.psychology.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.utils.NewDateUtil;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.dto.DateNumDTO;
import com.ruoyi.psychology.mapper.PsyConsultConfigMapper;
import com.ruoyi.psychology.service.IPsyConsultConfigService;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.vo.PsyConsultConfigByGroupVO;
import com.ruoyi.psychology.vo.PsyConsultConfigVO;
import com.ruoyi.system.service.ISysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 咨询类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Service
public class PsyConsultConfigServiceImpl implements IPsyConsultConfigService
{

    @Resource
    private PsyConsultConfigMapper psyConsultConfigMapper;

    @Resource
    private ISysDictTypeService iSysDictTypeService;

    @Resource
    private IPsyConsultOrderService orderService;

    @Override
    public List<DateNumDTO> getDateNum(Integer num) {
        List<DateNumDTO> list = new ArrayList<>();
        if (num == 0) {
            return list;
        }
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (int i = 1; i <= num; i++) {
            if (i > 1) {
                calendar.add(Calendar.DATE, 1);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fm = sdf.format(calendar.getTime());
            String[] format = StringUtils.split(fm, "-");

            DateNumDTO dto = new DateNumDTO();
            dto.setDate(fm);
            dto.setYear(format[0]);
            dto.setMonth(format[1] + "月");
            dto.setWeek(NewDateUtil.getWeekOfDate(calendar));
            dto.setDay(format[2]);
            list.add(dto);
        }

        return list;
    }

    @Override
    public List<PsyConsultConfigVO> getConfigByType(String dictType) {
        SysDictType sysDictType = iSysDictTypeService.selectDictTypeByType(dictType);
        List<SysDictData> dictData = iSysDictTypeService.selectDictDataByType(dictType);
        List<PsyConsultConfigVO> collect = dictData.stream().map(i -> convertToVo(sysDictType, i)).collect(Collectors.toList());
        return collect;
    }

    private PsyConsultConfigVO convertToVo(SysDictType sysDictType, SysDictData sysDictData) {
        PsyConsultConfigVO vo = new PsyConsultConfigVO();
        vo.setTitle(sysDictType.getDictName());
        vo.setCode(sysDictData.getDictType());
        vo.setName(sysDictData.getDictLabel());
        vo.setValue(sysDictData.getDictValue());
        return vo;
    }

    @Override
    public List<PsyConsultConfigByGroupVO> getConfigByTypes(String[] dictTypes) {
        return psyConsultConfigMapper.getConfigByTypes(dictTypes);
    }

    @Override
    public List<String> getNotices() {
        List<SysDictData> dictData = iSysDictTypeService.selectDictDataByType("consult_notice");
        List<String> list = dictData.stream().map(SysDictData::getDictLabel).collect(Collectors.toList());

        List<PsyConsultOrder> orders = orderService.getListForNotice("ORDER BY create_time DESC LIMIT 10");
        orders.forEach(a -> {
            list.add(StrUtil.format("{}** 下单了{}老师的{}!", a.getNickName().substring(0,1), a.getConsultName(), a.getServeName()));
        });

        Collections.shuffle(list);
        return list;
    }

}
