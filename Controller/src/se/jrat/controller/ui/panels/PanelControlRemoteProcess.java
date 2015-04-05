package se.jrat.controller.ui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet19ListProcesses;
import se.jrat.controller.packets.outgoing.Packet20KillProcess;
import se.jrat.controller.packets.outgoing.Packet38RunCommand;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.ui.renderers.table.ProcessTableRenderer;
import se.jrat.controller.utils.IconUtils;
import se.jrat.controller.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

@SuppressWarnings("serial")
public class PanelControlRemoteProcess extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;
	private JToolBar toolBar;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelControlRemoteProcess(Slave slave) {
		super(slave);
		final Slave sl = slave;

		setLayout(new BorderLayout(0, 0));

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.SOUTH);

		JButton btnClearList = new JButton("Clear list");
		toolBar.add(btnClearList);
		btnClearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});
		btnClearList.setIcon(IconUtils.getIcon("clear"));

		JButton btnCreateProcess = new JButton("Create process");
		toolBar.add(btnCreateProcess);
		btnCreateProcess.setIcon(IconUtils.getIcon("process-go"));

		JButton btnKillSelected = new JButton("Kill selected");
		toolBar.add(btnKillSelected);
		btnKillSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.getValueAt(table.getSelectedRow(), 0) != null) {
					String process;
					if (sl.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
						process = model.getValueAt(table.getSelectedRow(), 0).toString();
					} else {
						process = model.getValueAt(table.getSelectedRow(), 1).toString();
					}

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					sl.addToSendQueue(new Packet20KillProcess(process));

					sl.addToSendQueue(new Packet19ListProcesses());
				}
			}
		});
		btnKillSelected.setIcon(IconUtils.getIcon("delete"));

		JButton btnRefresh = new JButton("Refresh");
		toolBar.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				sl.addToSendQueue(new Packet19ListProcesses());
			}
		});
		btnRefresh.setIcon(IconUtils.getIcon("update"));
		btnCreateProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String proc = Utils.showDialog("Create process", "Input name of process");
				if (proc == null) {
					return;
				}
				proc = proc.trim();
				sl.addToSendQueue(new Packet38RunCommand(proc));
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				sl.addToSendQueue(new Packet19ListProcesses());
			}
		});

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new ProcessTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Process ID", "Type/User", "Memory usage" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(287);
		table.getColumnModel().getColumn(2).setPreferredWidth(108);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.setRowHeight(25);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		add(scrollPane, BorderLayout.CENTER);
	}
}
