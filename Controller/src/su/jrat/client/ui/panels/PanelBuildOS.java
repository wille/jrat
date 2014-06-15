package su.jrat.client.ui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import su.jrat.client.Help;
import su.jrat.client.OSConfig;
import su.jrat.common.OperatingSystem;


@SuppressWarnings("serial")
public class PanelBuildOS extends JPanel {

	public JCheckBox chckbxMacOsx;
	public JCheckBox chckbxWindows;
	public JCheckBox chckbxLinux;
	public JCheckBox chckbxFreebsd;
	public JCheckBox chckbxSolaris;
	public JCheckBox chckbxOpenbsd;

	public boolean useWindows() {
		return chckbxWindows.isSelected();
	}

	public boolean useMac() {
		return chckbxMacOsx.isSelected();
	}

	public boolean useLinux() {
		return chckbxLinux.isSelected();
	}

	public boolean useFreeBSD() {
		return chckbxFreebsd.isSelected();
	}

	public boolean useOpenBSD() {
		return chckbxOpenbsd.isSelected();
	}

	public boolean useSolaris() {
		return chckbxSolaris.isSelected();
	}

	public OSConfig getConfig() {
		OSConfig config = new OSConfig();

		if (chckbxWindows.isEnabled()) {
			config.addOS(OperatingSystem.WINDOWS);
		}
		if (chckbxMacOsx.isEnabled()) {
			config.addOS(OperatingSystem.LINUX);
		}
		if (chckbxLinux.isEnabled()) {
			config.addOS(OperatingSystem.OSX);
		}
		if (chckbxOpenbsd.isEnabled()) {
			config.addOS(OperatingSystem.OPENBSD);
		}
		if (chckbxSolaris.isEnabled()) {
			config.addOS(OperatingSystem.SOLARIS);
		}
		if (chckbxFreebsd.isEnabled()) {
			config.addOS(OperatingSystem.FREEBSD);
		}

		return config;
	}

	public PanelBuildOS() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Allowed operating systems"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addContainerGap()));

		chckbxWindows = new JCheckBox("Windows (Recommended)");
		chckbxWindows.setSelected(true);

		chckbxMacOsx = new JCheckBox("Mac OSX");
		chckbxMacOsx.setSelected(true);

		chckbxLinux = new JCheckBox("Linux");
		chckbxLinux.setSelected(true);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/os.png")));

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/os_mac.png")));

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/os_linux.png")));

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Selects what operating systems the stub is allowed to execute on");
			}
		});
		button.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/help.png")));

		chckbxFreebsd = new JCheckBox("FreeBSD");
		chckbxFreebsd.setForeground(Color.RED);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/freebsd.png")));

		chckbxOpenbsd = new JCheckBox("OpenBSD");
		chckbxOpenbsd.setForeground(Color.RED);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/openbsd.png")));

		chckbxSolaris = new JCheckBox("Solaris");
		chckbxSolaris.setForeground(Color.RED);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(PanelBuildOS.class.getResource("/icons/solaris.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(67).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(label).addComponent(label_1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE).addComponent(label_2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE).addComponent(label_3, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE).addComponent(label_4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))).addComponent(label_5, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxOpenbsd).addComponent(chckbxLinux).addComponent(chckbxMacOsx).addComponent(chckbxWindows).addComponent(chckbxFreebsd).addComponent(chckbxSolaris))).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(button, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))).addContainerGap(184, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxWindows, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxMacOsx).addComponent(label_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxLinux).addComponent(label_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel.createSequentialGroup().addComponent(chckbxFreebsd).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxOpenbsd)).addGroup(gl_panel.createSequentialGroup().addComponent(label_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(label_4, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chckbxSolaris)).addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent(label_5, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))).addGap(39).addComponent(button).addContainerGap()));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}