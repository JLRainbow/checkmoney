package com.citic.platform.service;

import java.util.List;
import java.util.Map;

import com.citic.platform.entity.OrderRefund;

public interface OrderRefundService {


	List<OrderRefund> getPlatformReceiptData(Map<String, Object> map);

}
