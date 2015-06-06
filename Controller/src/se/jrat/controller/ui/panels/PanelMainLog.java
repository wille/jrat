package se.jrat.controller.ui.panels;

import iconlib.IconUtils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.LogAction;
import se.jrat.controller.LogEntry;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.components.TableModel;
import se.jrat.controller.ui.renderers.table.LogTableRenderer;


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
		addPopup(scrollPane, popupMenu);
		addPopup(table, popupMenu);

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

	public void addEntry(LogAction action, AbstractSlave abstractSlave, String info) {
		LogEntry logEntry = new LogEntry(action, abstractSlave, info);
		getModel().insertRow(0, logEntry.getDisplayData());
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
