package jrat.controller.ui;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

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
		getTableHeader().setReorderingAllowed(false);
	}

}
