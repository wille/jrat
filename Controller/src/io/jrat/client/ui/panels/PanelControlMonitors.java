package io.jrat.client.ui.panels;

import io.jrat.client.Slave;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import com.redpois0n.graphs.monitors.MonitorListener;
import com.redpois0n.graphs.monitors.PanelMonitors;
import com.redpois0n.graphs.monitors.PanelMonitors.PanelMonitor;
import com.redpois0n.graphs.monitors.RemoteMonitor;

@SuppressWarnings("serial")
public class PanelControlMonitors extends PanelControlParent {
	
	private PanelMonitors panelMonitors;
	private JLabel lblXY;
	private JLabel lblWidthHeight;
	private JPanel panel_1;

	public PanelControlMonitors(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("Monitor")); 
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(null);
		
		lblXY = new JLabel("X, Y: ");
		lblXY.setBounds(10, 23, 226, 14);
		panel_1.add(lblXY);
		
		lblWidthHeight = new JLabel("Width, Height:");
		lblWidthHeight.setBounds(10, 48, 226, 14);
		panel_1.add(lblWidthHeight);
		
		panelMonitors = new PanelMonitors(sl.getMonitors(), false);
		
		panelMonitors.addListener(new MonitorListener() {
			@Override
			public void onMonitorChange(RemoteMonitor monitor) {						
				PanelMonitor panel = null;
				
				for (PanelMonitor panel1 : panelMonitors.getPanels()) {
					if (panel1.getMonitor().equals(monitor)) {
						panel = panel1;
						break;
					}
				}
				
				if (panel != null) {
					lblXY.setText("X, Y: " + panel.getMonitor().getX() + ", " + panel.getMonitor().getY());
					lblWidthHeight.setText("Width, Height: " + panel.getMonitor().getWidth() + ", " + panel.getMonitor().getHeight());
					panel_1.setBorder(BorderFactory.createTitledBorder(panel.getMonitor().getLabel())); 
				}
			}
		});
		
		
		scrollPane.setViewportView(panelMonitors);
		setLayout(groupLayout);
		
		panelMonitors.reload();

	}
}
