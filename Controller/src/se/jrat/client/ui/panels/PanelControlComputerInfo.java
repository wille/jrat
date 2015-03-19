package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet27RefreshSystemInfo;
import se.jrat.client.ui.components.DefaultJTable;
import se.jrat.client.ui.renderers.table.ComputerInfoTableRenderer;
import se.jrat.client.utils.FlagUtils;
import se.jrat.client.utils.IconUtils;

import com.redpois0n.oslib.Icons;


@SuppressWarnings("serial")
public class PanelControlComputerInfo extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;

	public PanelControlComputerInfo(Slave sl) {
		super(sl);

		this.slave = sl;

		JScrollPane scrollPane = new JScrollPane();

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(IconUtils.getIcon("update"));
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet27RefreshSystemInfo());
				JOptionPane.showMessageDialog(null, "Reload this dialog to refresh data", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showSaveDialog(null);
				File file = c.getSelectedFile();
				if (file != null) {
					try {
						FileWriter writer = new FileWriter(file);
						for (int i = 0; i < model.getRowCount(); i++) {
							writer.write(model.getValueAt(i, 1).toString() + ": " + model.getValueAt(i, 2).toString() + "\n");
						}
						writer.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnSave.setIcon(IconUtils.getIcon("save"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReload)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSave)
					.addContainerGap(422, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReload)
						.addComponent(btnSave))
					.addContainerGap(15, Short.MAX_VALUE))
		);

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new ComputerInfoTableRenderer());
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

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
		addRow("memory", "RAM", slave.getMemory() + " MB");
		addRow("cpu", "Available Cores", slave.getCores() + "");
		addRow("file", "Install Date/Last modified", slave.getInstallDate());
		addRow("username", "Username", slave.getUsername());
		addRow("computer", "Computer Name", slave.getComputerName());
		addRow(Icons.getIconString(slave.getOperatingSystem()), "Operating System", slave.getOperatingSystem().getDetailedString());
		addRow(FlagUtils.getFlag(slave), "Country", slave.getCountry());
		addRow("folder", "Stub Location", slave.getServerPath());
		addRow("key", "Stub Version", slave.getVersion());
		addRow("java", "Java Version", slave.getJavaVersion());
		addRow("java", "Java Path", slave.getJavaPath());
	}
}
