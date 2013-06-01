package com.redpois0n.ui.components;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Table extends JTable {
	
	public Table() {
		super();
		init();
	}

	public Table(DefaultTableModel model) {
		super(model);
		init();
	}
	
	public void init() {
		super.setGridColor(new Color(150, 150, 150));
	}

}
