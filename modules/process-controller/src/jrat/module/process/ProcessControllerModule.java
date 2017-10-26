package jrat.module.process;

import jrat.api.ClientEventListener;
import jrat.api.Module;
import jrat.api.Resources;
import jrat.api.ui.ClientMenu;
import jrat.api.ui.ClientMenuItem;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.process.ui.PanelProcesses;

public class ProcessControllerModule extends Module {

    public void init() throws Exception {
        IncomingPackets.register((short) 25, PacketProcessData.class);

        ClientMenuItem item = new ClientMenuItem("Processes", Resources.getIcon("process"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelProcesses((Slave) slave).displayFrame();
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelTab action = new ControlPanelTab(ControlPanel.Category.SYSTEM, "Processes", Resources.getIcon("process"), PanelProcesses.class);

        ControlPanel.ITEMS.add(action);
    }
}
