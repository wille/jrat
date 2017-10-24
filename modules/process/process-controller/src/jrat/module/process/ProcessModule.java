package jrat.module.process;

import jrat.api.ClientEventListener;
import jrat.api.Module;
import jrat.api.Resources;
import jrat.api.ui.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;

public class ProcessModule extends Module {

    public void init() throws Exception {
        IncomingPackets.register((short) 25, PacketProcessData.class);

        ClientMenuItem item = new ClientMenuItem("Processes", Resources.getIcon("process"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                FrameRemoteProcess frame = new FrameRemoteProcess((Slave) slave);
                frame.setVisible(true);
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelTab action = new ControlPanelTab(ControlPanel.Category.SYSTEM, "Processes", Resources.getIcon("process"), PanelControlRemoteProcess.class);

        ControlPanel.ITEMS.add(action);
    }
}
