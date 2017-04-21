package com.yizhuoyan.lcs.model.data.message;

import com.yizhuoyan.lcs.model.data.UserDM;
/**
 * 用户上线消息
 */
public class OnlineMessage extends AbstractMessage {
	private static final long serialVersionUID = -7962231701884323352L;
	private UserDM onlineUser;
	public UserDM getOnlineUser() {
		return onlineUser;
	}
	public void setOnlineUser(UserDM onlineUser) {
		this.onlineUser = onlineUser;
	}
}
