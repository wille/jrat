package jrat.module.fs;

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
import jrat.module.fs.packets.*;
import jrat.module.fs.ui.FrameRemoteFiles;

public class FileSystemControllerModule extends Module {

    public void init() throws Exception {
        IncomingPackets.register((short) 34, Packet34CustomDirectory.class);
        IncomingPackets.register((short) 22, Packet22ListFiles.class);
        IncomingPackets.register((short) 37, Packet37SearchResult.class);
        IncomingPackets.register((short) 46, Packet46FileHash.class);
        IncomingPackets.register((short) 59, Packet59ThumbnailPreview.class);

        ClientMenuItem item = new ClientMenuItem("File Browser", Resources.getIcon("folder-tree"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new FrameRemoteFiles((Slave) slave).displayFrame();
            }
        });

        ClientMenu.addItem(ClientMenu.Category.QUICK_OPEN, item);

        ControlPanelTab action = new ControlPanelTab(ControlPanel.Category.SYSTEM, "File Browser", Resources.getIcon("folder-tree"), FrameRemoteFiles.class);

        ControlPanel.ITEMS.add(action);
    }
}
