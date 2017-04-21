package com.yizhuoyan.lcs.model.data.message;

import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
/**
 * 抽象消息类
 * 系统分为不同的消息子类
 * 聊天消息
 * 用户上线消息
 * 用户下线消息
 * 用户更新个人信息消息
 * 用户发送文件消息
 */
public abstract class AbstractMessage implements Serializable {
	protected transient InetAddress ip;
	protected transient int port;

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
	/**
	 * 把消息通过序列化转换为字节数组,便于传输
	 * @return
	 * @throws IOException
	 */
	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		ObjectOutputStream out = new ObjectOutputStream(baos);
		out.writeObject(this);
		byte[] data= baos.toByteArray();
		out.close();
		return data;
	}
	/**
	 * 把消息转换UDP数据报,便用Socket传输
	 */
	public final DatagramPacket toPacket() {
		try {
			byte[] buf = this.getBytes();
			DatagramPacket p = new DatagramPacket(buf, buf.length);
			p.setPort(this.port);
			p.setAddress(this.ip);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
