package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;
import pro.jrat.plugins.PluginEventHandler;


public abstract class AbstractOutgoingPacket {
	
	public abstract void write(Slave slave, DataOutputStream dos) throws Exception;
	
	public abstract byte getPacketId();
	
	public synchronized final void send(Slave slave, DataOutputStream dos) {
		try {
			
			PluginEventHandler.onSendPacket(getPacketId(), slave);
			
			dos.writeByte(getPacketId());
			this.write(slave, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
