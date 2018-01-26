package com.citic.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.citic.annotation.SystemLog;
import com.citic.exception.SystemException;
import com.citic.mapper.OperationstatusMapper;

@Service
public class FileUploadService {

	@Autowired
	private OperationstatusMapper operationstatusMapper;

	@Value("${uploadFilePath}")
	private String uploadFilePath;

	@Transactional(readOnly=false)//需要事务操作必须加入此注解
	@SystemLog(module="工资管理",methods="工资管理-工资明细导入")//凡需要处理业务逻辑的.都需要记录操作日志
	public String upload(MultipartFile multipartFile, String dir) throws Exception {

		String originalFileName = multipartFile.getOriginalFilename();
		if (!originalFileName.endsWith(".xls") && !originalFileName.endsWith(".xlsx")) {
			throw new SystemException("文件类型错误！");
		}
		String uuid_fileName = makeFileName(originalFileName);

		String makePath = makePath(uuid_fileName, uploadFilePath, dir);

		File targetFile = new File(makePath, uuid_fileName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 上传文件到目标路径
		try {
			multipartFile.transferTo(targetFile);
		} catch (Exception e) {
			throw new SystemException("上传文件失败！文件名为：" + originalFileName);
		}
		return targetFile.toString();
	}

	/**
	 * @Method: makeFileName
	 * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * @Anthor:孤傲苍狼
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称
	 */
	private String makeFileName(String filename) { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}

	/**
	 * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * 
	 * @Method: makePath
	 * @Description:
	 * @Anthor:孤傲苍狼
	 *
	 * @param filename
	 *            文件名，要根据文件名生成存储目录
	 * @param savePath
	 *            文件存储路径
	 * @return 新的存储目录
	 */
	private String makePath(String filename, String savePath, String monthDir) {
		// 构造新的保存目录
		String dir = savePath + "\\\\" + monthDir;
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}
	
}
