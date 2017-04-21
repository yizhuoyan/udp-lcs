package com.yizhuoyan.lcs.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.yizhuoyan.lcs.model.data.UserDM;
import com.yizhuoyan.lcs.model.data.message.ChatMessage;
import com.yizhuoyan.lcs.model.data.message.InputStreamMessage;
import com.yizhuoyan.lcs.model.function.FunctionModel;
import com.yizhuoyan.lcs.ui.componet.MessagePanel;
import com.yizhuoyan.lcs.util.ThisAppUtil;
/**
 * 私聊窗口
 *
 */
public class PrivateChatWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -7890823468474035823L;
	private final ChatWindow window;
	private JTextField inputUI;
	private UserDM chatUser;
	private FunctionModel model;
	private MessagePanel messageListUI;

	public PrivateChatWindow(ChatWindow win) {
		this.window = win;
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setAutoRequestFocus(true);
		this.setSize(400, 500);
		this.setAlwaysOnTop(true);
		this.initLayout();
	}

	public void setModel(FunctionModel model) {
		this.model = model;
	}

	public void setChatUser(UserDM user) {
		this.chatUser = user;
		this.setTitle("私聊:" + user.getName());
		this.messageListUI.removeAll();
	}

	private void initLayout() {

		{
			MessagePanel messagePanel = new MessagePanel();
			JScrollPane scrollPane = new JScrollPane(messagePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			this.messageListUI=messagePanel;
			this.add(scrollPane);
		}
		{
			JPanel actionPanel = new JPanel(new BorderLayout());
			{
				JTextField inputUI = new JTextField();
				actionPanel.add(inputUI);
				this.inputUI = inputUI;
			}
			{
				JPanel btnPanel = new JPanel();
				JButton sendBtn = new JButton("发送");
				btnPanel.add(sendBtn);
				JButton sendFileBtn = new JButton("发送小文件(<10KB)");
				btnPanel.add(sendFileBtn);
				
				sendBtn.setActionCommand("send");
				sendBtn.addActionListener(this);
				sendFileBtn.setActionCommand("sendFile");
				sendFileBtn.addActionListener(this);
				actionPanel.add(btnPanel, BorderLayout.EAST);
			}
			this.add(actionPanel,BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {

		case "send":
			this.handleSendBtnClick((JButton) e.getSource());
			break;
		case "sendFile":
			this.handleSendFileBtnClick((JButton) e.getSource());
			break;

		}
	}

	private void handleSendBtnClick(JButton btn) {
		try {
			btn.setEnabled(false);
			String message = ThisAppUtil.ifBlank(this.inputUI.getText(), null);
			if (message != null) {
				this.inputUI.setText("");
				ChatMessage m = this.model.chatWithOne(chatUser, message);
				this.messageListUI.addMessage(m, true);
			}
		} finally {
			btn.setEnabled(true);
		}
	}

	private void handleSendFileBtnClick(JButton btn) {
		JFileChooser chooser = new JFileChooser();
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       File file=chooser.getSelectedFile();
	       model.transferFile(chatUser, file);
	    }

	}

	public void receiveMessage(ChatMessage m) {
		this.messageListUI.addMessage(m);
	}
	public void receiveMessage(InputStreamMessage m) {
		ChatMessage chat=new ChatMessage();
		chat.setUser(m.getForm());
		chat.setMessage("收到文件:"+m.getFileName());
		this.messageListUI.addMessage(chat);
	}
}
