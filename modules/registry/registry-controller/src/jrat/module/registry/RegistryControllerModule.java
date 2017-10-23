package jrat.module.registry;

import jrat.api.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.registry.ui.FrameRemoteRegistry;

public class RegistryControllerModule extends Module {

    public RegistryControllerModule() {
        super("Registry");
    }

    public void init() throws Exception {
        IncomingPackets.register((short) 54, PacketResult.class);

        ClientMenuItem item = new ClientMenuItem("Registry", Resources.getIcon("registry"), new ClientMenuListener() {
            @Override
            public void onClick(AbstractSlave slave) {
                new FrameRemoteRegistry((Slave) slave).setVisible(true);
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);
    }

}
