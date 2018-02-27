package com.citic.bill.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.citic.bill.alipay.AlipayBillDownload;

public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);
	/**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "GBK";

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * 第一步： 把 支付宝生成的账单 下载到本地目录
     * 
     * @param path
     *            支付宝资源url
     * @param filePath
     *            生成的zip 包目录
     * @throws MalformedURLException
     */
    public static void downloadNet(String path, String filePath)
            throws MalformedURLException {
    	 // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(path);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(filePath);
            
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unZip(String zipFilePath, String destDir)
            throws Exception {
        ZipFile zipFile = new ZipFile(zipFilePath,CHINESE_CHARSET);
        Enumeration<?> emu = zipFile.getEntries();
        BufferedInputStream bis;
        FileOutputStream fos;
        BufferedOutputStream bos;
        File file, parentFile;
        ZipEntry entry;
        byte[] cache = new byte[CACHE_SIZE];
        while (emu.hasMoreElements()) {
            entry = (ZipEntry) emu.nextElement();
            if (entry.isDirectory()) {
                new File(destDir + entry.getName()).mkdirs();
                continue;
            }
            bis = new BufferedInputStream(zipFile.getInputStream(entry));
            file = new File(destDir + entry.getName());
            parentFile = file.getParentFile();
            if (parentFile != null && (!parentFile.exists())) {
                parentFile.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos, CACHE_SIZE);
            int nRead = 0;
            while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                fos.write(cache, 0, nRead);
            }
            bos.flush();
            bos.close();
            fos.close();
            bis.close();
        }
        zipFile.close();
    }

    /**
     * 
     *方法：获取下载对账单路径
     *创建时间：2018年1月26日
     *创建者：jial
     */
    public static String getBillPath() throws IOException{
    	Properties props =new Properties();
		InputStream is = AlipayBillDownload.class.getClassLoader().getResourceAsStream("config.properties");
		props.load(is);
		String filePath = props.getProperty("filePath");
		is.close();
		return filePath;
    }
    
    /** 
     * @param srcFile：压缩文件路径 
     * @param dest：解压到的目录 
     * @param deleteFile：解压完成后是否删除文件 
     * @throws Exception 
     */  
    public static void unZip2(String srcFile,String dest,boolean deleteFile)  throws Exception {  
        File file = new File(srcFile);  
        if(!file.exists()) {  
            throw new Exception("解压文件不存在!");  
        }  
        ZipFile zipFile = new ZipFile(file,CHINESE_CHARSET);  
        Enumeration e = zipFile.getEntries();  
        while(e.hasMoreElements()) {  
            ZipEntry zipEntry = (ZipEntry)e.nextElement();  
            logger.debug("changeFileName before:"+zipEntry.getName());
            if(zipEntry.isDirectory()) {  
                String name = zipEntry.getName();  
                name = name.substring(0,name.length()-1);  
                File f = new File(dest + name);  
                f.mkdirs();  
            } else { 
            	logger.debug("changeFileName before:"+changeFileName(zipEntry.getName()));
                File f = new File(dest + changeFileName(zipEntry.getName()));
                f.getParentFile().mkdirs();  
                f.createNewFile();  
                InputStream is = zipFile.getInputStream(zipEntry);  
                FileOutputStream fos = new FileOutputStream(f);  
                int length = 0;  
                byte[] b = new byte[CACHE_SIZE];  
                while((length=is.read(b, 0, CACHE_SIZE))!=-1) {  
                    fos.write(b, 0, length);  
                }  
                is.close();  
                fos.close();  
            }  
        }  
          
        if (zipFile != null) {  
            zipFile.close();  
        }  
          
        if(deleteFile) {  
            file.deleteOnExit();  
        }  
    }
    
    public static void main(String[] args) throws Exception {
		unZip2("E:/csv/bill/1519633370780.zip","E:/csv/bill/",true);
    }
    
    private static String changeFileName(String fileName) {
    	String prefix = fileName.substring(0,fileName.lastIndexOf("_")+1);
    	String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
    	fileName = fileName.substring(fileName.lastIndexOf("_")+1,fileName.lastIndexOf("."));
    	switch (fileName) {
			case "业务明细(汇总)":  fileName = prefix+"businessDetailsSummary"+suffix; break; 
			case "业务明细": fileName = prefix+"businessDetails"+suffix;  break;
			case "账务明细(汇总)": fileName = prefix+"financialDetailsSummary"+suffix; break;
			case "账务明细": fileName = prefix+"financialDetails"+suffix; break; 
		}
		return fileName;
    }
}
