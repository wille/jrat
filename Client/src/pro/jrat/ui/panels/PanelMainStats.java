package pro.jrat.ui.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pro.jrat.settings.Statistics;
import pro.jrat.settings.Statistics.StatEntry;
import pro.jrat.threads.ThreadCountryGraph;

import com.redpois0n.graphs.country.Country;
import com.redpois0n.graphs.country.CountryColors;
import com.redpois0n.graphs.country.CountryGraph;

@SuppressWarnings("serial")
public class PanelMainStats extends JPanel {

	private DefaultTableModel uniqueModel;
	private DefaultTableModel totalModel;

	private JTable uniqueTable;
	private JTable totalTable;
	
	private int width = 630;
	private int height = 320;
	public CountryGraph totalGraph;
	public CountryGraph uniqueGraph;
	
	public PanelMainStats() {
		init();
	}
	
	public void init() {
		setLayout(null);

		JScrollPane uniqueScrollPane = new JScrollPane();
		uniqueScrollPane.setBounds(5, 5, 110, height / 2 - 10);
		add(uniqueScrollPane);
		
		uniqueModel = new DefaultTableModel(new Object[][] { { "Unique" } }, 0);
		uniqueTable = new JTable(uniqueModel);
		uniqueScrollPane.add(uniqueTable);
		
		uniqueGraph = new CountryGraph(new CountryColors());
		uniqueGraph.setBounds(120, 5, width - 120 - 10, height / 2 - 10);
		add(uniqueGraph);
		
		
		JScrollPane totalScrollPane = new JScrollPane();
		totalScrollPane.setBounds(5, height / 2, 110, height / 2 - 10);
		add(totalScrollPane);
		
		totalModel = new DefaultTableModel(new Object[][] { { "Total" } }, 0);
		totalTable = new JTable(totalModel);
		totalScrollPane.add(totalTable);
		
		totalGraph = new CountryGraph(new CountryColors());
		totalGraph.setBounds(120, height / 2, width - 120 - 10, height / 2 - 10);
		add(totalGraph);
		
		
		
	}

	public void setActive(boolean b) {
		totalGraph.setActive(b);
		uniqueGraph.setActive(b);
		
		if (b) {
			new ThreadCountryGraph(totalGraph).start();
			new ThreadCountryGraph(uniqueGraph).start();
		}
	}
}
