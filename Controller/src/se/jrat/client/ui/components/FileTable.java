package se.jrat.client.ui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import se.jrat.client.ui.renderers.JComboBoxIconRenderer;
import se.jrat.client.utils.IconUtils;

@SuppressWarnings("serial")
public abstract class FileTable extends JPanel {

	protected JToolBar toolBar;
	protected JTable table;
	
	protected JComboBox<String> driveComboBox;
	protected DefaultComboBoxModel<String> comboModel;
	protected JComboBoxIconRenderer renderer;
	
	public FileTable() {
		setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);;
		add(toolBar, BorderLayout.NORTH);
		
		JScrollPane sp = new JScrollPane();
		table = new JTable();
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
		
		driveComboBox = new JComboBox<String>();
		driveComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		comboModel = (DefaultComboBoxModel<String>) driveComboBox.getModel();
		renderer = new JComboBoxIconRenderer();
		
		toolBar.add(driveComboBox);
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
	
	public abstract void onItemClick();
}
