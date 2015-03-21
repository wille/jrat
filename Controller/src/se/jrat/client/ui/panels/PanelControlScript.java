package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.AbstractOutgoingPacket;
import se.jrat.client.packets.outgoing.Packet35Script;
import se.jrat.client.utils.IconUtils;
import se.jrat.common.script.Script;


@SuppressWarnings("serial")
public class PanelControlScript extends PanelControlParent {

	private Script type;
	private JTextPane textPane;

	public PanelControlScript(Slave slave, Script type) {
		super(slave);
		this.type = type;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnSendScript = new JButton("Send script");
		btnSendScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				send();
			}
		});

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
		btnSave.setIcon(IconUtils.getIcon("save"));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPane.setText("");
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGroup(groupLayout.createSequentialGroup().addContainerGap(315, Short.MAX_VALUE).addComponent(btnClear).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSendScript).addGap(31)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnSendScript).addComponent(btnSave).addComponent(btnClear)).addContainerGap(15, Short.MAX_VALUE)));

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		setLayout(groupLayout);
	}
	
	public void send() {
		AbstractOutgoingPacket packet = new Packet35Script(type, textPane.getText());
		
		slave.addToSendQueue(packet);
	}
}
