package pro.jrat.ui.panels;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pro.jrat.threads.ThreadCountryGraph;

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
	
	private boolean initialized;
	
	public CountryGraph totalGraph;
	public CountryGraph uniqueGraph;
	private JScrollPane totalScrollPane;
	private JScrollPane uniqueScrollPane;

	public PanelMainStats() {
		init();
	}

	public void init() {
		setLayout(null);
		
		if (!initialized) {
			initialized = true;
			
			uniqueScrollPane = new JScrollPane();
			add(uniqueScrollPane);

			uniqueModel = new DefaultTableModel(new Object[][] { { "Unique" } }, 0);
			uniqueTable = new JTable(uniqueModel);
			uniqueScrollPane.add(uniqueTable);
			
			uniqueGraph = new CountryGraph(new CountryColors());
			add(uniqueGraph);
			
			totalScrollPane = new JScrollPane();
			add(totalScrollPane);
			
			totalModel = new DefaultTableModel(new Object[][] { { "Total" } }, 0);
			totalTable = new JTable(totalModel);
			totalScrollPane.add(totalTable);
			
			totalGraph = new CountryGraph(new CountryColors());
			add(totalGraph);
		}
		
		uniqueScrollPane.setBounds(5, 5, 110, height / 2 - 10);		
		
		uniqueGraph.setBounds(120, 5, width - 120 - 10, height / 2 - 10);
		
		totalScrollPane.setBounds(5, height / 2, 110, height / 2 - 10);
		
		totalGraph.setBounds(120, height / 2, width - 120 - 10, height / 2 - 10);	
		
		addComponentListener(new ComponentListener() { 
			
			@Override
			public void componentResized(ComponentEvent e) {
				boolean b = width == getWidth() && height == getHeight();
				width = getWidth();
				height = getHeight();
				
				if (!b) {
					init();
				}
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				
			}
		});
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
