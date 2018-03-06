package com.citic.platform.mapper;

import java.util.List;
import java.util.Map;

import com.citic.mapper.BaseMapper;
import com.citic.platform.entity.WxGiftCardBuyRecord;

public interface WxGiftCardBuyRecordMapper  extends BaseMapper{

	
	List<WxGiftCardBuyRecord> queryWxGiftCardBuyRecord(Map<String, Object> map);
}
