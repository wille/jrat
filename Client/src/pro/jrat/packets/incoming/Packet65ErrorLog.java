package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.FrameControlPanel;
import pro.jrat.ui.panels.PanelControlErrorLog;


public class Packet65ErrorLog extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String trace = slave.readLine();
		
		FrameControlPanel frame = FrameControlPanel.instances.get(slave);
		
		if (frame != null) {
			PanelControlErrorLog panel = (PanelControlErrorLog) frame.panels.get("error log");
			
			panel.getPane().setText(trace);
		}
	}

}