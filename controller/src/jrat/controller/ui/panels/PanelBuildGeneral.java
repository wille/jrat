package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.controller.Help;
import jrat.controller.listeners.SocketComboBoxListener;
import jrat.controller.net.PortListener;
import jrat.controller.settings.Settings;
import jrat.controller.ui.components.JPlaceholderTextField;
import jrat.controller.ui.components.PortListenerJComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;


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

		txtID = new JPlaceholderTextField("Client");
		txtID.setText(Settings.getGlobal().getString(Settings.KEY_BUILD_ID));
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
		btnHelp.setIcon(IconUtils.getIcon("help"));
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

		passPass = new JPasswordField(Settings.getGlobal().getBuildPassword());

		JButton btnShowPassword = new JButton("Show password");
		btnShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Password: " + new String(passPass.getPassword()), "Show passwords", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Password to authenticate clients");
			}
		});
		button.setIcon(IconUtils.getIcon("help"));
		setLayout(groupLayout);

		comboBox = new PortListenerJComboBox(new SocketComboBoxListener() {
			@Override
			public void onChange(PortListener pl) {
				passPass.setText(pl.getPass());
			}
		});

		JLabel lblLoad = new JLabel("Load");
		lblLoad.setHorizontalAlignment(SwingConstants.TRAILING);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(141)
					.addComponent(btnShowPassword))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblLoad, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addComponent(lblSecurityPassword, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(passPass, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
					.addGap(103))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblSecurityPassword))
						.addComponent(passPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button)
					.addGap(34)
					.addComponent(btnShowPassword)
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblLoad))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
	}
}
