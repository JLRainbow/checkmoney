package com.citic.platform.service;

import java.util.List;
import java.util.Map;

import com.citic.platform.entity.OrderReceipts;

public interface OrderReceiptsService {

	List<OrderReceipts> getPlatformPayData(Map<String, Object> map) throws Exception;

	void mergePay(Map<String, Object> map);

}
