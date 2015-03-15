package se.jrat.client.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.Status;
import se.jrat.client.utils.IconUtils;

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
		table.setDefaultRenderer(Object.class, new ClientsTableRenderer());
		
		setViewportView(table);
	}
	
	public void add(AbstractSlave slave) {
		model.addRow(new Object[] { slave });
	}

	public class ClientsTableRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if (isSelected) {
				label.setBackground(TABLE_SELECTED);
			} else if (row % 2 == 0) {
				label.setBackground(TABLE_GRAY);
			} else {
				label.setBackground(Color.white);
			}
			
			Object obj = table.getValueAt(row, 0);

			if (obj instanceof AbstractSlave) {
				AbstractSlave slave = (AbstractSlave) obj;
				
				String colname = table.getColumnName(column);
				
				label.setIcon(null);
				
				if (colname.equals(COLUMN_COUNTRY)) {
					String path;

					String color = Integer.toHexString(label.getForeground().getRGB() & 0xffffff) + "";
					if (color.length() < 6) {
						color = "000000".substring(0, 6 - color.length()) + color;
					}
					
					String country = slave.getCountry();
								
					if (country != null) {
						path = "/flags/" + country.toLowerCase() + ".png";
					} else {
						path = "/flags/unknown.png";
					}
					
					JCheckBox b = new JCheckBox(value.toString(), slave.isSelected());

					b.setToolTipText(row + "");
					b.setBackground(label.getBackground());

					URL url = Main.class.getResource(path);

					if (url == null) {
						url = Main.class.getResource("/flags/unknown.png");
					}

					b.setText("<html><table cellpadding=0><tr><td><img src=\"" + url.toString() + "\"/></td><td width=3><td><font color=\"#" + color + "\">" + value + "</font></td></tr></table></html>");

					return b;
				} else if (colname.equals(COLUMN_ID)) {
					String id;
					
					if (slave.getRenamedID() != null) {
						id = slave.getRenamedID();
					} else {
						id = slave.getID();
					}
					
					label.setText(id);
				} else if (colname.equals(COLUMN_STATUS)) {
					label.setText(Status.getStatusFromID(slave.getStatus()));
				} else if (colname.equals(COLUMN_IP)) {
					label.setText(slave.getIP());
				} else if (colname.equals(COLUMN_PING)) {
					label.setIcon(IconUtils.getPingIcon(slave));
					label.setText(slave.getPing() + " ms");
				} else if (colname.equals(COLUMN_USER_HOST)) {
					label.setText(slave.formatUserString());
				} else if (colname.equals(COLUMN_OPERATINGSYSTEM)) {
					label.setIcon(IconUtils.getOSIcon(slave));
					label.setText(slave.getOperatingSystem().getDisplayString());
				} else if (colname.equals(COLUMN_RAM)) {
					label.setText(slave.getMemory() + " MB");
				} else if (colname.equals(COLUMN_LOCAL_ADDRESS)) {
					label.setText(slave.getLocalIP());
				} else if (colname.equals(COLUMN_VERSION)) {
					label.setText(slave.getVersion());
				}
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
