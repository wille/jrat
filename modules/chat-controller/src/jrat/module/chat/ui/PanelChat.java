package jrat.module.chat.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.chat.PacketChatAction;
import jrat.module.chat.Type;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class PanelChat extends ClientPanel {

    public Slave slave;
	public JTextField txtMsg;
	public JTextPane txtChat;

	public PanelChat(Slave slave) {
		super(slave, "Chat", Resources.getIcon("chat"));
		this.slave = slave;

		setBounds(100, 100, 450, 300);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		txtMsg = new JTextField();
		toolBar.add(txtMsg);
		txtMsg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}
			}
		});
		txtMsg.setColumns(10);

		JButton btnSend = new JButton("Send");
		toolBar.add(btnSend);
		btnSend.setIcon(Resources.getIcon("arrow-right"));

		JButton btnNudge = new JButton("Nudge");
		toolBar.add(btnNudge);
		btnNudge.setIcon(Resources.getIcon("nudge"));
		btnNudge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send(Type.NUDGE);
			}
		});
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		txtChat = new JTextPane();
		scrollPane.setViewportView(txtChat);
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE).addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE).addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		setLayout(gl_contentPane);

		send(Type.START);
	}

	public void addRemoteMessage(String message) {
        try {
            StyleContext sc = StyleContext.getDefaultStyleContext();
            AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.red);
            txtChat.getDocument().insertString(txtChat.getDocument().getLength(), "Remote: " + message + "\n", set);
            txtChat.setSelectionEnd(txtChat.getText().length());
            txtChat.setSelectionStart(txtChat.getText().length());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	public void send() {
		try {
			if (txtMsg.getText().length() > 0) {
				String msg = txtMsg.getText().trim();

				send(msg);

				StyleContext sc = StyleContext.getDefaultStyleContext();
				AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.blue);
				txtChat.getDocument().insertString(txtChat.getDocument().getLength(), "You: " + txtMsg.getText().trim() + "\n", set);
				txtMsg.setText("");
				txtChat.setSelectionEnd(txtChat.getText().length());
				txtChat.setSelectionStart(txtChat.getText().length());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void send(String message) {
	    slave.addToSendQueue(new PacketChatAction(Type.MESSAGE, message));
    }

    private void send(Type type) {
	    slave.addToSendQueue(new PacketChatAction(type));
    }

	@Override
	public void dispose() {
	    super.dispose();
		send(Type.END);
	}
}
