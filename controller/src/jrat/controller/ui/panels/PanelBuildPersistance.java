package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Help;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelBuildPersistance extends JPanel {

	private JCheckBox chckbxEnableRegistryPersistance;
	private JSpinner spinner;

	public boolean isPersistanceEnabled() {
		return chckbxEnableRegistryPersistance.isSelected();
	}

	public int getMS() {
		return Integer.parseInt(spinner.getValue().toString()) * 1000;
	}

	public PanelBuildPersistance() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Persistance"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE).addContainerGap(106, Short.MAX_VALUE)));

		chckbxEnableRegistryPersistance = new JCheckBox("Enable startup persistance");
		chckbxEnableRegistryPersistance.setSelected(true);

		JLabel lblCheckingInterval = new JLabel("Checking interval:");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 1, 60, 1));

		JLabel lblSeconds = new JLabel("seconds");

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("The interval in seconds between each startup check");
			}
		});
		button.setIcon(Resources.getIcon("help"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(47).addComponent(chckbxEnableRegistryPersistance)).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(lblCheckingInterval).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinner, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblSeconds).addPreferredGap(ComponentPlacement.RELATED).addComponent(button))).addContainerGap(141, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(8).addComponent(chckbxEnableRegistryPersistance).addGap(18).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblCheckingInterval).addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblSeconds).addComponent(button)).addContainerGap(86, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
