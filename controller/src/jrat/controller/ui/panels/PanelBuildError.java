package jrat.controller.ui.panels;

import jrat.api.Resources;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class PanelBuildError extends JPanel {

	private JCheckBox chckbxEnableErrorLogging;

	public boolean errorLogging() {
		return chckbxEnableErrorLogging.isSelected();
	}

	public PanelBuildError() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Error Handling"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE).addContainerGap(111, Short.MAX_VALUE)));

		chckbxEnableErrorLogging = new JCheckBox("Enable error logging");

		JLabel label = new JLabel("");
		label.setIcon(Resources.getIcon("error"));

		JLabel lblWhenTheServer = new JLabel("When the stub crashes, it will make a error log in log.dat");

		JLabel lblInTheServer = new JLabel("in the stub directory that you can access and read");

		JLabel lblNextTimeIt = new JLabel("next time it connects in control panel.");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(20).addComponent(label).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblNextTimeIt).addComponent(lblInTheServer).addComponent(lblWhenTheServer).addComponent(chckbxEnableErrorLogging)).addContainerGap(96, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxEnableErrorLogging, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblWhenTheServer).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblInTheServer).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNextTimeIt).addContainerGap(56, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
