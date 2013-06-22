package pro.jrat.ui.panels;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import pro.jrat.Help;


@SuppressWarnings("serial")
public class PanelBuildKeylogger extends JPanel {

	private JCheckBox chckbxEnableKeylogger;
	private JCheckBox chckbxEnableOfflineKeylogger;
	private JSpinner spinnerDelay;
	private JSpinner spinnerBreak;

	public int delayAsMilliSeconds() {
		return Integer.parseInt(spinnerDelay.getValue().toString()) * 1000;
	}

	public int lineBreak() {
		return Integer.parseInt(spinnerBreak.getValue().toString());
	}

	public boolean keylogger() {
		return chckbxEnableKeylogger.isSelected();
	}

	public boolean offlinekeylogger() {
		return chckbxEnableOfflineKeylogger.isSelected();
	}

	public PanelBuildKeylogger() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Keylogger settings"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE).addContainerGap(92, Short.MAX_VALUE)));

		chckbxEnableKeylogger = new JCheckBox("Enable keylogger");
		chckbxEnableKeylogger.setToolTipText("Export keylogger into server");

		JLabel lblCodegooglecompjnativehook = new JLabel("code.google.com/p/jnativehook/");
		lblCodegooglecompjnativehook.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("http://code.google.com/p/jnativehook/"));
				} catch (Exception ex) {
				}
			}
		});
		lblCodegooglecompjnativehook.setForeground(Color.BLUE);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Select if keylogger library should be exported with server.\nWill increase file size with around 150 kb");
			}
		});
		button.setIcon(new ImageIcon(PanelBuildKeylogger.class.getResource("/icons/help.png")));

		chckbxEnableOfflineKeylogger = new JCheckBox("Enable offline keylogger");
		chckbxEnableOfflineKeylogger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox src = (JCheckBox) arg0.getSource();
				if (src.isSelected()) {
					chckbxEnableKeylogger.setSelected(true);
					chckbxEnableKeylogger.setEnabled(false);
				} else {
					chckbxEnableKeylogger.setSelected(false);
					chckbxEnableKeylogger.setEnabled(true);
				}
			}
		});

		JLabel lblSavingDelay = new JLabel("Saving delay:");

		spinnerDelay = new JSpinner();
		spinnerDelay.setModel(new SpinnerNumberModel(5, 1, 9999, 1));

		JLabel lblLineBreak = new JLabel("Line break:");

		spinnerBreak = new JSpinner();
		spinnerBreak.setModel(new SpinnerNumberModel(30, 5, 9999, 1));

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelBuildKeylogger.class.getResource("/icons/keyboard.png")));

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(PanelBuildKeylogger.class.getResource("/icons/keyboard_arrow.png")));

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Delay in seconds between saving logs");
			}
		});
		button_1.setIcon(new ImageIcon(PanelBuildKeylogger.class.getResource("/icons/help.png")));

		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Line numbers before creating a new line");
			}
		});
		button_2.setIcon(new ImageIcon(PanelBuildKeylogger.class.getResource("/icons/help.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(32).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(label).addComponent(label_1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxEnableOfflineKeylogger).addComponent(chckbxEnableKeylogger)).addGap(217)).addGroup(gl_panel.createSequentialGroup().addGap(16).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblSavingDelay).addComponent(lblLineBreak)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false).addComponent(spinnerBreak).addComponent(spinnerDelay, GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(button_2, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE).addComponent(button_1))).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(button, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 200, Short.MAX_VALUE).addComponent(lblCodegooglecompjnativehook))).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(lblCodegooglecompjnativehook).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(label, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE).addComponent(chckbxEnableKeylogger, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(chckbxEnableOfflineKeylogger).addComponent(label_1, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblSavingDelay).addComponent(spinnerDelay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addComponent(button_1)).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(spinnerBreak, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblLineBreak).addComponent(button_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)).addGap(18).addComponent(button))).addGap(82)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
