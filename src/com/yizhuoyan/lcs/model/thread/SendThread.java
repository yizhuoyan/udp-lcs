package com.yizhuoyan.lcs.model.thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 发送消息线程
 *
 */
public class SendThread extends Thread {
	private BlockingQueue<DatagramPacket> sendMessages;
	private final DatagramSocket socket;

	public SendThread(DatagramSocket socket) {
		this.socket = socket;
		this.sendMessages = new LinkedBlockingQueue<DatagramPacket>();
	}

	public void sendMessage(DatagramPacket m) {
		try {
			this.sendMessages.put(m);
		} catch (InterruptedException e) {
			e.printStackTrace();	
		}
	}


	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				DatagramPacket p = this.sendMessages.take();
				try {
					this.socket.send(p);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
