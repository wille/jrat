package pro.jrat.ui.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import pro.jrat.ErrorDialog;
import pro.jrat.extensions.ExtensionInstaller;
import pro.jrat.extensions.OnlinePlugin;
import pro.jrat.listeners.ExtensionInstallerListener;

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
	private JProgressBar progressBar;
	private JLabel lblStatus;

	public FrameOnlinePlugins() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameOnlinePlugins.class.getResource("/icons/application_icon_large.png")));
		setTitle("Browse Online Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 725, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	if (!event.getValueIsAdjusting() && table.getSelectedColumn() == 5) {
	        		install((String) table.getValueAt(table.getSelectedRow(), 0));
	        	}
	        }
	    });
		
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
						JButton btn = new JButton(plugin.isInstalled() ? "Uninstall" : "Install");
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
		
		lblStatus = new JLabel("Status");
		lblStatus.setVisible(false);
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStatus)
					.addGap(171))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)))
		);
		contentPane.setLayout(gl_contentPane);

		reload();
	}

	public void install(String pluginDisplayName) {
		install(getPlugin(pluginDisplayName));
	}
	
	public void install(final OnlinePlugin plugin) {
		String what = plugin.isInstalled() ? "uninstall" : "install";
		if (JOptionPane.showConfirmDialog(null, "Are you sure that you want to " + what + " " + plugin.getDisplayName() + "?", "Plugin", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		
		final ExtensionInstaller installer = new ExtensionInstaller(plugin, new ExtensionInstallerListener() {
			@Override
			public void status(Color color, String message, int status) {
				lblStatus.setForeground(color);
				lblStatus.setText(message);
				progressBar.setValue(status);
			}
		});
		
		progressBar.setVisible(true);
		lblStatus.setVisible(true);
		
		new Thread(new Runnable() {
			public void run() {
				try {
					installer.toggle();
					
					progressBar.setVisible(false);
					lblStatus.setVisible(false);
					
					JOptionPane.showMessageDialog(null, "Successfully installed and enabled " + plugin.getDisplayName(), "Plugin", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					
					progressBar.setVisible(false);
					lblStatus.setVisible(false);
					
					JOptionPane.showMessageDialog(null, "Failed to install " + plugin.getDisplayName() + ", " + ex.getClass().getSimpleName() + ": " + ex.getMessage(), "Plugin", JOptionPane.ERROR_MESSAGE);
				}
				table.repaint();				
			}
		}).start();	
	}

	public void reload() {
		try {
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			plugins = OnlinePlugin.getAvailablePlugins();

			for (OnlinePlugin plugin : plugins) {
				model.addRow(new Object[] { plugin.getDisplayName(), plugin.getAuthor(), plugin.getDescription(), plugin.getVersion(), plugin.getBuiltFor() });
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

	public OnlinePlugin getPlugin(String name) {
		for (OnlinePlugin plugin : plugins) {
			if (plugin.getName().trim().equals(name.trim().replace(" ", ""))) {
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
