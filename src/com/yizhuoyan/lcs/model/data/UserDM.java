package com.yizhuoyan.lcs.model.data;

import java.io.Serializable;
import java.net.InetAddress;
/**
 * 用户数据模型,代表用户个人信息
 *
 */
public class UserDM implements Serializable{
	private static final long serialVersionUID = -7798990187931132951L;
	/**用户昵称*/
	private String name;
	/**用户所在ip*/
	private  InetAddress ip;
	/**用户UDP对应端口*/
	private int port;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public static void main(String[] args) throws Exception{
		InetAddress local=InetAddress.getLocalHost();
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		System.out.println(local.equals(InetAddress.getByName("192.168.1.106")));
	}
	
	
}
