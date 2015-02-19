package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.jrat.client.Help;
import se.jrat.client.settings.Settings;
import se.jrat.common.DropLocations;


@SuppressWarnings("serial")
public class PanelBuildStartup extends JPanel {

	private JTextField txtName;
	private JComboBox<String> comboBox;
	private JCheckBox chckbxMeltDropperAfter;
	private JButton button;
	private JCheckBox chckbxHideInstalledFile;
	private JCheckBox chckbxRunNextStart;

	public String getJarname() {
		return txtName.getText().trim();
	}

	public int droploc() {
		return comboBox.getSelectedIndex();
	}
	
	public boolean runNext() {
		return chckbxRunNextStart.isSelected();
	}

	public boolean melt() {
		return chckbxMeltDropperAfter.isSelected();
	}

	public PanelBuildStartup() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Startup (Installer)"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(83, Short.MAX_VALUE))
		);

		JLabel lblInstallIn = new JLabel("Install in:");

		comboBox = new JComboBox<String>();
		comboBox.setToolTipText("Drop directory");
		comboBox.setModel(new DefaultComboBoxModel<String>(DropLocations.STRINGS));

		JLabel lblDroppedFileAnd = new JLabel("File and reg key name:");

		txtName = new JTextField(Settings.getGlobal().getString("jarname"));
		txtName.setToolTipText("File name of installed file and key in registry in Windows");
		txtName.setColumns(10);

		chckbxMeltDropperAfter = new JCheckBox("Melt installer after it has been run (Delete itself)");
		chckbxMeltDropperAfter.setToolTipText("The installer will delete itself when it is executed, disappearing.");

		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Required for installed stub to come back on system reboot");
			}
		});
		button.setIcon(new ImageIcon(PanelBuildStartup.class.getResource("/icons/help.png")));

		chckbxHideInstalledFile = new JCheckBox("Hide installed file");
		
		chckbxRunNextStart = new JCheckBox("Run next start");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelBuildStartup.class.getResource("/icons/os_win.png")));

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxRunNextStart)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label))
						.addComponent(chckbxHideInstalledFile)
						.addComponent(chckbxMeltDropperAfter)
						.addComponent(button)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInstallIn)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDroppedFileAnd)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInstallIn)
						.addComponent(lblDroppedFileAnd))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxMeltDropperAfter)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxHideInstalledFile)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxRunNextStart)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(button)
					.addGap(37))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	public boolean hideFile() {
		return chckbxHideInstalledFile.isSelected();
	}
}
