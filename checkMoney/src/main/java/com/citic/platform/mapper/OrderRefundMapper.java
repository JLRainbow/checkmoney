package com.citic.platform.mapper;

import java.util.List;
import java.util.Map;

import com.citic.mapper.BaseMapper;
import com.citic.platform.entity.OrderRefund;

public interface OrderRefundMapper extends BaseMapper{
	List<OrderRefund> getPlatformReceiptData(Map<String, Object> map);
}
