package com.citic.platform.service;

import java.util.List;
import java.util.Map;

import com.citic.platform.entity.WxGiftCardBuyRecord;

public interface WxGiftCardBuyRecordService {

	List<WxGiftCardBuyRecord> queryWxGiftCardBuyRecord(Map<String, Object> map);
}
