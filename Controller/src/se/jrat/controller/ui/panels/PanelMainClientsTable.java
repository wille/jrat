package se.jrat.controller.ui.panels;

import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import jrat.api.Client;
import jrat.api.ui.RATMenuItem;
import se.jrat.common.downloadable.Downloadable;
import se.jrat.common.utils.DataUnits;
import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Main;
import se.jrat.controller.Slave;
import se.jrat.controller.Status;
import se.jrat.controller.addons.ClientFormat;
import se.jrat.controller.listeners.CountryMenuItemListener;
import se.jrat.controller.net.URLParser;
import se.jrat.controller.packets.outgoing.Packet100RequestElevation;
import se.jrat.controller.packets.outgoing.Packet11Disconnect;
import se.jrat.controller.packets.outgoing.Packet14VisitURL;
import se.jrat.controller.packets.outgoing.Packet17DownloadExecute;
import se.jrat.controller.packets.outgoing.Packet18Update;
import se.jrat.controller.packets.outgoing.Packet36Uninstall;
import se.jrat.controller.packets.outgoing.Packet37RestartJavaProcess;
import se.jrat.controller.packets.outgoing.Packet38RunCommand;
import se.jrat.controller.packets.outgoing.Packet42ServerUploadFile;
import se.jrat.controller.packets.outgoing.Packet45Reconnect;
import se.jrat.controller.packets.outgoing.Packet98InjectJAR;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.settings.SettingsColumns;
import se.jrat.controller.threads.UploadThread;
import se.jrat.controller.ui.Columns;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.components.TableModel;
import se.jrat.controller.ui.dialogs.DialogFileType;
import se.jrat.controller.ui.frames.FrameControlPanel;
import se.jrat.controller.ui.frames.FrameNotes;
import se.jrat.controller.ui.frames.FrameRemoteChat;
import se.jrat.controller.ui.frames.FrameRemoteFiles;
import se.jrat.controller.ui.frames.FrameRemoteProcess;
import se.jrat.controller.ui.frames.FrameRemoteRegistry;
import se.jrat.controller.ui.frames.FrameRemoteScreen;
import se.jrat.controller.ui.frames.FrameRemoteShell;
import se.jrat.controller.ui.frames.FrameRename;
import se.jrat.controller.ui.frames.FrameSystemInfo;
import se.jrat.controller.ui.renderers.table.DefaultJTableCellRenderer;
import se.jrat.controller.utils.BasicIconUtils;
import se.jrat.controller.utils.NetUtils;
import se.jrat.controller.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

@SuppressWarnings("serial")
public class PanelMainClientsTable extends PanelMainClients {
	
	private final List<String> columns = new ArrayList<String>();

	private JTable table;
	private TableModel model;
	
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
	
	public void reloadTable() {
		model = new ClientsTableModel();
		table.setModel(model);
		
		for (AbstractSlave slave : Main.connections) {
			model.addRow(new Object[] { slave });
		}
	}
	
	@Override
	public void addSlave(AbstractSlave slave) {
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

			if (slave != null) {		
				String colname = table.getColumnName(column);
				
				label.setIcon(null);
				
				if (colname.equals(Columns.COUNTRY.getName()) && Main.instance.showThumbnails()) {
					label.setIcon(slave.getThumbnail());
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
	public JMenu getConfigPopupMenu() {
		JMenu menu = new JMenu();
		
		
		return menu;
	}

}
