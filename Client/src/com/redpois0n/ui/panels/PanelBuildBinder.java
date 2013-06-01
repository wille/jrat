package com.redpois0n.ui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.redpois0n.Help;

@SuppressWarnings("serial")
public class PanelBuildBinder extends JPanel {

	private JTextField txtPath;
	private JTextField txtName;
	private JCheckBox chckbxBindFileTo;
	private JComboBox<String> comboBox;

	public String getFile() {
		return txtPath.getText().trim();
	}

	public String getName() {
		return txtName.getText().trim();
	}

	public boolean shouldBind() {
		return chckbxBindFileTo.isSelected();
	}

	public String dropTarget() {
		return comboBox.getSelectedItem().toString().toLowerCase();
	}

	public PanelBuildBinder() {

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Binder"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE).addContainerGap(15, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE).addContainerGap(51, Short.MAX_VALUE)));

		chckbxBindFileTo = new JCheckBox("Bind file to be ran on execution (Along with dropper)");

		JLabel lblPath = new JLabel("Path");

		txtPath = new JTextField();
		txtPath.setEditable(false);
		txtPath.setColumns(10);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser f = new JFileChooser();
				f.showOpenDialog(null);
				File file = f.getSelectedFile();
				if (file != null) {
					txtPath.setText(file.getAbsolutePath());
				}
			}
		});
		button.setIcon(new ImageIcon(PanelBuildBinder.class.getResource("/icons/folder.png")));

		JLabel lblDropPath = new JLabel("Drop path");

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "temp/documents (UNIX)", "appdata", "desktop" }));

		JLabel lblName = new JLabel("Name");

		txtName = new JTextField();
		txtName.setText("Bind");
		txtName.setColumns(10);

		JLabel lblSelectedFileWill = new JLabel("Selected file will not be encrypted");
		lblSelectedFileWill.setForeground(Color.RED);

		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help.help("A file to be executed when program is launched, using dropper");
			}
		});
		button_1.setIcon(new ImageIcon(PanelBuildBinder.class.getResource("/icons/help.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxBindFileTo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDropPath)
								.addComponent(lblName)
								.addComponent(lblPath))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtName)
										.addComponent(txtPath, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(button))
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSelectedFileWill))))
					.addGap(31))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(button)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxBindFileTo)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPath))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDropPath)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSelectedFileWill)
					.addContainerGap(77, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
