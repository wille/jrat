package io.jrat.client.ui.frames;

import io.jrat.client.ui.panels.PluginPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FramePluginsNew extends JFrame {

	private JPanel contentPane;
	private JPanel panelGrid;
	private JScrollPane panel_1;

	public FramePluginsNew() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePluginsNew.class.getResource("/icons/plugin.png")));
		setTitle("Plugin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panel_1 = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
		);
		
		panelGrid = new JPanel();
		panel_1.setViewportView(panelGrid);
		contentPane.setLayout(gl_contentPane);
		
		GridBagLayout gbl_panelGrid = new GridBagLayout();
		gbl_panelGrid.columnWidths = new int[] { 0, 0 };
		gbl_panelGrid.rowHeights = new int[] { 0, 0 };
		gbl_panelGrid.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelGrid.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelGrid.setLayout(gbl_panelGrid);
	}
	
	public void addPluginPanel(PluginPanel panel) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.gridx = 0;
		grid.gridy = panelGrid.getComponentCount();
		panelGrid.add(panel, grid);
		
		panel.setIndex(panelGrid.getComponentCount() - 1);
	}
}
