package jrat.module.system;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;
import jrat.module.system.packets.Packet33UsedMemory;
import jrat.module.system.packets.Packet61SystemJavaProperties;
import jrat.module.system.packets.Packet78RegistryStartup;
import jrat.module.system.packets.Packet81InstalledPrograms;

public class SystemClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 33, Packet33UsedMemory.class);
        IncomingPackets.register((short) 61, Packet61SystemJavaProperties.class);
        IncomingPackets.register((short) 78, Packet78RegistryStartup.class);
        IncomingPackets.register((short) 81, Packet81InstalledPrograms.class);
    }

}
