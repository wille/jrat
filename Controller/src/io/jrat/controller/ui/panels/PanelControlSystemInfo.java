package io.jrat.controller.ui.panels;

import com.redpois0n.oslib.Icons;
import iconlib.IconUtils;
import io.jrat.common.utils.DataUnits;
import io.jrat.controller.Slave;
import io.jrat.controller.ui.components.TableModel;
import io.jrat.controller.ui.renderers.table.ComputerInfoTableRenderer;
import io.jrat.controller.utils.FlagUtils;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import jrat.api.ui.DefaultJTable;

@SuppressWarnings("serial")
public class PanelControlSystemInfo extends PanelControlParent {

	private JTable table;
	private TableModel model;

	public PanelControlSystemInfo(Slave sl) {
		super(sl);
		this.slave = sl;
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new ComputerInfoTableRenderer());
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.setRowHeight(25);

		scrollPane.setViewportView(table);
		add(scrollPane);

		reload();
	}

	public ComputerInfoTableRenderer getRenderer() {
		return (ComputerInfoTableRenderer) table.getDefaultRenderer(Object.class);
	}

	public void addRow(String icon, String key, String value) {
		addRow(IconUtils.getIcon(icon), key, value);
	}

	public void addRow(ImageIcon icon, String key, String value) {
		getRenderer().icons.put(key.toLowerCase(), icon);
		model.addRow(new Object[] { key, value });
	}

	public void reload() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		addRow("computer", "Remote address", slave.getIP());
		addRow("computer", "Local address", slave.getLocalIP());
		addRow("id", "Stub ID", slave.getID());
		addRow("memory", "RAM", DataUnits.getAsString(slave.getMemory()));
		addRow("cpu", "Available Cores", slave.getCores() + "");
		addRow("file", "Install Date/Last modified", slave.getInstallDate());
		addRow("username", "Username", slave.getUsername());
		addRow("computer", "Hostname", slave.getHostname());
		addRow(Icons.getIconString(slave.getOperatingSystem()), "Operating System", slave.getOperatingSystem().getDetailedString());
		addRow(FlagUtils.getFlag(slave), "Country", FlagUtils.getStringFromIso2(slave.getCountry()));
		addRow("folder", "Stub Location", slave.getServerPath());
		addRow("key", "Stub Version", slave.getVersion());
		addRow("java", "Java Version", slave.getJavaVersion());
		addRow("java", "Java Path", slave.getJavaPath());
	}
}
