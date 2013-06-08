package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.redpois0n.Settings;
import com.redpois0n.ui.components.JPortSpinner;

@SuppressWarnings("serial")
public class PanelSettingsProxy extends JPanel {
	
	private JTextField txtIP;
	private JPortSpinner spPort;
	private JComboBox<String> cbType;
	private JCheckBox chckbxUseProxy;

	public PanelSettingsProxy() {
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Proxy"));
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
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		chckbxUseProxy = new JCheckBox("Use Proxy");
		chckbxUseProxy.setSelected(Settings.getGlobal().getBoolean("proxy"));
		
		JLabel lblHost = new JLabel("Host:");
		
		txtIP = new JTextField();
		txtIP.setText(Settings.getGlobal().get("proxyhost"));
		txtIP.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		
		spPort = new JPortSpinner(Settings.getGlobal().getInt("proxyport"));
		
		JLabel lblType = new JLabel("Type:");
		
		cbType = new JComboBox<String>();
		cbType.setModel(new DefaultComboBoxModel<String>(new String[] {"SOCKS", "HTTP"}));
		cbType.setSelectedIndex(Settings.getGlobal().getBoolean("proxysocks") ? 0 : 1);
		
		JButton btnTest = new JButton("Test");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {	
					Proxy proxy = new Proxy(useSocks() ? Proxy.Type.SOCKS : Proxy.Type.HTTP, new InetSocketAddress(getHost(), getPort()));
					URLConnection connection = new URL("http://jrat-project.org/misc/getip.php").openConnection(proxy);
						
					String ip = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();	

					JOptionPane.showMessageDialog(null, "Connection successful, current IP address: " + ip, "Proxy Test", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Failed to connect to proxy: " + ex.getClass().getSimpleName() + ": " + ex.getMessage(), "Proxy Test", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxUseProxy)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnTest))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblHost)
								.addComponent(lblPort)
								.addComponent(lblType))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
								.addComponent(spPort, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbType, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(218, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxUseProxy)
						.addComponent(btnTest))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHost))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPort)
						.addComponent(spPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(cbType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(132, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	public boolean useProxy() {
		return chckbxUseProxy.isSelected();
	}

	public boolean useSocks() {
		return cbType.getSelectedIndex() == 0;
	}

	public String getHost() {
		return txtIP.getText().trim();
	}
	
	public int getPort() {
		return (Integer)spPort.getValue();
	}
}
