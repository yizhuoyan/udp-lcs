package com.yizhuoyan.lcs.model;

import com.yizhuoyan.lcs.model.data.UserDM;
import com.yizhuoyan.lcs.model.data.message.ChatMessage;
import com.yizhuoyan.lcs.model.data.message.InputStreamMessage;
/**
 * 视图更新回调
 * @author Administrator
 *
 */
public interface UpdateViewListener {
	/**
	 * 用户上线通知
	 * @param u
	 */
	void userOnLine(UserDM u);
	/**
	 * 用户下线通知
	 * @param u
	 */
	void userOffLine(UserDM u);
	/**
	 * 接收聊天信息
	 * @param m
	 */
	void receiveChatMessage(ChatMessage m);
	/**
	 * 接收用户更新信息
	 * @param u
	 */
	void userChangeInfo(UserDM u);
	/**
	 * 接收文件
	 * @param message
	 */
	void receiveInputStreamMessage(InputStreamMessage message);
}
