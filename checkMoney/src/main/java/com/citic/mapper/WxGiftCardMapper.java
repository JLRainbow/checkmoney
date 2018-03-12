package com.citic.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WxGiftCardMapper extends BaseMapper {

	void updatePayPrice(@Param("parmsMap")Map<String, Object> parmsMap);

	void updateCheckResult(@Param("parmsMap")Map<String, Object> parmsMap);

}
