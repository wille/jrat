package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.SettingsCustomID;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;


@SuppressWarnings("serial")
public class PanelSettingsMain extends JPanel {

	private JCheckBox chckbxRequestScreenOn;
	private JCheckBox chckbxTrayIcon;
	private JCheckBox chckbxAskBeforeConnect;
	private JCheckBox chckbxMaximum;
	private JSpinner spinner;

	public boolean askOnConnect() {
		return chckbxAskBeforeConnect.isSelected();
	}

	public boolean useTrayIcon() {
		return chckbxTrayIcon.isSelected();
	}

	public boolean useAutoScreen() {
		return chckbxRequestScreenOn.isSelected();
	}

	public boolean setMaximum() {
		return chckbxMaximum.isSelected();
	}

	public int getMaximum() {
		return chckbxMaximum.isSelected() ? (Integer) spinner.getValue() : -1;
	}

	public PanelSettingsMain() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Main"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE).addContainerGap(11, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));

		chckbxRequestScreenOn = new JCheckBox("Request screen on remote screen open");
		chckbxRequestScreenOn.setSelected(Settings.getGlobal().getBoolean("remotescreenstartup"));

		JLabel label = new JLabel("");
		label.setIcon(IconUtils.getIcon("screen"));

		chckbxTrayIcon = new JCheckBox("Tray Icon");
		chckbxTrayIcon.setSelected(Settings.getGlobal().getBoolean("traynote"));

		JLabel label_2 = new JLabel("");
		label_2.setIcon(IconUtils.getIcon("icon-16x16"));

		JButton btnClearRenamedServers = new JButton("Clear renamed connections");
		btnClearRenamedServers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Confirm clearing all saved names (Cant be undone)", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					SettingsCustomID.getGlobal().getIDList().clear();
				}
			}
		});
		btnClearRenamedServers.setIcon(IconUtils.getIcon("delete"));

		JLabel label_1 = new JLabel("");
		label_1.setIcon(IconUtils.getIcon("question"));

		chckbxAskBeforeConnect = new JCheckBox("Ask before connect", Settings.getGlobal().getBoolean("askurl"));

		JLabel label_3 = new JLabel("");
		label_3.setIcon(IconUtils.getIcon("computer"));

		chckbxMaximum = new JCheckBox("Maximum amount of connections", Settings.getGlobal().getInt("max") != -1);
		chckbxMaximum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinner.setEnabled(((JCheckBox) arg0.getSource()).isSelected());
			}
		});

		spinner = new JSpinner();
		spinner.setEnabled(chckbxMaximum.isSelected());
		int max = Settings.getGlobal().getInt("max");
		spinner.setModel(new SpinnerNumberModel(max == -1 ? 10 : max, 1, 999999999, 1));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(41).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(label_3).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxMaximum).addPreferredGap(ComponentPlacement.RELATED).addComponent(spinner, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(label_2).addComponent(label).addComponent(label_1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxTrayIcon).addComponent(chckbxRequestScreenOn).addComponent(chckbxAskBeforeConnect)))).addContainerGap(102, Short.MAX_VALUE)).addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addContainerGap(246, Short.MAX_VALUE).addComponent(btnClearRenamedServers).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxRequestScreenOn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(label_2, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE).addComponent(chckbxTrayIcon)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(chckbxAskBeforeConnect, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(label_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(chckbxMaximum, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(label_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(90).addComponent(btnClearRenamedServers).addContainerGap()));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
