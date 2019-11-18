//package com.picc.common.utils;
//
//import java.io.Serializable;
//import java.util.List;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//public class ReturnResult implements Serializable{
//
//
//	private static final String ERROR="0";
//
//	private static final String SUCCESS="1";
//
//	private static final String  PARAMEXCEPTION ="参数异常";
//
//	private static final String SERVICEEXCEPTION="服务异常";
//
//	private static final String SYSTEMEXCEPTION="系统异常";
//
//	public static String getSystemexception() {
//		return SYSTEMEXCEPTION;
//	}
//
//	public static String getError() {
//		return ERROR;
//	}
//
//	public static String getSuccess() {
//		return SUCCESS;
//	}
//
//	public static String getParamexception() {
//		return PARAMEXCEPTION;
//	}
//
//	public static String getServiceexception() {
//		return SERVICEEXCEPTION;
//	}
//
//	private Object result;
//
//	public static final String writeJsonSuccess(Object obj){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("status", SUCCESS);
//		jsonObject.put("result", JSONObject.fromObject(obj));
//		return jsonObject.toString();
//	}
//
//	public static final String writeJsonListSuccess(List list){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("massger", SUCCESS);
//		jsonObject.put("data",JSONArray.fromObject(list));
//		return jsonObject.toString();
//	}
//
//	public static final String writeJsonError(String str){
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("massger", ERROR);
//		jsonObject.put("data", str);
//		return jsonObject.toString();
//	}
//
//
//
//
//
//}
