package se.jrat.client.ui.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.threads.ThreadGraph;
import se.jrat.client.utils.FlagUtils;

import com.redpois0n.graphs.graph.Graph;
import com.redpois0n.graphs.graph.GraphColors;
import com.redpois0n.graphs.graph.GraphEntry;

@SuppressWarnings("serial")
public class PanelMainStats extends JPanel {

	private DefaultTableModel uniqueModel;
	private DefaultTableModel totalModel;

	private JTable uniqueTable;
	private JTable totalTable;

	private int width = 630;
	private int height = 320;

	private boolean initialized;

	public Graph totalGraph;
	public Graph uniqueGraph;

	private JScrollPane totalTableScrollPane;
	private JScrollPane uniqueTableScrollPane;

	private JScrollPane totalScrollPane;
	private JScrollPane uniqueScrollPane;

	private DefaultTableCellRenderer flagRenderer = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			c.setIcon(FlagUtils.getFlag(value.toString().toLowerCase()));
			c.setText(value.toString());

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

			uniqueTableScrollPane = new JScrollPane();
			add(uniqueTableScrollPane);

			uniqueGraph = new Graph(new GraphColors()) {
				@Override
				public void onUpdate(List<GraphEntry> list, int x) {
					uniqueGraph.setPreferredSize(new Dimension(x, uniqueTableScrollPane.getHeight()));

					while (uniqueModel.getRowCount() > 0) {
						uniqueModel.removeRow(0);
					}

					for (GraphEntry entry : list) {
						uniqueModel.insertRow(0, new Object[] { entry.getDisplay() });
					}
				}
			};

			uniqueScrollPane = new JScrollPane(uniqueGraph);
			add(uniqueScrollPane);

			totalGraph = new Graph(new GraphColors()) {
				@Override
				public void onUpdate(List<GraphEntry> list, int x) {
					totalGraph.setPreferredSize(new Dimension(x, totalScrollPane.getHeight()));

					while (totalModel.getRowCount() > 0) {
						totalModel.removeRow(0);
					}

					for (GraphEntry entry : list) {
						totalModel.insertRow(0, new Object[] { entry.getDisplay() });
					}
				}
			};

			totalScrollPane = new JScrollPane(totalGraph);
			add(totalScrollPane);

			uniqueModel = new DefaultTableModel(new Object[][] {}, new String[] { "Unique" });
			uniqueTable = new JTable(uniqueModel);
			uniqueTable.setDefaultRenderer(Object.class, flagRenderer);

			uniqueTableScrollPane.setViewportView(uniqueTable);

			totalTableScrollPane = new JScrollPane();
			add(totalTableScrollPane);

			totalModel = new DefaultTableModel(new Object[][] {}, new String[] { "Total" });
			totalTable = new JTable(totalModel);
			totalTable.setDefaultRenderer(Object.class, flagRenderer);

			totalTableScrollPane.setViewportView(totalTable);
		}

		uniqueTableScrollPane.setBounds(5, 5, 110, height / 2 - 10);

		uniqueScrollPane.setBounds(120, 5, width - 120 - 10, height / 2 - 10);

		totalTableScrollPane.setBounds(5, height / 2, 110, height / 2 - 10);

		totalScrollPane.setBounds(120, height / 2, width - 120 - 10, height / 2 - 10);

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
			new ThreadGraph(totalGraph).start();
			new ThreadGraph(uniqueGraph).start();
		}
	}
}
