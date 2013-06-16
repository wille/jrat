package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import com.redpois0n.ErrorDialog;
import com.redpois0n.Help;
import com.redpois0n.settings.Settings;
import com.redpois0n.utils.NetworkUtils;


@SuppressWarnings("serial")
public class PanelBuildNetwork extends JPanel {

	private JTextField txtIP;
	private JSpinner spinPort;
	private JSpinner spinRate;

	public String getIP() {
		return txtIP.getText().trim();
	}

	public int getPort() {
		return Integer.parseInt(spinPort.getValue().toString());
	}

	public int getConnectionRate() {
		return Integer.parseInt(spinRate.getValue().toString());
	}

	public PanelBuildNetwork() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Network"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(158, Short.MAX_VALUE))
		);

		JLabel lblIp = new JLabel("IP:");

		txtIP = new JTextField(Settings.getGlobal().getString("bip"));
		txtIP.setColumns(10);

		JLabel lblPort = new JLabel("Port:");

		spinPort = new JSpinner();
		spinPort.setModel(new SpinnerNumberModel(Settings.getGlobal().getInt("bport"), 1, 65535, 1));

		JLabel lblReconnectRate = new JLabel("Reconnect rate:");

		spinRate = new JSpinner();
		spinRate.setToolTipText("Seconds between server reconnect attempts");
		spinRate.setModel(new SpinnerNumberModel(Settings.getGlobal().getInt("brecat"), 1, 120, 1));

		JLabel lblseconds = new JLabel("(seconds)");

		JButton btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Seconds between each reconnect attempt, recommended 10");
			}
		});
		btnHelp.setIcon(new ImageIcon(PanelBuildNetwork.class.getResource("/icons/help.png")));
		
		JButton btnLocal = new JButton("");
		btnLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtIP.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (Exception e1) {
					e1.printStackTrace();
					ErrorDialog.create(e1);
				}
			}
		});
		btnLocal.setIcon(new ImageIcon(PanelBuildNetwork.class.getResource("/icons/network-ip-local.png")));
		
		JButton btnExternal = new JButton("");
		btnExternal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtIP.setText(NetworkUtils.getIP());
				} catch (Exception e1) {
					e1.printStackTrace();
					ErrorDialog.create(e1);
				}
			}
		});
		btnExternal.setIcon(new ImageIcon(PanelBuildNetwork.class.getResource("/icons/network-ip.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblReconnectRate)
						.addComponent(lblIp)
						.addComponent(lblPort))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(spinPort, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnLocal, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnExternal, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(spinRate, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblseconds)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnHelp)))
					.addContainerGap(53, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnExternal, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblIp))
						.addComponent(btnLocal))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPort)
						.addComponent(spinPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblReconnectRate)
						.addComponent(spinRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblseconds)
						.addComponent(btnHelp))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
