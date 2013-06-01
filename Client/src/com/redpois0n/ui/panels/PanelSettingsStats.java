package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.redpois0n.Settings;
import com.redpois0n.Statistics;

@SuppressWarnings("serial")
public class PanelSettingsStats extends JPanel {

	private JCheckBox chckbxTrackStatistics;

	public boolean trackStats() {
		return chckbxTrackStatistics.isSelected();
	}

	public PanelSettingsStats() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Statistics settings"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));

		chckbxTrackStatistics = new JCheckBox("Track statistics");
		chckbxTrackStatistics.setSelected(Settings.getBoolean("stats"));

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelSettingsStats.class.getResource("/icons/statistics.png")));

		JButton btnClearStats = new JButton("Clear stats");
		btnClearStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Confirm clearing all stats (Cant be undone)", "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					Statistics.list.clear();
					Statistics.saveEmpty();
					Statistics.reload();
				}
			}
		});
		btnClearStats.setIcon(new ImageIcon(PanelSettingsStats.class.getResource("/icons/delete.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(50).addComponent(label).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(btnClearStats).addComponent(chckbxTrackStatistics)).addContainerGap(247, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxTrackStatistics, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClearStats).addContainerGap(189, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
