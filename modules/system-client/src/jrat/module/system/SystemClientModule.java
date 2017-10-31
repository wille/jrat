package jrat.module.system;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;

public class SystemClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 33, Packet33UsedMemory.class);
    }

}
