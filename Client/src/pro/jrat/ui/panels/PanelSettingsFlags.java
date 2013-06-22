package pro.jrat.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pro.jrat.settings.Settings;


@SuppressWarnings("serial")
public class PanelSettingsFlags extends JPanel {
	
	private JRadioButton rdbtnUseSystemLanguage;
	private JRadioButton rdbtnUseLocationTo;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public Object useWeb() {
		return rdbtnUseLocationTo.isSelected();
	}
	
	public PanelSettingsFlags() {
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Locating connection"));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);
		
		rdbtnUseLocationTo = new JRadioButton("Use IP to Country library to get flag");
		buttonGroup.add(rdbtnUseLocationTo);
		rdbtnUseLocationTo.setSelected(Settings.getGlobal().getBoolean("geoip"));
		
		rdbtnUseSystemLanguage = new JRadioButton("Use system locale to get flag");
		rdbtnUseSystemLanguage.setSelected(!Settings.getGlobal().getBoolean("geoip"));
		buttonGroup.add(rdbtnUseSystemLanguage);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(53)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnUseLocationTo)
						.addComponent(rdbtnUseSystemLanguage))
					.addContainerGap(182, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(25)
					.addComponent(rdbtnUseLocationTo)
					.addGap(3)
					.addComponent(rdbtnUseSystemLanguage)
					.addContainerGap(94, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
