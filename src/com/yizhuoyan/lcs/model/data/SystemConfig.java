package com.yizhuoyan.lcs.model.data;

import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * 系统配置信息
 * @author Administrator
 *
 */
public class SystemConfig {
	public static final String SYSTEM_NAME="局域网聊天系统";
	public static final int PORT=8888;
	public static final InetAddress BROADCAST_IP= createBroadCastIP();
	/**
	 * 获取广播地址
	 */
	private static InetAddress createBroadCastIP(){
		try {
			InetAddress local= InetAddress.getLocalHost();
			String ip=local.getHostAddress();
			String broadIP=ip.substring(0, ip.lastIndexOf('.'));
			return InetAddress.getByName(broadIP+".255");
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		
	}
}
