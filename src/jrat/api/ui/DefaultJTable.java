package jrat.api.ui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableModel;

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
