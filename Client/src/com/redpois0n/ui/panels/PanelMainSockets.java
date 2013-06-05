package com.redpois0n.ui.panels;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.redpois0n.ErrorDialog;
import com.redpois0n.net.PortListener;
import com.redpois0n.ui.frames.FrameAddSocket;
import com.redpois0n.ui.renderers.table.SocketsTableRenderer;

@SuppressWarnings("serial")
public class PanelMainSockets extends JPanel {

	private JTable table;
	private DefaultTableModel model;
	public static PanelMainSockets instance;

	public DefaultTableModel getModel() {
		return model;
	}

	public PanelMainSockets() {
		instance = this;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);

		table = new JTable();
		table.setDefaultRenderer(Object.class, new SocketsTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Port", "Timeout", "Pass", "Encryption key" }) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(211);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(101);
		table.getColumnModel().getColumn(3).setPreferredWidth(110);
		table.getColumnModel().getColumn(4).setPreferredWidth(128);

		table.getTableHeader().setReorderingAllowed(false);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		addPopup(table, popupMenu);
		
		JMenuItem mntmAddSocket = new JMenuItem("Add Socket");
		mntmAddSocket.setIcon(new ImageIcon(PanelMainSockets.class.getResource("/icons/socket_add.png")));
		mntmAddSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAddSocket frame = new FrameAddSocket();
				frame.setVisible(true);
			}
		});
		popupMenu.add(mntmAddSocket);
		
		popupMenu.addSeparator();
		
		JMenuItem mntmCloseSocket = new JMenuItem("Close Socket");
		mntmCloseSocket.setIcon(new ImageIcon(PanelMainSockets.class.getResource("/icons/socket_remove.png")));
		mntmCloseSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						String name = model.getValueAt(row, 0).toString();
						String port = model.getValueAt(row, 1).toString();
						String timeout = model.getValueAt(row, 2).toString();
						String pass = model.getValueAt(row, 3).toString().split(", ")[0];
						String key = model.getValueAt(row, 4).toString();

						model.removeRow(row);

						PortListener con = PortListener.getConnection(name, Integer.parseInt(port), Integer.parseInt(timeout), key, pass);
						con.getServer().close();
						PortListener.servers.remove(con);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		popupMenu.add(mntmCloseSocket);
		
		JMenuItem mntmCloseAllSockets = new JMenuItem("Close all Sockets");
		mntmCloseAllSockets.setIcon(new ImageIcon(PanelMainSockets.class.getResource("/icons/socket_remove.png")));
		mntmCloseAllSockets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				for (int i = 0; i < PortListener.servers.size(); i++) {
					PortListener con = PortListener.servers.get(i);
					try {
						con.getServer().close();
					} catch (Exception ex) {
						ex.printStackTrace();
						ErrorDialog.create(ex);
					}
				}
				PortListener.servers.clear();
			}
		});
		popupMenu.add(mntmCloseAllSockets);
		
		popupMenu.addSeparator();
		
		JMenu mnCopy = new JMenu("Copy");
		mnCopy.setIcon(new ImageIcon(PanelMainSockets.class.getResource("/icons/clipboard.png")));
		popupMenu.add(mnCopy);
		
		JMenuItem mntmPassword = new JMenuItem("Password");
		mntmPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copy(table.getSelectedRow(), 3);
			}
		});
		mntmPassword.setIcon(new ImageIcon(PanelMainSockets.class.getResource("/icons/information-button.png")));
		mnCopy.add(mntmPassword);
		
		JMenuItem mntmEncryptionKey = new JMenuItem("Encryption Key");
		mntmEncryptionKey.setIcon(new ImageIcon(PanelMainSockets.class.getResource("/icons/information-button.png")));
		mntmEncryptionKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copy(table.getSelectedRow(), 4);
			}
		});
		mnCopy.add(mntmEncryptionKey);

		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}
	
	public void copy(int row, int col) {
		if (row != -1) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getModel().getValueAt(row, col).toString()), null);
		}	
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
