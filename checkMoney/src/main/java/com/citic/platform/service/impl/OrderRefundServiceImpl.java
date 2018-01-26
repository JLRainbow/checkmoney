package com.citic.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.platform.entity.OrderRefund;
import com.citic.platform.mapper.OrderRefundMapper;
import com.citic.platform.service.OrderRefundService;

@Service
public class OrderRefundServiceImpl implements OrderRefundService {

	@Autowired
	private OrderRefundMapper orderRefundMapper;
	
	@Override
	public List<OrderRefund> getPlatformReceiptData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return orderRefundMapper.getPlatformReceiptData(map);
	}

}
