package com.redpois0n.ui.panels;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.redpois0n.Help;

@SuppressWarnings("serial")
public class PanelBuildUSB extends JPanel {
	
	private JTextField txtExclude;
	private JTextField txtName;
	private JCheckBox chckbxCopyToUsb;
	
	public boolean copyToUsb() {
		return chckbxCopyToUsb.isSelected();
	}
	
	public String getExclude() {
		return txtExclude.getText().trim();
	}
	
	public String getNameOfFile() {
		return txtName.getText().trim();
	}

	public PanelBuildUSB() {
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Copy to USB"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
		);
		
		chckbxCopyToUsb = new JCheckBox("Copy to USB drives");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PanelBuildUSB.class.getResource("/icons/usb-flash-drive--plus.png")));
		
		JLabel lblExcludeDrives = new JLabel("Exclude drives:");
		
		txtExclude = new JTextField();
		txtExclude.setText("C:\\, D:\\");
		txtExclude.setColumns(10);
		
		JLabel lblWillCopyItself = new JLabel("Will copy itself to all drives on the computer");
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Select what drives that should not be affected");
			}
		});
		button.setIcon(new ImageIcon(PanelBuildUSB.class.getResource("/icons/help.png")));
		
		JLabel lblNameInDrive = new JLabel("Name in drive:");
		
		txtName = new JTextField();
		txtName.setText("Server");
		txtName.setColumns(10);
		
		JLabel lblMakeSureYou = new JLabel("Make sure you have write permission on the usb drives");
		lblMakeSureYou.setForeground(Color.RED);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMakeSureYou)
						.addComponent(lblWillCopyItself)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxCopyToUsb))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblExcludeDrives)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtExclude, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNameInDrive)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtName, 203, 203, 203)))
					.addContainerGap(117, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chckbxCopyToUsb, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblExcludeDrives)
						.addComponent(txtExclude, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblWillCopyItself)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNameInDrive)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblMakeSureYou)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}
}
