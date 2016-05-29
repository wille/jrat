package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Help;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;


@SuppressWarnings("serial")
public class PanelBuildTimeout extends JPanel {

	private JSpinner spinner;
	private JCheckBox chckbxEnableTimeout;

	public boolean enabled() {
		return chckbxEnableTimeout.isSelected();
	}

	public int getSecondsAsMilli() {
		return Integer.parseInt(spinner.getValue().toString()) * 1000;
	}

	public PanelBuildTimeout() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Socket timeout"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));

		chckbxEnableTimeout = new JCheckBox("Enable timeout");
		chckbxEnableTimeout.setSelected(true);

		JLabel lblSeconds = new JLabel("Seconds:");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(15, 1, 9999, 1));

		JButton btnRandom = new JButton("");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinner.setValue((new Random()).nextInt(9999));
			}
		});
		btnRandom.setIcon(IconUtils.getIcon("random"));

		JLabel lblSetsTheSocket = new JLabel("Sets the socket timeout");

		JLabel lblDefaultSeconds = new JLabel("Default: 15 seconds");

		JButton btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Sets the default socket timeout.");
			}
		});
		btnHelp.setIcon(IconUtils.getIcon("help"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(66).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblDefaultSeconds).addComponent(lblSetsTheSocket).addGroup(gl_panel.createSequentialGroup().addComponent(lblSeconds).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinner, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRandom).addGap(6).addComponent(btnHelp)).addComponent(chckbxEnableTimeout)).addContainerGap(96, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(chckbxEnableTimeout).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblSetsTheSocket).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblDefaultSeconds).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(18).addComponent(lblSeconds)).addGroup(gl_panel.createSequentialGroup().addGap(15).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(btnHelp).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnRandom))))).addContainerGap(147, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
