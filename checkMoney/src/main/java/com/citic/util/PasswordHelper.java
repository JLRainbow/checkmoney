package com.citic.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.citic.entity.UserFormMap;

/**
 * 密码MD5加解密类
 *	
 * @author xutao
 * @date 2017年3月15日
 */
public class PasswordHelper {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private static final String ALGORITHMNAME = "md5";
	private int hashIterations = 2;

	public void encryptPassword(UserFormMap userFormMap) {
		String salt=randomNumberGenerator.nextBytes().toHex();
		userFormMap.put("credentialssalt", salt);
		String newPassword = new SimpleHash(ALGORITHMNAME, userFormMap.get("password"), ByteSource.Util.bytes(userFormMap.get("accountname")+salt), hashIterations).toHex();
		userFormMap.put("password", newPassword); 
	}
	public static void main(String[] args) {
		PasswordHelper passwordHelper = new PasswordHelper();
		UserFormMap userFormMap = new UserFormMap();
		userFormMap.put("password","123456");
		userFormMap.put("accountname","admin");
		passwordHelper.encryptPassword(userFormMap);
		System.out.println(userFormMap);
	}
}
