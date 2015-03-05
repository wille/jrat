package se.jrat.client.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class PanelBuildDebugMessages extends JPanel {

	private JCheckBox chckbxKeepDebugMessages;

	public boolean keepDebugMessages() {
		return chckbxKeepDebugMessages.isSelected();
	}

	public PanelBuildDebugMessages() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Debug Messages"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE).addContainerGap(11, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));

		chckbxKeepDebugMessages = new JCheckBox("Keep debug messages");
		chckbxKeepDebugMessages.setSelected(true);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelBuildDebugMessages.class.getResource("/icons/application-detail.png")));

		JLabel lblWillPrintOut = new JLabel("Will print out debug messages that the developer left");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(22).addComponent(label).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblWillPrintOut).addComponent(chckbxKeepDebugMessages)).addContainerGap(118, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxKeepDebugMessages, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(18).addComponent(lblWillPrintOut).addContainerGap(187, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
