package com.yizhuoyan.lcs;

import java.net.InetAddress;

import javax.swing.SwingUtilities;

import com.yizhuoyan.lcs.model.data.SystemConfig;
import com.yizhuoyan.lcs.model.data.UserDM;
import com.yizhuoyan.lcs.model.function.impl.UserFunctionModel;
import com.yizhuoyan.lcs.ui.ChatWindow;
/**
 * 系统入口
 *
 */
public class Application {
	/**
	 * 主方法,启动程序
	 */
	public static void main(String[] args) throws Exception{
		UserDM dm=new UserDM();
		dm.setIp(InetAddress.getLocalHost());
		dm.setPort(SystemConfig.PORT);
		dm.setName(dm.getIp().getHostAddress());
		
		final UserFunctionModel model=new UserFunctionModel(dm);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ChatWindow win=new ChatWindow(model);
				model.setViewListenr(win);
				win.setLocationRelativeTo(null);
				win.setVisible(true);
				model.start();
			}
		});
		
	}
}
