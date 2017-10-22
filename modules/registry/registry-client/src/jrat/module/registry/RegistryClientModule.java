package jrat.module.registry;

import apiv2.StubModule;
import io.jrat.stub.packets.incoming.AbstractIncomingPacket;
import io.jrat.stub.packets.incoming.Packet98InjectJAR;

public class RegistryClientModule extends StubModule {

    public void init() {
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 97, Packet97RegistryAdd.class);
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 99, Packet99RegistryDelete.class);
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 79, Packet79BrowseRegistry.class);
        System.out.println("RegistryClientModule initialized");
    }
}
