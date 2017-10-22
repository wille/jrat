package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.controller.Help;
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
public class PanelBuildDelay extends JPanel {

	private JCheckBox chckbxEnableDelay;
	private JSpinner spinner;

	public boolean useDelay() {
		return chckbxEnableDelay.isSelected();
	}

	public int getDelayAsSeconds() {
		return Integer.parseInt(spinner.getValue().toString()) * 1000;
	}

	public PanelBuildDelay() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Dropper delay"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE).addContainerGap(16, Short.MAX_VALUE)));

		chckbxEnableDelay = new JCheckBox("Enable delay");

		JLabel lblWillWaitSelected = new JLabel("Will wait selected seconds before dropping");

		JLabel lblAndExecutingServer = new JLabel("and executing stub");

		JLabel lblSeconds = new JLabel("Seconds:");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10, 1, 9999, 1));

		JButton btnRandom = new JButton("");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinner.setValue((new Random()).nextInt(9999));
			}
		});
		btnRandom.setIcon(IconUtils.getIcon("random"));

		JButton btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Selects the second the dropper will wait until it is dropping all files.");
			}
		});
		btnHelp.setIcon(IconUtils.getIcon("help"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(61).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addComponent(lblSeconds).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinner, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRandom).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnHelp)).addComponent(lblAndExecutingServer).addComponent(lblWillWaitSelected).addComponent(chckbxEnableDelay)).addContainerGap(112, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(chckbxEnableDelay).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblWillWaitSelected).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addComponent(lblAndExecutingServer).addGap(18).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblSeconds).addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).addComponent(btnHelp).addComponent(btnRandom)).addContainerGap(141, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
