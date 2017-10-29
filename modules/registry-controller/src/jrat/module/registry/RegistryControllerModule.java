package jrat.module.registry;

import jrat.api.ClientEventListener;
import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.registry.ui.PanelRegistry;

public class RegistryControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 54, PacketResult.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN, "Registry", Resources.getIcon("registry"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelRegistry((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Registry", Resources.getIcon("registry"), PanelRegistry.class));
    }
}
