package io.jrat.controller.ui.frames;

import io.jrat.controller.addons.OnlinePlugin;
import io.jrat.controller.ui.panels.PluginPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameInstallPlugins extends BaseFrame {

	private JPanel contentPane;
	private JPanel panelGrid;
	private JScrollPane panel_1;
	public JProgressBar progressBar;
	public JLabel lblStatus;

	public FrameInstallPlugins() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameInstallPlugins.class.getResource("/icons/plugin.png")));
		setTitle("View Available Plugins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		panel_1 = new JScrollPane();
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		lblStatus = new JLabel("Status");
		lblStatus.setVisible(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblStatus, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
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
		
		loadPlugins();
	}

	public void loadPlugins() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<OnlinePlugin> list = OnlinePlugin.getAvailablePlugins();
					
					for (OnlinePlugin op : list) {
						PluginPanel pp = new PluginPanel(FrameInstallPlugins.this, op);
						
						addPluginPanel(pp);
						panelGrid.repaint();
						panelGrid.validate();
						repaint();
						validate();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}).start();
	}
	
	public void addPluginPanel(PluginPanel panel) {
		GridBagConstraints grid = new GridBagConstraints();
		grid.gridx = 0;
		grid.gridy = panelGrid.getComponentCount();
		panelGrid.add(panel, grid);
		
		panel.setIndex(panelGrid.getComponentCount() - 1);
	}
}
