package com.yizhuoyan.lcs.model.data.message;

import java.net.DatagramPacket;

import com.yizhuoyan.lcs.model.data.UserDM;
/**
 * 聊天信息
 */
public class ChatMessage extends AbstractMessage{
	/**消息内容*/
	private String message;
	/**消息来源*/
	private UserDM user;
	/***
	 * 0=群聊
	 * 1=私聊
	 */
	private int type=0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UserDM getUser() {
		return user;
	}

	public void setUser(UserDM user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
