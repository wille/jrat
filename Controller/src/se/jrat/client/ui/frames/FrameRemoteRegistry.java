package se.jrat.client.ui.frames;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.Slave;
import se.jrat.client.packets.outgoing.Packet79BrowseRegistry;
import se.jrat.client.packets.outgoing.Packet80CustomRegQuery;
import se.jrat.client.ui.components.pathtree.PathJTree;
import se.jrat.client.ui.components.pathtree.PathListener;
import se.jrat.client.ui.components.pathtree.PathTreeModel;
import se.jrat.client.ui.components.pathtree.PathTreeNode;
import se.jrat.client.ui.dialogs.DialogCustomRegQuery;
import se.jrat.client.ui.renderers.JComboBoxIconRenderer;
import se.jrat.client.ui.renderers.table.RegistryTableRenderer;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;
import javax.swing.JSplitPane;

@SuppressWarnings({ "serial", "rawtypes" })
public class FrameRemoteRegistry extends BaseFrame {

	private JPanel contentPane;
	private Slave slave;
	private DefaultTableModel model;

	public static final Map<Slave, FrameRemoteRegistry> instances = new HashMap<Slave, FrameRemoteRegistry>();
	public static final ImageIcon REGSZ_ICON = IconUtils.getIcon("registry_ab");
	public static final ImageIcon REG01_ICON = IconUtils.getIcon("registry_01");
	public static final ImageIcon FOLDER_ICON = IconUtils.getIcon("folder");
	public static final String[] ROOT_VALUES = new String[] { "HKEY_LOCAL_MACHINE", "HKEY_CURRENT_USER", "HKEY_CLASSES_ROOT", "HKEY_USERS", "HKEY_CURRENT_CONFIG" };

	private JTable table;
	private JComboBox comboBox;
	private JTextField txt;
	private JPopupMenu popupMenu;
	private JMenuItem mntmAdd;
	private JMenuItem mntmDelete;
	private JMenuItem mntmEditValue;
	private JMenu mnBrowse;
	private JMenuItem mntmStartup;
	private RegistryTableRenderer renderer = new RegistryTableRenderer();
	private PathJTree tree;

