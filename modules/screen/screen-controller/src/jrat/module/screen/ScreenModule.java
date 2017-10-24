package jrat.module.screen;

import jrat.api.*;
import jrat.api.ui.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.screen.packets.PacketRemoteScreenChunk;
import jrat.module.screen.packets.PacketRemoteScreenCompleted;
import jrat.module.screen.packets.PacketReceiveAllThumbnails;
import jrat.module.screen.ui.PanelScreenController;

public class ScreenModule extends Module {

    public void init() throws Exception {
        IncomingPackets.register((short) 68, PacketRemoteScreenCompleted.class);
        IncomingPackets.register((short) 71, PacketReceiveAllThumbnails.class);
        IncomingPackets.register((short) 26, PacketRemoteScreenChunk.class);

        ClientMenuItem item = new ClientMenuItem("View Screen", Resources.getIcon("screen"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelScreenController((Slave) slave).displayFrame();
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelTab action = new ControlPanelTab(ControlPanel.Category.SYSTEM, "View Screen", Resources.getIcon("screen"), PanelScreenController.class);

        ControlPanel.ITEMS.add(action);
    }
}
