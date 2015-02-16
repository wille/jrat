package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import se.jrat.client.Help;
import se.jrat.client.listeners.SocketComboBoxListener;
import se.jrat.client.net.PortListener;
import se.jrat.client.settings.Settings;
import se.jrat.client.ui.components.PortListenerJComboBox;


@SuppressWarnings("serial")
public class PanelBuildGeneral extends JPanel {

	public JPasswordField passPass;
	public JTextField txtID;
	private JComboBox<?> comboBox;

	public String getPass() {
		return new String(passPass.getPassword());
	}

	public String getID() {
		return txtID.getText().trim();
	}

	public PanelBuildGeneral() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Security"));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("General"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE).addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE).addContainerGap()));

		txtID = new JTextField(Settings.getGlobal().getString("bid"));
		txtID.setColumns(10);

		JLabel lblServerId = new JLabel("Stub ID");
		lblServerId.setHorizontalAlignment(SwingConstants.TRAILING);

		JLabel lblNameOfServer = new JLabel("Name of Stub on connect");

		JButton btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Name of stub on connect, like \"Brother\"");
			}
		});
		btnHelp.setIcon(new ImageIcon(PanelBuildGeneral.class.getResource("/icons/help.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblServerId, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNameOfServer)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(txtID, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnHelp, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(75, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnHelp, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblServerId))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNameOfServer)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);

		JLabel lblSecurityPassword = new JLabel("Security Password");
		lblSecurityPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSecurityPassword.setBounds(10, 35, 133, 14);

		passPass = new JPasswordField(Settings.getGlobal().getString("bpass"));
		passPass.setBounds(147, 32, 174, 20);

		JButton btnShowPassword = new JButton("Show password");
		btnShowPassword.setBounds(147, 117, 107, 23);
		btnShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Password: " + new String(passPass.getPassword()), "Show passwords", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JButton button = new JButton("");
		button.setBounds(147, 81, 37, 25);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Encryption key for dropper, settings, and communication, needs to be 24 in length");
			}
		});
		button.setIcon(new ImageIcon(PanelBuildGeneral.class.getResource("/icons/help.png")));
		panel.setLayout(null);
		panel.add(lblSecurityPassword);
		panel.add(passPass);
		panel.add(btnShowPassword);
		panel.add(button);
		setLayout(groupLayout);

		comboBox = new PortListenerJComboBox(new SocketComboBoxListener() {
			@Override
			public void onChange(PortListener pl) {
				passPass.setText(pl.getPass());
			}
		});

		comboBox.setBounds(147, 146, 107, 20);
		panel.add(comboBox);

		JLabel lblLoad = new JLabel("Load");
		lblLoad.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLoad.setBounds(10, 149, 133, 14);
		panel.add(lblLoad);
	}
}
