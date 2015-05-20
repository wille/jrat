package se.jrat.controller.ui.components;

import java.awt.Dimension;

import javax.swing.JTable;

import se.jrat.controller.ui.renderers.table.DefaultJTableCellRenderer;

@SuppressWarnings("serial")
public class DefaultJTable extends JTable {
	
	public DefaultJTable() {
		this(null);
	}
	
	public DefaultJTable(TableModel model) {
		super(model);
		
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		setFillsViewportHeight(true);
		setDefaultRenderer(Object.class, new DefaultJTableCellRenderer());
	}

}