	public DefaultTableModel getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	public FrameRemoteRegistry(Slave sl) {
		super();
		this.slave = sl;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		instances.put(slave, this);
		setTitle("Registry - " + "[" + slave.formatUserString() + "] - " + slave.getIP());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteRegistry.class.getResource("/icons/registry.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String value = comboBox.getSelectedItem().toString();
				clear();
				execute(value);
				txt.setText(value);
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(ROOT_VALUES));
		comboBox.setRenderer(getRenderer(comboBox));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 380, Short.MAX_VALUE).addGap(3)).addComponent(splitPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)));

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (e.getClickCount() == 2 && row != -1) {
					if (table.getValueAt(row, 1) == null) {
						String value = table.getValueAt(row, 0).toString();
						clear();
						execute(value);
						txt.setText(value);
					}
				}
			}
		});

		table.setRowHeight(25);
		table.setModel(model = new DefaultTableModel(new Object[][] {}, new String[] { "Key", "Value", "Type" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		table.setDefaultRenderer(Object.class, renderer);

		popupMenu = new JPopupMenu();

		addPopup(table, popupMenu);

		mntmAdd = new JMenuItem("Add Value");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addValue();
			}
		});
		mntmAdd.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_plus.png")));
		popupMenu.add(mntmAdd);

		mntmDelete = new JMenuItem("Delete Value");
		mntmDelete.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_minus.png")));
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeValue();
			}
		});
		popupMenu.add(mntmDelete);
		popupMenu.addSeparator();

		mntmEditValue = new JMenuItem("Edit Value");
		mntmEditValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editValue();
			}
		});
		mntmEditValue.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_pencil.png")));
		popupMenu.add(mntmEditValue);
		popupMenu.addSeparator();

		mnBrowse = new JMenu("Browse");
		mnBrowse.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/bookmark_go.png")));
		popupMenu.add(mnBrowse);

		mntmStartup = new JMenuItem("Startup");
		mnBrowse.add(mntmStartup);
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		tree = new PathJTree();
		tree.setModel(new PathTreeModel(new PathTreeNode("root", null)));
		tree.setDelimiter("\\");
		tree.addPathListener(new PathListener() {
			@Override
			public void pathSelected(String path) {
				clear();
				execute(path);
				txt.setText(path);
			}
		});
		scrollPane_1.setViewportView(tree);

		for (String root : ROOT_VALUES) {
			getTreeModel().addRoot(new PathTreeNode(root, IconUtils.getIcon("folder_network")));
		}

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
		
		tree.setRootVisible(false);

		txt = new JTextField();
		toolBar.add(txt);
		txt.setEditable(false);
		txt.setColumns(10);

		JButton btnBack = new JButton("Back");
		toolBar.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = txt.getText().trim();
				if (str.lastIndexOf("\\") != -1) {
					str = str.substring(0, str.lastIndexOf("\\"));
					execute(str);
					txt.setText(str);
				}
			}
		});
		btnBack.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/left.png")));

		JButton btnReload = new JButton("Reload");
		toolBar.add(btnReload);
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reload();
			}
		});
		btnReload.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/update.png")));

		JButton btnClear = new JButton("Clear");
		toolBar.add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClear.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/clear.png")));

		JButton btnAdd = new JButton("Add");
		toolBar.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addValue();
			}
		});
		btnAdd.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_plus.png")));

		JButton btnRemove = new JButton("Remove");
		toolBar.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeValue();
			}
		});
		btnRemove.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_minus.png")));

		JButton btnEdit = new JButton("Edit");
		toolBar.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editValue();
			}
		});
		btnEdit.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_pencil.png")));

		JButton btnCustomCommand = new JButton("Custom");
		btnCustomCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogCustomRegQuery frame = new DialogCustomRegQuery(slave);
				frame.setVisible(true);
			}
		});
		btnCustomCommand.setIcon(new ImageIcon(FrameRemoteRegistry.class.getResource("/icons/key_arrow.png")));
		toolBar.add(btnCustomCommand);
		contentPane.setLayout(gl_contentPane);

		execute("hklm");
	}

	public void execute(String location) {
		slave.addToSendQueue(new Packet79BrowseRegistry(location));
	}

	public void exit() {
		instances.remove(slave);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void reload() {
		clear();
		String value;
		if (txt.getText().length() == 0) {
			value = comboBox.getSelectedItem().toString();
		} else {
			value = txt.getText();
		}
		execute(value);
	}

	public JComboBoxIconRenderer getRenderer(JComboBox box) {
		JComboBoxIconRenderer renderer = new JComboBoxIconRenderer();
		int num = box.getItemCount();
		ImageIcon folder = IconUtils.getIcon("folder_network");
		for (int i = 0; i < num; i++) {
			String item = box.getItemAt(i).toString();
			renderer.addIcon(item.toLowerCase(), folder);
		}
		return renderer;
	}

	public void addValue() {
		String name = Utils.showDialog("Add Value", "Input the name of the value to be created");
		if (name == null) {
			return;
		}
		String type = Utils.showDialog("Add Value", "Input type of value to be created, like:\n\nREG_SZ, REG_MULTI_SZ, REG_DWORD");
		if (type == null) {
			return;
		}
		String data = Utils.showDialog("Add Value", "Input the data to assign to the value being added");
		if (data == null) {
			return;
		}
		slave.addToSendQueue(new Packet80CustomRegQuery("REG ADD " + txt.getText() + "\\ /v " + name + " /t " + type + " /d " + data + " /f"));
		reload();
	}

	public void removeValue() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String val = table.getValueAt(row, 0).toString();
			slave.addToSendQueue(new Packet80CustomRegQuery("U REG DELETE " + txt.getText() + "\\" + " /v " + val + " /f"));
			reload();
		}
	}

	public void editValue() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String val = model.getValueAt(row, 0).toString();
			String type = model.getValueAt(row, 2).toString();
			String data = Utils.showDialog("Edit Value", "Input the data to replace the current data with");
			if (data == null) {
				return;
			}

			slave.addToSendQueue(new Packet80CustomRegQuery("U REG ADD " + txt.getText() + "\\ /v " + val + " /t " + type + " /d " + data + " /f"));

			reload();
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

	public RegistryTableRenderer getRenderer() {
		return renderer;
	}

	public PathTreeModel getTreeModel() {
		return (PathTreeModel) tree.getModel();
	}

	public PathJTree getTree() {
		return tree;
	}
}
