package jrat.controller.ui.panels;

import jrat.controller.Drive;
import jrat.controller.Slave;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.table.DrivesTableRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;


@SuppressWarnings("serial")
public class PanelControlDrives extends PanelControlParent {

	private JTable table;

	public JTable getTable() {
		return table;
	}

	public TableModel getModel() {
		return (TableModel) table.getModel();
	}

	public PanelControlDrives(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE));

		table = new DefaultJTable();
		table.setModel(new TableModel(new Object[][] {}, new String[] { "Drive", "Total Space", "Free Space", "Usable Space" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.setDefaultRenderer(Object.class, new DrivesTableRenderer());
		table.getColumnModel().getColumn(0).setPreferredWidth(123);
		table.getColumnModel().getColumn(1).setPreferredWidth(177);
		table.getColumnModel().getColumn(2).setPreferredWidth(157);
		table.getColumnModel().getColumn(3).setPreferredWidth(139);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

		load();
	}

	public void load() {
		while (getModel().getRowCount() > 0) {
			getModel().removeRow(0);
		}
		for (Drive drive : slave.getDrives()) {
			getModel().addRow(new Object[] { drive.getName(), drive.getTotalSpace() + " GB", drive.getFreeSpace() + " GB", drive.getUsableSpace() + " GB" });
		}
	}
}
