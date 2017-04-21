package com.yizhuoyan.lcs.model.data.message;

import com.yizhuoyan.lcs.model.data.UserDM;
/**
 * 用户离线消息
 *
 */
public class OfflineMessage extends AbstractMessage {
	private static final long serialVersionUID = -2098101702156321174L;
	private UserDM onlineUser;
	public UserDM getOnlineUser() {
		return onlineUser;
	}
	public void setOnlineUser(UserDM onlineUser) {
		this.onlineUser = onlineUser;
	}
	
}
