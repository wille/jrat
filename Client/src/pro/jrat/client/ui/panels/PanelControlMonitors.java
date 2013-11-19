package pro.jrat.client.ui.panels;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import pro.jrat.client.Monitor;
import pro.jrat.client.Slave;
import pro.jrat.client.ui.renderers.table.MonitorsTableRenderer;

@SuppressWarnings("serial")
public class PanelControlMonitors extends PanelControlParent {

	private JTable table;

	public DefaultTableModel getModel() {
		return (DefaultTableModel) table.getModel();
	}

	public PanelControlMonitors(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE));

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Monitor", "Index" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(504);
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new MonitorsTableRenderer());
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

		reload();
	}

	public void reload() {
		for (Monitor monitor : slave.getMonitors()) {
			getModel().addRow(new Object[] { monitor.getName(), monitor.getIndex() });
		}
	}
}
