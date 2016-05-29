package io.jrat.controller.ui.panels;

import iconlib.IconUtils;
import io.jrat.common.utils.DataUnits;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Main;
import io.jrat.controller.OfflineSlave;
import io.jrat.controller.Slave;
import io.jrat.controller.Status;
import io.jrat.controller.packets.outgoing.Packet40Thumbnail;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.SettingsColumns;
import io.jrat.controller.ui.Columns;
import io.jrat.controller.ui.components.TableModel;
import io.jrat.controller.utils.BasicIconUtils;
import io.jrat.controller.utils.Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import jrat.api.ui.DefaultJTable;
import jrat.api.ui.DefaultJTableCellRenderer;

@SuppressWarnings("serial")
public class PanelMainClientsTable extends PanelMainClients {
	
	private final List<String> columns = new ArrayList<String>();

	private JTable table;
	private TableModel model;
	
	private boolean showThumbnails;
	
	public PanelMainClientsTable() {
		for (Columns s : Columns.values()) {
			if (SettingsColumns.getGlobal().isSelected(s.getName())) {
				columns.add(s.getName());
			}
		}
		
		model = new ClientsTableModel();
		table = new DefaultJTable(model);
		table.setDefaultRenderer(Object.class, new ClientsTableRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();

				if (row != -1 && table.getColumnName(column).equals(Columns.COUNTRY.getName())) {
					AbstractSlave sl = getSlave(row);
					sl.setSelected(!sl.isSelected());
					ListSelectionModel selectionModel = table.getSelectionModel();
					selectionModel.removeSelectionInterval(row, row);
				}
			}
		});
		
		resetRowHeight();
		
		Utils.addPopup(table, getPopupMenu());
		
		setViewportView(table);
	}
	
	@Override
	public String getViewName() {
		return "Table";
	}
	
	@Override
	public Icon getIcon() {
		return IconUtils.getIcon("tab-main");
	}
	
	public void reloadTable() {
		model = new ClientsTableModel();
		table.setModel(model);
		
		for (AbstractSlave slave : Main.connections) {
			model.addRow(new Object[] { slave });
		}
	}
	
	@Override
	public void addSlave(AbstractSlave slave) {
		for (int i = 0; i < model.getRowCount(); i++) {
			AbstractSlave as = getSlave(i);

			if (as instanceof OfflineSlave && as.equals(slave)) {
				model.removeRow(i);
				break;
			}
		}

		model.addRow(new Object[] { slave });
	}
	
	@Override
	public void removeSlave(AbstractSlave slave) {
		for (int column = 0; column < table.getColumnCount(); column++) {
			for (int row = 0; row < table.getRowCount(); row++) {
				Object obj = table.getValueAt(row, column);
				
				if (obj.equals(slave)) {
					model.removeRow(row);
					return;
				}
			}
		}
	}

	@Override
	public AbstractSlave getSlave(int row) {
		AbstractSlave slave = null;
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			Object obj = table.getValueAt(row, i);
			if (obj instanceof AbstractSlave) {
				slave = (AbstractSlave) obj;
				break;
			}
		}
		
		return slave;
	}
	
	@Override
	public List<AbstractSlave> getSelectedSlaves() {
		List<AbstractSlave> list = new ArrayList<AbstractSlave>();
		for (int i = 0; i < model.getRowCount(); i++) {
			AbstractSlave slave = getSlave(i);
			
			if (slave.isSelected() || table.isRowSelected(i)) {
				list.add(slave);
			}
		}
		return list;
	}
	
	@Override
	public AbstractSlave getSelectedSlave() {
		int row = table.getSelectedRow();
		return getSlave(row);
	}
	
	@Override
	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	public void setRowHeight(int i) {
		table.setRowHeight(i);
	}
	
	public void resetRowHeight() {
		int rowheight = 30;
		
		try {
			Object rowHeight = Settings.getGlobal().get("rowheight");
			if (rowHeight != null) {
				rowheight = Integer.parseInt(rowHeight.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		table.setRowHeight(rowheight);
	}
	
	public JTable getTable() {
		return table;
	}
	
	public List<String> getColumns() {
		return columns;
	}
	
	public class ClientsTableRenderer extends DefaultJTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			AbstractSlave slave = getSlave(row);

			boolean offline = slave instanceof OfflineSlave;

			if (slave != null) {		
				String colname = table.getColumnName(column);
				
				label.setIcon(null);
				
				if (!offline && colname.equals(Columns.COUNTRY.getName()) && showThumbnails) {
					if (slave.getThumbnail() == null) {
						label.setText("Loading...");
					} else {
						label.setIcon(slave.getThumbnail());
					}
				} else if (colname.equals(Columns.COUNTRY.getName())) {
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
					
					JCheckBox b = new JCheckBox(country, slave.isSelected());

					b.setToolTipText(row + "");
					b.setBackground(label.getBackground());

					URL url = Main.class.getResource(path);

					if (url == null) {
						url = Main.class.getResource("/flags/unknown.png");
					}

					b.setText("<html><table cellpadding=0><tr><td><img src=\"" + url.toString() + "\"/></td><td width=3><td><font color=\"#" + color + "\">" + country + "</font></td></tr></table></html>");

					return b;
				} else if (colname.equals(Columns.ID.getName())) {
					String id;
					
					if (slave.getRenamedID() != null) {
						id = slave.getRenamedID();
					} else {
						id = slave.getID();
					}
					
					label.setText(id);
				} else if (colname.equals(Columns.STATUS.getName())) {
					label.setText(Status.getStatusFromID(slave.getStatus()));
				} else if (colname.equals(Columns.IP.getName())) {
					label.setText(slave.getIP());
				} else if (colname.equals(Columns.PING.getName())) {
					label.setIcon(BasicIconUtils.getPingIcon(slave));
					label.setText(slave.getPing() + " ms");
				} else if (colname.equals(Columns.USER_HOST.getName())) {
					label.setText(slave.getDisplayName());
				} else if (colname.equals(Columns.OPERATINGSYSTEM.getName())) {
					label.setIcon(BasicIconUtils.getOSIcon(slave));
					label.setText(slave.getOperatingSystem().getDisplayString());
				} else if (colname.equals(Columns.RAM.getName())) {
					label.setText(DataUnits.getAsString(slave.getMemory()));
				} else if (colname.equals(Columns.LOCAL_ADDRESS.getName())) {
					label.setText(slave.getLocalIP());
				} else if (colname.equals(Columns.VERSION.getName())) {
					label.setText(slave.getVersion());
				} else if (colname.equals(Columns.AVAILABLE_CORES.getName())) {
					if (slave instanceof Slave) {
						label.setText(((Slave) slave).getCores() + "");
					} else {
						label.setText("?");
					}
				} else if (colname.equals(Columns.DESKTOP_ENVIRONMENT.getName())) {
					label.setText(slave.getOperatingSystem().getDesktopEnvironment().getDisplayString());
				} else if (colname.equals(Columns.CPU.getName())) {
					if (slave instanceof Slave) {
						label.setText(((Slave) slave).getCPU());
					} else {
						label.setText("?");
					}
				} else if (colname.equals(Columns.HEADLESS.getName())) {
					label.setText(slave.isHeadless() ? "Yes" : "No");
					label.setForeground(slave.isHeadless() ? Color.red : Color.black);
				} else if (colname.equals(Columns.NETWORK_USAGE.getName())) {
					label.setText(DataUnits.getAsString(slave.getCurrentIn()) + "/s down, " + DataUnits.getAsString(slave.getCurrentOut()) + "/s up");
				}
			}

			return label;
		}
	}

	public class ClientsTableModel extends TableModel {

		public ClientsTableModel() {
			for (String s : columns) {
				super.addColumn(s);
			}
		}

		@Override
		public boolean isCellEditable(int i, int i1) {
			return false;
		}
	}

	@Override
	public List<JMenuItem> getConfigMenu() {
		List<JMenuItem> menu = new ArrayList<JMenuItem>();
		
		JMenu mnColumns = new JMenu("Columns");
		mnColumns.setIcon(IconUtils.getIcon("application-table"));
		menu.add(mnColumns);
		
		for (Columns s : Columns.values()) {
			JCheckBoxMenuItem jcb = new JCheckBoxMenuItem("Display " + s.getName());
			jcb.setSelected(SettingsColumns.getGlobal().isSelected(s.getName()));
			jcb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JCheckBoxMenuItem jcb = (JCheckBoxMenuItem) e.getSource();
					
					String text = jcb.getText().substring(8, jcb.getText().length());
					
					if (jcb.isSelected()) {
						getColumns().add(text);
					} else {
						getColumns().remove(text);
					}
					
					SettingsColumns.getGlobal().setColumn(text, jcb.isSelected());
					
					reloadTable();
				}	
			});
			
			mnColumns.add(jcb);
		}

		JCheckBoxMenuItem mntmShowshowThumbnails = new JCheckBoxMenuItem("Show Thumbnails");
		menu.add(mntmShowshowThumbnails);
		mntmShowshowThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showThumbnails = !showThumbnails;

				if (showThumbnails) {
					for (int i = 0; i < Main.connections.size(); i++) {
						AbstractSlave sl = Main.connections.get(i);
						if (sl.getThumbnail() == null) {
							if (sl instanceof Slave) {
								((Slave) sl).addToSendQueue(new Packet40Thumbnail());
							}
						}
					}
					setRowHeight(100);
				} else {
					resetRowHeight();
				}
			}
		});
		
		JMenu mnTableResizeBehaviour = new JMenu("Table resize behaviour");
		menu.add(mnTableResizeBehaviour);
		mnTableResizeBehaviour.setIcon(IconUtils.getIcon("application-table"));

		JMenuItem mntmResizeOff = new JMenuItem("Resize off");
		mntmResizeOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			}
		});
		mntmResizeOff.setIcon(IconUtils.getIcon("application-resize"));
		mnTableResizeBehaviour.add(mntmResizeOff);

		JMenuItem mntmFit = new JMenuItem("Fit");
		mntmFit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			}
		});
		mntmFit.setIcon(IconUtils.getIcon("application-resize"));
		mnTableResizeBehaviour.add(mntmFit);

		JMenuItem mntmRowHeight = new JMenuItem("Row height");
		menu.add(mntmRowHeight);
		mntmRowHeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = Utils.showDialog("Input", "Input new row height. Default: 30");
				if (str == null) {
					return;
				}

				int h;

				try {
					h = Integer.parseInt(str.trim());
				} catch (Exception ex) {
					return;
				}
				setRowHeight(h);
				Settings.getGlobal().setVal("rowheight", h);
			}
		});
		mntmRowHeight.setIcon(IconUtils.getIcon("application-dock"));
		
		return menu;
	}

}
