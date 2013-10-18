package pro.jrat.ui.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PanelMainStats extends JPanel {

	private DefaultTableModel uniqueModel;
	private DefaultTableModel totalModel;

	private JTable uniqueTable;
	private JTable totalTable;
	
	private int width = 730;
	private int height = 430;
	
	public PanelMainStats(int width, int height) {
		this.width = width;
		this.height = height;
		init();
	}

	public PanelMainStats() {
		init();
	}
	
	public void init() {
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 150, height / 2 - 10);
		add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, height / 2 + 10, 150, height / 2 - 20);
		add(scrollPane_1);
		
		uniqueTable = new JTable(uniqueModel);
		uniqueTable.setBounds(205, 10, width - 10, height / 2 - 5);
		add(uniqueTable);
		
		totalTable = new JTable(totalModel);
		totalTable.setBounds(205, height / 2 + 5, width - 10, height / 2 - 5);
		add(totalTable);
	}
}
