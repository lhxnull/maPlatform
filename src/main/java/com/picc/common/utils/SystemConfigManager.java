//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : SystemConfigManager.java
//  @ Date : 2013/12/31
//  @ Author : 
//
//



package com.picc.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 配置文件管理类
 */
public class SystemConfigManager {


	//读取配置文件
	private Properties prop = new Properties();
	private Map<String, String> bdMapKey = null;
	private Map<String, String> gdUrl = null;
	private Map<String, String> swUrl = null;

	private Map<String, String>  unifiedConfiguration= null;
	private static final Logger log = LoggerFactory.getLogger(SystemConfigManager.class);
	public static final  String VERSION = "V1.0.0.4";
	public static final  String SYSTEM_CODE = "0225";
	public static final String  SYSTEM_USER = "0106";
	public static final String defmaptype = "3";

	private Map<String, String> mapCenterAddress = null;

	/**
	 * 获取globalconfiguration中最大等待时间
	 *
	 * @return
	 */
	public String getMaxWaitTime() {
		String time = "";
		try {
			time = prop.getProperty("max_wait_time").trim();
		} catch (Exception e) {
			log.error("异常：", e);
		}
		return time;
	}

	/**
	 * 获取globalconfiguration中线程数量
	 *
	 * @return
	 */
	public String getThreadNumber() {
		String number = "";
		try {
			number = prop.getProperty("thread_number").trim();
		} catch (Exception e) {
			log.error("异常：", e);
		}
		return number;
	}

	private static SystemConfigManager instance = new SystemConfigManager();

	/**
	 * <configType, <comCode, <key, value>>>
	 */
	private Map<String, Map<String, Map<String, String>>> configMap;


	/**
	 * 私有构造函数
	 * <p>
	 * 加载配置项
	 * </p>
	 */
	private SystemConfigManager() {

	}


	/**
	 * 获取统一门户url
	 * @param key
	 * @return
	 */
	public String getUnifiedConfig(String key){

		return unifiedConfiguration.get(key);

	}

	/**
	 * 获取Manager实例
	 *
	 * @return
	 */
	public static SystemConfigManager getInstance() {
		return instance;
	}

	public void destroy() {
		running = false;
	}

	private volatile boolean running = false;


	/**
	 *
	 * @Title: getPropertiesMap
	 * @Description:读取配置文件，通过文件名获取配置文件的map
	 * @Author: MaXiao
	 * @param @param fileName
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	private Map<String, String> getPropertiesMap(String fileName) {
		Properties properties = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try {
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName));
			if (properties != null) {
				String[] keys = properties.keySet().toArray(new String[0]);
				for (int i = 0; i < keys.length; i++) {
					map.put(keys[i], properties.getProperty(keys[i]));
				}
			}
		} catch (IOException e) {
			System.out.println("SystemConfigManager.getPropertiesMap() "
					+ e.toString());
			log.info(e.toString());
			e.printStackTrace();
		}
		return map;
	}



	/**
	 * 启动配置变化更新线程
	 */
//	public void initAndStartConfigChangeMonitor(){
//		unifiedConfiguration =getPropertiesMaps("config.properties");
//		//获取百度key
//		bdMapKey=getPropertiesMap("mapCenter.properties");
//		//
//		gdUrl=getPropertiesMap("GDServiceUrl.properties");
//		swUrl=getPropertiesMap("SWServiceUrl.properties");
//	}

	/**
	 * 获取百度key
	 * @param key
	 * @return
	 */
	public String getBDMapKey(String key) {

		return bdMapKey.get(key);
	}
	/**
	 * 获取高德地址
	 * @param key
	 * @return
	 */
	public String getGDUrl(String key){

		return gdUrl.get(key);
	}


	/**

	 * @Title: getPropertiesMap
	 * @Description:读取配置文件，通过文件名获取配置文件的key
	 * @Author: zhanqiangqiang
	 * @param @param fileName
	 * @param @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String, String> getPropertiesMaps(String fileName) {
		Properties properties = new Properties();
		Map<String, String> key = new HashMap<String, String>();
		try {
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(fileName));
			if (properties != null) {
				String[] keys = properties.keySet().toArray(new String[0]);
				for (int i = 0; i < keys.length; i++) {
					key.put(keys[i], properties.getProperty(keys[i]));
				}
			}
		} catch (IOException e) {
			System.out.println("SystemConfigManager.getPropertiesMaps() "
					+ e.toString());
			log.info(e.toString());
			e.printStackTrace();
		}
		return key;
	}

	public String getMapCenterAddr(String addr) {
		if (mapCenterAddress.containsKey(addr)) {
			return mapCenterAddress.get(addr);
		}
		return "";
	}
	/**
	 * 获取高德地址
	 * @param key
	 * @return
	 */
	public String getSWUrl(String key){

		return swUrl.get(key);
	}




}