package pro.jrat.ui.panels;

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

import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet27RefreshSystemInfo;
import pro.jrat.packets.outgoing.Packet83WinSysInfo;
import pro.jrat.ui.frames.FrameRawSystemInfo;
import pro.jrat.ui.renderers.table.ComputerInfoTableRenderer;
import pro.jrat.utils.FlagUtils;
import pro.jrat.utils.IconUtils;



@SuppressWarnings("serial")
public class PanelControlComputerInfo extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;

	public PanelControlComputerInfo(Slave sl) {
		super(sl);

		this.slave = sl;

		JScrollPane scrollPane = new JScrollPane();

		JButton btnReload = new JButton("Reload");
		btnReload.setIcon(new ImageIcon(PanelControlComputerInfo.class.getResource("/icons/update.png")));
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
		btnSave.setIcon(new ImageIcon(PanelControlComputerInfo.class.getResource("/icons/save.png")));
		
		JButton btnGetSysteminfoexe = new JButton("Get systeminfo.exe");
		btnGetSysteminfoexe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameRawSystemInfo frame = new FrameRawSystemInfo(slave);
				frame.setVisible(true);
				slave.addToSendQueue(new Packet83WinSysInfo());	
			}
		});
		btnGetSysteminfoexe.setIcon(new ImageIcon(PanelControlComputerInfo.class.getResource("/icons/down_arrow.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReload)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnGetSysteminfoexe)
					.addContainerGap(327, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReload)
						.addComponent(btnSave)
						.addComponent(btnGetSysteminfoexe))
					.addContainerGap(15, Short.MAX_VALUE))
		);

		table = new JTable();
		table.setDefaultRenderer(Object.class, new ComputerInfoTableRenderer());
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Key", "Value" }));
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

		refreshSystemInfo();
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

	public void refreshSystemInfo() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		addRow("connection_host", "Connection Host", slave.getIP());
		addRow("localhost", "LAN IP", slave.getLocalIP());
		addRow("id", "Server ID", slave.getServerID());
		addRow("memory", "RAM", slave.getRam() + " MB");
		addRow("adapters", "Available Processors", slave.getProcessors() + "");
		addRow("last_modified", "Install Date/Last modified", slave.getInstallDate());
		addRow("host", "Ping", slave.getPing() + "");
		addRow("username", "Username", slave.getUsername());
		addRow("computer_name", "Computer Name", slave.getComputerName());
		addRow("os", "OS Name", slave.getOperatingSystem());
		addRow(FlagUtils.getFlag(slave), "Country", slave.getCountry());
		addRow("folder", "Server Location", slave.getServerPath());
		addRow("key", "Server Version", slave.getVersion());
		addRow("javascript", "Java Version", slave.getJavaVersion());
		addRow("javascript", "Java Path", slave.getJavaPath());
	}
}
