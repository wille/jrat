package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.common.DropLocations;
import jrat.controller.Help;
import jrat.controller.settings.Settings;
import jrat.controller.ui.components.JPlaceholderTextField;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelBuildStartup extends JPanel {

	private JTextField txtName;
	private JComboBox<String> comboBox;
	private JCheckBox chckbxMeltDropperAfter;
	private JButton button;
	private JCheckBox chckbxHideInstalledFile;
	private JCheckBox chckbxRunNextStart;
	private JCheckBox chckbxDisableInstalling;

	public String getJarName() {
		return txtName.getText().trim();
	}

	public int getDropLocation() {
		return comboBox.getSelectedIndex();
	}
	
	public boolean shouldRunNextBoot() {
		return chckbxRunNextStart.isSelected();
	}

	public boolean shouldMelt() {
		return chckbxMeltDropperAfter.isSelected();
	}
	
	public boolean shouldHideFile() {
		return chckbxHideInstalledFile.isSelected();
	}

	public boolean dontInstall() {
		return chckbxDisableInstalling.isSelected();
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
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
					.addContainerGap())
		);

		JLabel lblInstallIn = new JLabel("Install in:");

		comboBox = new JComboBox<String>();
		comboBox.setToolTipText("Drop directory");
		comboBox.setModel(new DefaultComboBoxModel<String>(DropLocations.STRINGS));

		JLabel lblDroppedFileAnd = new JLabel("File and reg key name:");

		txtName = new JPlaceholderTextField("jrat");
		txtName.setText(Settings.getGlobal().getString(Settings.KEY_INSTALLATION_NAME));
		txtName.setToolTipText("File name of installed file and startup entry or registry key");
		txtName.setColumns(10);

		chckbxMeltDropperAfter = new JCheckBox("Melt installer after it has been run (Delete itself)");
		chckbxMeltDropperAfter.setToolTipText("The installer will delete itself when it is executed, disappearing.");

		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Required for installed stub to come back on system reboot");
			}
		});
		button.setIcon(IconUtils.getIcon("help"));

		chckbxHideInstalledFile = new JCheckBox("Hide installed file");
		
		chckbxRunNextStart = new JCheckBox("Run next start");
		
		JLabel label = new JLabel("");
		label.setIcon(IconUtils.getIcon("os_win"));
		
		chckbxDisableInstalling = new JCheckBox("Disable installing");
		chckbxDisableInstalling.setForeground(Color.RED);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxDisableInstalling)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxRunNextStart)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label))
						.addComponent(chckbxHideInstalledFile)
						.addComponent(chckbxMeltDropperAfter)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInstallIn)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDroppedFileAnd)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)))
						.addComponent(button))
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
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxDisableInstalling)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(button)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
