package se.jrat.controller.ui.frames;

import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet48ChatStart;
import se.jrat.controller.packets.outgoing.Packet49ChatEnd;
import se.jrat.controller.packets.outgoing.Packet51ChatMessage;
import se.jrat.controller.packets.outgoing.Packet58NudgeChat;

@SuppressWarnings("serial")
public class FrameRemoteChat extends BaseFrame {

	private JPanel contentPane;
	public Slave slave;
	public static final Map<Slave, FrameRemoteChat> instances = new HashMap<Slave, FrameRemoteChat>();
	public JTextField txtMsg;
	public JTextPane txtChat;

	public FrameRemoteChat(Slave slave) {
		super(slave);
		setTitle("Chat - " + "[" + slave.formatUserString() + "] - " + slave.getIP());
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
		setContentPane(contentPane);

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
		btnSend.setIcon(IconUtils.getIcon("arrow-right"));

		JButton btnNudge = new JButton("Nudge");
		toolBar.add(btnNudge);
		btnNudge.setIcon(IconUtils.getIcon("nudge"));
		btnNudge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet58NudgeChat());
			}
		});
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		txtChat = new JTextPane();
		scrollPane.setViewportView(txtChat);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE).addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 434, GroupLayout.PREFERRED_SIZE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE).addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		contentPane.setLayout(gl_contentPane);

		slave.addToSendQueue(new Packet48ChatStart());
	}

	public void send() {
		try {
			if (txtMsg.getText().length() > 0) {
				String msg = txtMsg.getText().trim();

				slave.addToSendQueue(new Packet51ChatMessage(msg));

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
		slave.addToSendQueue(new Packet49ChatEnd());
		instances.remove(slave);
		slave = null;
	}
}
