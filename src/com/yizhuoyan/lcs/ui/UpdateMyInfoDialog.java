package com.yizhuoyan.lcs.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.yizhuoyan.lcs.util.ThisAppUtil;
/**
 * 修改个人信息模式框
 *
 */
public class UpdateMyInfoDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = -5515821373008562097L;
	private final ChatWindow window;
	private JTextField nameInputUI;
	public UpdateMyInfoDialog(ChatWindow win) {
		this.window=win;
		this.setAutoRequestFocus(true);
		this.setTitle("修改个人信息");
		this.setResizable(false);
		this.initLayout();
		this.pack();
	}

	private void initLayout() {
		BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		this.setLayout(layout);
		{
			JPanel formRow = new JPanel(new BorderLayout());
			formRow.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			JLabel itemLb = new JLabel("昵称:");
			formRow.add(itemLb, BorderLayout.WEST);
			JTextField inputUI = new JTextField(16);
			formRow.add(inputUI);
			this.add(formRow);
			this.nameInputUI=inputUI;
		}
		{
			JPanel actionRow = new JPanel();
			JButton confirmBtn = new JButton("确定");
			actionRow.add(confirmBtn);
			JButton cancelBtn = new JButton("取消");
			actionRow.add(cancelBtn);
			this.add(actionRow);
			confirmBtn.setActionCommand("confirm");
			confirmBtn.addActionListener(this);
			cancelBtn.setActionCommand("cancel");
			cancelBtn.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			
		case "confirm":
			this.handleConfimBtnClick((JButton) e.getSource());
			break;
		case "cancel":
			this.handleCancelBtnClick((JButton) e.getSource());
			break;

		}
	}
	private void handleConfimBtnClick(JButton btn){
		try {
			btn.setEnabled(false);
			String name = ThisAppUtil.ifBlank(this.nameInputUI.getText(), null);
			if (name != null) {
				window.updateMyInfo(name);
			}
			this.setVisible(false);
		} finally {
			btn.setEnabled(true);
		}
	}
	
	private void handleCancelBtnClick(JButton btn){
		this.setVisible(false);
	}
}
