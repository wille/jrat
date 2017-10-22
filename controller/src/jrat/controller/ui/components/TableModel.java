package jrat.controller.ui.components;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TableModel extends DefaultTableModel {
	
	public TableModel() {
		
	}

	public TableModel(Object[] columnNames) {
		super(null, columnNames);
	}
	
	public TableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	@Override
	public boolean isCellEditable(int i, int i1) {
		return false;
	}
}
