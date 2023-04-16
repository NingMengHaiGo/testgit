package com.zhao.deep.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author risenb-003
 * @date 
 */
public class EncodingUtil {
	private static Log log = LogFactory.getLog(EncodingUtil.class);
	/**
	 * 加密
	 * @param str
	 * @return
	 */
	public static String encodeBASE64(String str) {
		if ("".equals(StringUtil.trim(str)))
			return "";
		byte[] result = Base64.encode(str.getBytes());
		return new String(result);
	}
	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static String decodeBASE64(String str) {
		if ("".equals(StringUtil.trim(str)))
			return "";
		byte[] result = Base64.decode(str);
		return new String(result);
	}

	public static String encode8859(String str) {
		try {
			return new String(str.getBytes(), "8859_1");
		} catch (UnsupportedEncodingException e) {
			log.error(e);
			return "";
		}
	}

	public static String urlEncode(String str, String charset) {
		try {
			String result = URLEncoder.encode(str, charset);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String urlDecode(String str, String charset) {
		try {
			String result = URLDecoder.decode(str, charset);
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 *  str 被转码的中文
	 *  转换为 UTF-8格式编码
	 */
	public static String encodeUtf8(String str) {
		String result;
		try {
			result = new String(str.getBytes("ISO-8859-1"),"UTF-8");
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, String> mapEncodeUtf8(Map<String, String> map) {
		Iterator<Map.Entry<String,String>> iter3 =map.entrySet().iterator();  
        Map.Entry<String,String> entry3;  
        while (iter3.hasNext()){  
            entry3 = iter3.next();  
            String key = encodeUtf8(entry3.getKey());  
            String value = encodeUtf8(entry3.getValue());  
            map.put(key, value);
        }  
		return map;
	}
}
