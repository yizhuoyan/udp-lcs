package com.yizhuoyan.lcs.model.thread;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.yizhuoyan.lcs.model.data.message.AbstractMessage;
import com.yizhuoyan.lcs.model.data.message.ChatMessage;
import com.yizhuoyan.lcs.model.data.message.InputStreamMessage;
import com.yizhuoyan.lcs.model.data.message.OfflineMessage;
import com.yizhuoyan.lcs.model.data.message.OnlineMessage;
import com.yizhuoyan.lcs.model.data.message.UpdateInfoMessage;
import com.yizhuoyan.lcs.model.function.impl.UserFunctionModel;
/**
 * 接收消息线程任务
 *
 */
public class ReceiveThread extends Thread {
	private DatagramPacket receivePacket;
	private UserFunctionModel model;
	private final DatagramSocket socket;
	private final String localhost;
	public ReceiveThread(UserFunctionModel model) {
		this.model=model;
		this.socket = model.socket;
		localhost=this.model.dm.getIp().getHostAddress();
		byte[] buf = new byte[1024 * 1024*10];// 10M
		receivePacket = new DatagramPacket(buf, buf.length);
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			this.receiveOneMessage();
		}
	}


	private void receiveOneMessage() {
		try {
			this.socket.receive(this.receivePacket);
			DatagramPacket p=this.receivePacket;
			InetAddress fromIp=p.getAddress();
			//miss自己的
			String ip=fromIp.getHostAddress();
			if(ip.equals(this.localhost)){
				return;
			}
			
			ByteArrayInputStream bais=new ByteArrayInputStream(p.getData(),p.getOffset(),p.getLength());
			ObjectInputStream in=new ObjectInputStream(bais);
			AbstractMessage message=(AbstractMessage) in.readObject();
			message.setIp(fromIp);
			message.setPort(p.getPort());
			this.handleMessage(message);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void handleMessage(Object message){
		if(message instanceof OnlineMessage){
			model.handleOnlineMessage((OnlineMessage)message);
		}else if(message instanceof ChatMessage){
			model.handleChatMessage((ChatMessage)message);
		}else if(message instanceof InputStreamMessage){
			model.handleInputStreamMessage((InputStreamMessage)message);
		}else if(message instanceof OfflineMessage){
			model.handleOfflineMessage((OfflineMessage) message);
		}else if(message instanceof UpdateInfoMessage){
			model.handleUpdateInfoMessage((UpdateInfoMessage) message);
		}
	}
	
}
