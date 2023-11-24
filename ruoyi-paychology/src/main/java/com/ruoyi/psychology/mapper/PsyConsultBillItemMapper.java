package com.ruoyi.psychology.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultBillItem;
import com.ruoyi.psychology.dto.BillItemDTO;
import com.ruoyi.psychology.request.PsyAdminBillReq;

import java.util.List;

public interface PsyConsultBillItemMapper extends BaseMapper<PsyConsultBillItem>
{

    List<PsyConsultBillItem> getOrderItems();

    List<BillItemDTO> getItemList(PsyAdminBillReq req);

    List<PsyConsultBillItem> getItemListForDetail(PsyAdminBillReq req);

    List<PsyConsultBillItem> getBillItems(PsyAdminBillReq req);

}
