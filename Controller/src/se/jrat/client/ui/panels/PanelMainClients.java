package se.jrat.client.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.AbstractSlave;

@SuppressWarnings("serial")
public class PanelMainClients extends JScrollPane {

	public static final Color TABLE_SELECTED = new Color(51, 153, 255);
	public static final Color TABLE_GRAY = new Color(240, 240, 240);
	
	public static final String COLUMN_COUNTRY = "Country";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_STATUS = "Status";
	public static final String COLUMN_IP = "IP/Port";
	public static final String COLUMN_PING = "Ping";
	public static final String COLUMN_USER_HOST = "User@Host";
	public static final String COLUMN_OPERATINGSYSTEM = "Operating System";
	public static final String COLUMN_RAM = "RAM";
	public static final String COLUMN_LOCAL_ADDRESS = "Local Address";
	public static final String COLUMN_VERSION = "Version";

	private final List<String> columns = new ArrayList<String>();

	private JTable table;
	private DefaultTableModel model;
	
	public PanelMainClients() {
		columns.add(COLUMN_COUNTRY);
		columns.add(COLUMN_ID);
		columns.add(COLUMN_STATUS);
		columns.add(COLUMN_IP);
		columns.add(COLUMN_PING);
		columns.add(COLUMN_USER_HOST);
		columns.add(COLUMN_OPERATINGSYSTEM);
		columns.add(COLUMN_RAM);
		columns.add(COLUMN_LOCAL_ADDRESS);
		columns.add(COLUMN_VERSION);
		
		model = new ClientsTableModel();
		table = new JTable(model);
		
		setViewportView(table);
	}

	public class ClientsTableRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			Object obj = table.getValueAt(row, 0);

			if (obj instanceof AbstractSlave) {
				String colname = table.getColumnName(column);
				
				if (colname.equals(COLUMN_COUNTRY)) {
					
				} else if (colname.equals(COLUMN_ID)) {
					
				} else if (colname.equals(COLUMN_STATUS)) {
					
				} else if (colname.equals(COLUMN_IP)) {
					
				} else if (colname.equals(COLUMN_PING)) {
					
				} else if (colname.equals(COLUMN_USER_HOST)) {
					
				} else if (colname.equals(COLUMN_OPERATINGSYSTEM)) {
					
				} else if (colname.equals(COLUMN_RAM)) {
					
				} else if (colname.equals(COLUMN_LOCAL_ADDRESS)) {
					
				} else if (colname.equals(COLUMN_VERSION)) {
					
				}
			}

			if (isSelected) {
				label.setBackground(TABLE_SELECTED);
			} else if (row % 2 == 0) {
				label.setBackground(TABLE_GRAY);
			} else {
				label.setBackground(Color.white);
			}

			return label;
		}
	}

	public class ClientsTableModel extends DefaultTableModel {

		public ClientsTableModel() {
			reload();
		}

		public void reload() {
			for (String s : columns) {
				super.addColumn(s);
			}
		}

		@Override
		public boolean isCellEditable(int i, int i1) {
			return false;
		}

	}

}
