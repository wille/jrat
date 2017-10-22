package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.controller.AbstractSlave;
import jrat.controller.LogAction;
import jrat.controller.LogEntry;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.table.LogTableRenderer;
import jrat.controller.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import jrat.api.ui.DefaultJTable;


@SuppressWarnings("serial")
public class PanelMainLog extends JPanel {

	private JTable table;
	private TableModel model;

	public TableModel getModel() {
		return model;
	}

	public JTable getTable() {
		return table;
	}
	
	public PanelMainLog() {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new LogTableRenderer());
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Action", "Connection", "Info", "Time" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(126);
		table.getColumnModel().getColumn(1).setPreferredWidth(153);
		table.getColumnModel().getColumn(2).setPreferredWidth(205);
		table.getColumnModel().getColumn(3).setPreferredWidth(118);
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false);

		JPopupMenu popupMenu = new JPopupMenu();
		Utils.addPopup(scrollPane, popupMenu);
		Utils.addPopup(table, popupMenu);

		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();

				for (int row : rows) {
					model.removeRow(row);
				}
			}
		});
		mntmRemove.setIcon(IconUtils.getIcon("application-minus"));
		popupMenu.add(mntmRemove);

		JMenuItem mntmRemoveAll = new JMenuItem("Remove all");
		mntmRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});
		mntmRemoveAll.setIcon(IconUtils.getIcon("application-minus"));
		popupMenu.add(mntmRemoveAll);

		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}

	public void addEntry(LogAction action, AbstractSlave slave, String info) {
		LogEntry logEntry = new LogEntry(action, slave, info);
		getModel().insertRow(0, logEntry.getDisplayData());
	}
}
