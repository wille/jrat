package se.jrat.client.ui.components;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.ui.renderers.table.DefaultJTableCellRenderer;

@SuppressWarnings("serial")
public class DefaultJTable extends JTable {
	
	public DefaultJTable() {
		this(null);
	}
	
	public DefaultJTable(DefaultTableModel model) {
		super(model);
		
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		setFillsViewportHeight(true);
		setDefaultRenderer(Object.class, new DefaultJTableCellRenderer());
	}

}
