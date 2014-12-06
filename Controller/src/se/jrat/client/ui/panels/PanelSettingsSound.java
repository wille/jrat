package se.jrat.client.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.jrat.client.settings.Settings;


@SuppressWarnings("serial")
public class PanelSettingsSound extends JPanel {

	private JCheckBox chckbxOnConnect;
	private JCheckBox chckbxOnDisconnect;

	public boolean onConnect() {
		return chckbxOnConnect.isSelected();
	}

	public boolean onDisconnect() {
		return chckbxOnDisconnect.isSelected();
	}

	public PanelSettingsSound() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Sound settings"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));

		chckbxOnConnect = new JCheckBox("On Connect");
		chckbxOnConnect.setSelected(Settings.getGlobal().getBoolean("soundonc"));

		chckbxOnDisconnect = new JCheckBox("On Disconnect");
		chckbxOnDisconnect.setSelected(Settings.getGlobal().getBoolean("soundondc"));

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelSettingsSound.class.getResource("/icons/action_join.png")));

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(PanelSettingsSound.class.getResource("/icons/action_disconnect.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(63).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(label).addComponent(label_1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxOnConnect).addComponent(chckbxOnDisconnect)).addContainerGap(242, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(20).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxOnConnect, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(label_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxOnDisconnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(180, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
