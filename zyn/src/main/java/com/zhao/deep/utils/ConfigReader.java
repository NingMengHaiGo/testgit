package com.zhao.deep.utils;


import java.util.ResourceBundle;

/**
 * 将需要配置的数据读入内存
 * @author
 *
 */
public class ConfigReader {
	private static ResourceBundle  config = ResourceBundle.getBundle("dbconfig");
	public static String getValue(String name){
		return config.getString(name);
	}
	public static void main(String[] args) {
		System.out.println(getValue("domain"));	
	}
}