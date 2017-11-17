package jrat.module.system;

import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.system.packets.Packet24UsedMemory;
import jrat.module.system.packets.Packet44SystemJavaProperty;
import jrat.module.system.packets.Packet53RegistryStartup;
import jrat.module.system.packets.Packet55InstalledProgram;
import jrat.module.system.ui.PanelApplications;
import jrat.module.system.ui.PanelJVM;
import jrat.module.system.ui.PanelStartup;
import jrat.module.system.ui.PanelMemoryUsage;

public class SystemControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 24, Packet24UsedMemory.class);
        IncomingPackets.register((short) 53, Packet53RegistryStartup.class);
        IncomingPackets.register((short) 55, Packet55InstalledProgram.class);
        IncomingPackets.register((short) 44, Packet44SystemJavaProperty.class);

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Resources", Resources.getIcon("memory"), PanelMemoryUsage.class));
        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Applications", Resources.getIcon("application-detail"), PanelApplications.class));
        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Startup Items", Resources.getIcon("application-run"), PanelStartup.class));
        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "JVM Info", Resources.getIcon("java"), PanelJVM.class));
    }
}
