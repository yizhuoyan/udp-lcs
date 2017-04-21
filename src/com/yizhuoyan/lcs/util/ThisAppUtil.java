package com.yizhuoyan.lcs.util;
/**
 * 工具类
 *
 */
public class ThisAppUtil {
	/**
	 * 如果s为null或空白,则返回def,否则返回去掉前后空格的字符串
	 * @param s 测试字符串
	 * @param def 默认字符串
	 * @return 
	 */
	static public String ifBlank(String s,String def){
		if(s==null||(s=s.trim()).length()==0){
			return def;
		}
		return s;
	}
}
