package com.yizhuoyan.lcs.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.SoftBevelBorder;

import com.yizhuoyan.lcs.model.UpdateViewListener;
import com.yizhuoyan.lcs.model.data.SystemConfig;
import com.yizhuoyan.lcs.model.data.UserDM;
import com.yizhuoyan.lcs.model.data.message.ChatMessage;
import com.yizhuoyan.lcs.model.data.message.InputStreamMessage;
import com.yizhuoyan.lcs.model.function.FunctionModel;
import com.yizhuoyan.lcs.ui.componet.MessagePanel;
import com.yizhuoyan.lcs.util.ThisAppUtil;
/**
 * 群聊窗口
 *
 */
public class ChatWindow extends JFrame implements ActionListener, UpdateViewListener {
	private JPanel onlineListUI;
	private MessagePanel messageListUI;
	private Map<String, JLabel> onlineUserUIMap;
	private JTextField inputUI;
	private JButton sendBtn, updateMyInfoBtn, exitBtn;
	private JLabel nameUI;
	private FunctionModel model;
	public ChatWindow(FunctionModel model) {
		this.model = model;
		this.onlineUserUIMap=new HashMap<String, JLabel>(36);
		this.initLayout();
		this.initListener();
	}

	private void initLayout() {
		{
			this.setTitle(SystemConfig.SYSTEM_NAME);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setResizable(true);
			this.setAutoRequestFocus(false);
			this.setSize(500, 600);
		}
		{
			JPanel topPanel = new JPanel(new BorderLayout());
			JButton updateMyInfo = new JButton("修改个人信息");
			UserDM dm = model.getMyselfInfo();
			Icon icon = new ImageIcon(this.getClass().getResource("/user.gif"));
			String name = dm.getName() + "(" + dm.getIp().getHostAddress() + ")";
			JLabel nameUI = new JLabel(name, icon, JLabel.CENTER);
			JButton exitBtn = new JButton("退出");
			topPanel.add(updateMyInfo, BorderLayout.WEST);
			topPanel.add(nameUI, BorderLayout.CENTER);
			topPanel.add(exitBtn, BorderLayout.EAST);
			topPanel.setBorder(BorderFactory.createSoftBevelBorder(SoftBevelBorder.RAISED));
			topPanel.setBackground(Color.lightGray);
			this.add(topPanel, BorderLayout.NORTH);
			this.nameUI=nameUI;
			this.updateMyInfoBtn = updateMyInfo;
			this.exitBtn = exitBtn;
		}
		{
			JPanel leftPanel = new JPanel(new BorderLayout());
			leftPanel.setPreferredSize(new Dimension(100, 0));
			leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			{
				JLabel title = new JLabel("在线列表");
				title.setFont(new Font("黑体", Font.BOLD, 16));
				title.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				title.setHorizontalAlignment(JLabel.CENTER);
				leftPanel.add(title, BorderLayout.NORTH);
				JPanel list=new JPanel();
				BoxLayout layout=new BoxLayout(list, BoxLayout.Y_AXIS);
				JScrollPane scrollPane = new JScrollPane(list);
				leftPanel.add(list);
				this.onlineListUI=list;
			}
			JPanel rightPanel = new JPanel(new BorderLayout());
			{
				JLabel title = new JLabel("消息");
				title.setHorizontalAlignment(JLabel.CENTER);
				title.setFont(new Font("黑体", Font.BOLD, 16));
				title.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				rightPanel.add(title, BorderLayout.NORTH);
				MessagePanel messagePanel=new MessagePanel();
				JScrollPane scrollPane = new JScrollPane(messagePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				scrollPane.setAutoscrolls(true);
				this.messageListUI = messagePanel;
				rightPanel.add(scrollPane);

				JPanel actionPanel = new JPanel(new BorderLayout());
				JTextField inputUI = new JTextField();
				inputUI.setColumns(25);
				actionPanel.add(inputUI);
				JButton sendBtn = new JButton("发送");
				actionPanel.add(sendBtn, BorderLayout.EAST);
				rightPanel.add(actionPanel, BorderLayout.SOUTH);
				this.sendBtn = sendBtn;
				this.inputUI = inputUI;
			}
			this.add(leftPanel, BorderLayout.WEST);
			this.add(rightPanel);
		}
	}

	private void initListener() {
		this.sendBtn.setActionCommand("send");
		this.sendBtn.addActionListener(this);
		this.updateMyInfoBtn.setActionCommand("updateMyInfo");
		this.updateMyInfoBtn.addActionListener(this);
		this.exitBtn.setActionCommand("exit");
		this.exitBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent actionevent) {
		switch (actionevent.getActionCommand()) {
		case "send":
			this.handleSendBtnClick(this.sendBtn);
			break;
		case "updateMyInfo":
			this.handleUpdateMyInfoBtnClick(this.updateMyInfoBtn);
			break;
		case "exit":
			this.handleExitBtnClick(this.exitBtn);
			break;
		}

	}
	private void handleOnlineUserClick(JLabel u){
		String ip=u.getName();
		UserDM to=this.model.checkUser(ip);
		this.showPrivateChatDialog(to);
	}
	private void handleExitBtnClick(JButton btn) {
		try {
			btn.setEnabled(false);
			model.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			btn.setEnabled(true);
			//System.exit(0);
		}
	}

	private void handleUpdateMyInfoBtnClick(JButton btn) {
		try {
			btn.setEnabled(false);
			this.showUpdateMyInfoDialog();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			btn.setEnabled(true);
		}
	}

	private void handleSendBtnClick(JButton btn) {
		try {
			btn.setEnabled(false);
			String message = ThisAppUtil.ifBlank(this.inputUI.getText(), null);
			if (message != null) {
				this.inputUI.setText("");
				ChatMessage m=model.chatWithAll(message);
				this.messageListUI.addMessage(m,true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			btn.setEnabled(true);
		}
	}

	@Override
	public void userOnLine(final UserDM u) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JLabel comp=createOnlineUserUI(u);
				onlineListUI.add(comp);
				onlineListUI.doLayout();
				onlineUserUIMap.put(u.getIp().getHostAddress(), comp);
			}
		});
	}

	@Override
	public void userOffLine(UserDM u) {
		final JLabel comp=onlineUserUIMap.remove(u.getIp().getHostAddress());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if(comp!=null){
					onlineListUI.remove(comp);
					onlineListUI.updateUI();
				}
			}
		});
	}

	@Override
	public void receiveChatMessage(final ChatMessage m) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if(m.getType()==0){
					messageListUI.addMessage(m);
				}else{
					String ip=m.getUser().getIp().getHostAddress();
					PrivateChatWindow win=privateChatWindowMap.get(ip);			
					win.receiveMessage(m);
				}
			}
		});
	}
	private Map<String,PrivateChatWindow> privateChatWindowMap;
	private void showPrivateChatDialog(UserDM to){
		if(this.privateChatWindowMap==null){
			this.privateChatWindowMap=new HashMap<String, PrivateChatWindow>();
		}
		String ip=to.getIp().getHostAddress();
		PrivateChatWindow win=this.privateChatWindowMap.get(ip);
		if(win==null){
			this.privateChatWindowMap.put(ip, win=new PrivateChatWindow(this));
			win.setModel(model);
			win.setChatUser(to);
		}
		win.setLocationRelativeTo(win);
		win.setVisible(true);
	}
	private class OnlineUserClickListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel userLable=(JLabel) e.getSource();
			String ip=userLable.getName();
			UserDM to=model.checkUser(ip);
			showPrivateChatDialog(to);
		}
	}
	private OnlineUserClickListener onlineUserClickListener=new OnlineUserClickListener();
	private JLabel createOnlineUserUI(UserDM u) {
		JLabel mLabel = new JLabel(u.getName());
		mLabel.setHorizontalAlignment(JLabel.CENTER);
		mLabel.setBackground(Color.RED);
		mLabel.setName(u.getIp().getHostAddress());
		mLabel.addMouseListener(onlineUserClickListener);
		return mLabel;
	}
	@Override
	public void userChangeInfo(UserDM u) {
		String ip=u.getIp().getHostAddress();
		JLabel comp=this.onlineUserUIMap.get(ip);
		if(comp!=null){
			comp.setText(u.getName());
			//this.onlineUserUIMap.put(ip, comp);
		}
	}

	@Override
	public void receiveInputStreamMessage(InputStreamMessage m) {
		String ip=m.getForm().getIp().getHostAddress();
		PrivateChatWindow win=privateChatWindowMap.get(ip);			
		win.receiveMessage(m);
	}

	@Override
	public void setVisible(boolean flag) {
		super.setVisible(flag);
		if (flag) {
			try {
				model.login(InetAddress.getLocalHost().getHostAddress());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private UpdateMyInfoDialog updateMyInfoDialog;
	public void showUpdateMyInfoDialog(){
		if(updateMyInfoDialog==null){
			updateMyInfoDialog=new UpdateMyInfoDialog(this);
			
			updateMyInfoDialog.setLocationRelativeTo(this);
		}
		updateMyInfoDialog.setVisible(true);
	}
	public void updateMyInfo(String name){
		UserDM dm=model.getMyselfInfo();
		this.nameUI.setText(name+"("+dm.getIp().getHostAddress()+")");
		model.updateUserInfo(name);
	}
	
	
}
