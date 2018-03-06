package com.citic.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citic.platform.entity.WxGiftCardBuyRecord;
import com.citic.platform.mapper.WxGiftCardBuyRecordMapper;
import com.citic.platform.service.WxGiftCardBuyRecordService;
@Service
public class WxGiftCardBuyRecordServiceImpl implements WxGiftCardBuyRecordService {

	@Autowired
	private WxGiftCardBuyRecordMapper wxGiftCardBuyRecordMapper;
	
	@Override
	public List<WxGiftCardBuyRecord> queryWxGiftCardBuyRecord(Map<String, Object> map) {
		return wxGiftCardBuyRecordMapper.queryWxGiftCardBuyRecord(map);
	}

}
