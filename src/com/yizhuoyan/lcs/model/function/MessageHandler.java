package com.yizhuoyan.lcs.model.function;

import com.yizhuoyan.lcs.model.data.message.ChatMessage;
import com.yizhuoyan.lcs.model.data.message.InputStreamMessage;
import com.yizhuoyan.lcs.model.data.message.OfflineMessage;
import com.yizhuoyan.lcs.model.data.message.OnlineMessage;
import com.yizhuoyan.lcs.model.data.message.UpdateInfoMessage;

/**
 * 用户消息处理接口
 */
public interface MessageHandler {
	/**
	 * 处理其他用户上线消息
	 * 
	 * @param m
	 *            消息
	 */
	void handleOnlineMessage(OnlineMessage m);

	/**
	 * 处理其他用户个人信息修改消息
	 * 
	 * @param m
	 *            消息
	 */
	void handleUpdateInfoMessage(UpdateInfoMessage m);

	/**
	 * 处理其他用户下线消息
	 * 
	 * @param m
	 *            消息
	 */
	void handleOfflineMessage(OfflineMessage m);

	/**
	 * 处理其他用户聊天消息,包括群聊和私聊
	 * 
	 * @param m
	 *            消息
	 */
	void handleChatMessage(ChatMessage m);

	/**
	 * 处理其他用户发送文件消息
	 * 
	 * @param m
	 *            消息
	 */
	void handleInputStreamMessage(InputStreamMessage m);
}
