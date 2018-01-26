package com.citic.platform.mapper;

import java.util.List;
import java.util.Map;

import com.citic.entity.AccountReceiptChkFormMap;
import com.citic.mapper.BaseMapper;
import com.citic.platform.entity.OrderReceipts;

public interface OrderReceiptsMapper extends BaseMapper{
	
	List<OrderReceipts> getPlatformPayData(Map<String, Object> map);

	List<AccountReceiptChkFormMap> getMergePay(Map<String, Object> map);

}
