package jrat.module.chat;

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
import jrat.module.chat.ui.PanelChat;

public class ChatControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 35, PacketIncomingMessage.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN, "Chat", Resources.getIcon("chat"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelChat((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Chat", Resources.getIcon("chat"), PanelChat.class));
    }
}