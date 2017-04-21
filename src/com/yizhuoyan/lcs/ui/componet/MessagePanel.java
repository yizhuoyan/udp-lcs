package com.yizhuoyan.lcs.ui.componet;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.yizhuoyan.lcs.model.data.UserDM;
import com.yizhuoyan.lcs.model.data.message.ChatMessage;
/**
 * 自定义消息组件面板
 *
 */
public class MessagePanel extends JPanel {
	private static final long serialVersionUID = -3578687957773794324L;

	public MessagePanel() {
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
	}

	public void addMessage(ChatMessage message) {
		this.addMessage(message, false);
	}

	public void addMessage(ChatMessage message, boolean right) {
		final JLabel item = this.createMessageUI(message);
		if (right) {
			//item.setHorizontalAlignment(JLabel.RIGHT);
		}
		add(item);
		this.doLayout();
	}

	private JLabel createMessageUI(ChatMessage m) {
		StringBuilder message = new StringBuilder();
		UserDM from = m.getUser();
		message.append("<html><b>").append(from.getName()).append(":</b><br>").append(m.getMessage())
		.append("</html>");
		JLabel mLabel = new JLabel(message.toString());
		return mLabel;
	}

}
