package pro.jrat.client.ui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import pro.jrat.client.Help;

@SuppressWarnings("serial")
public class PanelBuildMutex extends JPanel {
	private JTextField txtMutex;
	private JCheckBox chckbxEnableMutex;

	public boolean useMutex() {
		return chckbxEnableMutex.isSelected();
	}

	public int mutexPort() {
		return Integer.parseInt(txtMutex.getText().trim());
	}

	/**
	 * Create the panel.
	 */
	public PanelBuildMutex() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Mutex (Only allow one instance)"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE).addContainerGap(87, Short.MAX_VALUE)));

		chckbxEnableMutex = new JCheckBox("Enable mutex");

		JLabel lblMutexPort = new JLabel("Mutex port");

		txtMutex = new JTextField();
		txtMutex.setForeground(Color.BLUE);
		txtMutex.setText("1334");
		txtMutex.setColumns(10);

		JButton btnRandom = new JButton("");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtMutex.setText("" + (new Random()).nextInt(65535));
			}
		});
		btnRandom.setIcon(new ImageIcon(PanelBuildMutex.class.getResource("/icons/random.png")));

		JLabel lblWillCreateA = new JLabel("Will create a socket at selected port, and occupy it");

		JLabel lblToMakeSure = new JLabel("to make sure only one instance of the server is running");

		JLabel lblSelectRareUsed = new JLabel("Select rare used ports.");

		JButton btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("Will help to prevent dual connections from the same computer");
			}
		});
		btnHelp.setIcon(new ImageIcon(PanelBuildMutex.class.getResource("/icons/help.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGap(53).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(lblSelectRareUsed).addComponent(lblToMakeSure).addComponent(lblWillCreateA).addComponent(chckbxEnableMutex).addGroup(gl_panel.createSequentialGroup().addComponent(lblMutexPort).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtMutex, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE).addGap(6).addComponent(btnRandom).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnHelp, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))).addContainerGap(59, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(btnHelp).addGroup(gl_panel.createSequentialGroup().addComponent(chckbxEnableMutex).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblWillCreateA).addGap(5).addComponent(lblToMakeSure).addGap(4).addComponent(lblSelectRareUsed).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(btnRandom).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(lblMutexPort).addComponent(txtMutex, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))).addContainerGap(65, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
