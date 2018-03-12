package com.citic.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WxWebankMapper extends BaseMapper {

	void updateReciptAmount(@Param("parmsMap")Map<String, Object> parmsMap);

	void updateCheckResult(@Param("parmsMap")Map<String, Object> parmsMap);
}
