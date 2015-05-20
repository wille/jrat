package se.jrat.controller.ui.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import se.jrat.controller.threads.ThreadGraph;
import se.jrat.controller.ui.components.TableModel;
import se.jrat.controller.ui.components.DefaultJTable;
import se.jrat.controller.utils.FlagUtils;

import com.redpois0n.graphs.graph.Graph;
import com.redpois0n.graphs.graph.GraphColors;
import com.redpois0n.graphs.graph.GraphEntry;

@SuppressWarnings("serial")
public class PanelMainStats extends JScrollPane {

	private TableModel countryModel;
	private TableModel osModel;

	private JTable countryTable;
	private JTable osTable;

	private int width = 630;
	private int height = 320;

	private boolean initialized;

	public Graph countryGraph;
	public Graph osGraph;

	private JScrollPane countryTableScrollPane;
	private JScrollPane osTableScrollPane;

	private JScrollPane countryScrollPane;
	private JScrollPane osScrollPane;

	private DefaultTableCellRenderer flagRenderer = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if (value != null && value.toString().length() > 0) {
				c.setIcon(FlagUtils.getFlag(value.toString().toLowerCase()));
				c.setText(FlagUtils.getStringFromIso2(value.toString()));
			}

			return c;
		}
	};
	
	private DefaultTableCellRenderer osRenderer = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			if (value != null && value.toString().length() > 0) {
				GraphEntry entry = (GraphEntry) value;
				c.setIcon(entry.getIcon());
				c.setText(entry.getDisplay());
			}

			return c;
		}
	};

	public PanelMainStats() {
		init();
	}

	public void init() {
		setLayout(null);

		if (!initialized) {
			initialized = true;

			
			osGraph = new Graph(new GraphColors()) {
				@Override
				public synchronized void onUpdate(List<GraphEntry> list, int x) {
					osGraph.setPreferredSize(new Dimension(x, osTableScrollPane.getHeight()));

					while (osModel.getRowCount() > 0) {
						osModel.removeRow(0);
					}

					for (GraphEntry entry : list) {
						osModel.insertRow(0, new Object[] { entry });
					}
				}
			};
			osGraph.setDrawBackgroundSquare(true);

			osScrollPane = new JScrollPane(osGraph);
			add(osScrollPane);

			countryGraph = new Graph(new GraphColors()) {
				@Override
				public synchronized void onUpdate(List<GraphEntry> list, int x) {
					countryGraph.setPreferredSize(new Dimension(x, countryScrollPane.getHeight()));

					while (countryModel.getRowCount() > 0) {
						countryModel.removeRow(0);
					}

					List<GraphEntry> added = new ArrayList<GraphEntry>();
					for (GraphEntry entry : list) {
						if (added.contains(entry)) {
							continue;
						}
						added.add(entry);
						
						countryModel.insertRow(0, new Object[] { entry.getDisplay() });
					}
				}
			};

			countryScrollPane = new JScrollPane(countryGraph);
			add(countryScrollPane);
			countryModel = new TableModel(new Object[][] {}, new String[] { "Countries" });
			countryTable = new DefaultJTable(countryModel);
			countryTable.setDefaultRenderer(Object.class, flagRenderer);
			countryTable.setRowHeight(20);
			countryTableScrollPane = new JScrollPane();
			add(countryTableScrollPane);
			countryTableScrollPane.setViewportView(countryTable);

			osModel = new TableModel(new Object[][] {}, new String[] { "Operating Systems" });
			osTable = new DefaultJTable(osModel);
			osTable.setDefaultRenderer(Object.class, osRenderer);
			osTable.setRowHeight(20);
			osTableScrollPane = new JScrollPane();
			add(osTableScrollPane);
			osTableScrollPane.setViewportView(osTable);

		}

		osTableScrollPane.setBounds(5, 5, 150, height / 2 - 10);
		osScrollPane.setBounds(160, 5, width - 160 - 10, height / 2 - 10);

		countryTableScrollPane.setBounds(5, height / 2, 150, height / 2 - 10);
		countryScrollPane.setBounds(160, height / 2, width - 160 - 10, height / 2 - 10);

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
		countryGraph.setActive(b);
		osGraph.setActive(b);

		if (b) {
			new ThreadGraph(countryGraph).start();
			new ThreadGraph(osGraph).start();
		}
	}
}
