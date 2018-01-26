package com.citic.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citic.annotation.SystemLog;
import com.citic.bean.po.Operationstatus;
import com.citic.bean.po.Wages;
import com.citic.bean.pojo.WagesPojo;
import com.citic.bean.vo.IdNoWalletStatus;
import com.citic.bean.vo.WagesAccountValidationVo;
import com.citic.bean.vo.WagesQueryVo;
import com.citic.bean.vo.WalletAbutmentTransVo;
import com.citic.bean.vo.WalletAbutmentVo;
import com.citic.constants.OperationstatusEnum;
import com.citic.entity.WagesFormMap;
import com.citic.exception.SystemException;
import com.citic.mapper.OperationstatusMapper;
import com.citic.mapper.WagesMapper;
import com.citic.util.CalendarUtil;
import com.citic.util.HttpClientUtil;
import com.citic.util.JSONUtil;
import com.citic.util.StringUtil;
import com.sun.net.httpserver.Authenticator.Failure;

import sun.util.calendar.CalendarUtils;

@Service
public class OperationstatusService {

	@Autowired
	private OperationstatusMapper operationstatusMapper;

	public void saveOperationstatus(Operationstatus operationstatus) throws Exception {
		operationstatusMapper.insertOperstatus(operationstatus);
	} 
	
}
