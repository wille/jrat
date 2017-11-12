package jrat.module.fs;

import jrat.api.ClientEventListener;
import jrat.api.ControllerModule;
import jrat.api.Resources;
import jrat.api.ui.*;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.module.fs.packets.download.Packet29DownloadPart;
import jrat.module.fs.packets.download.Packet30BeginDownload;
import jrat.module.fs.packets.download.Packet31CompleteDownload;
import jrat.module.fs.ui.PanelFileTransfers;
import jrat.module.fs.packets.*;
import jrat.module.fs.ui.PanelFileManager;
import jrat.module.fs.ui.PanelFileSystem;
import jrat.module.fs.ui.PanelSearchFiles;
import jrat.module.fs.ui.PanelThumbView;

public class FileSystemControllerModule extends ControllerModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 29, Packet29DownloadPart.class);
        IncomingPackets.register((short) 30, Packet30BeginDownload.class);
        IncomingPackets.register((short) 31, Packet31CompleteDownload.class);
        IncomingPackets.register((short) 34, Packet34CustomDirectory.class);
        IncomingPackets.register((short) 22, Packet22ListFiles.class);
        IncomingPackets.register((short) 37, Packet37SearchResult.class);
        IncomingPackets.register((short) 59, Packet59ThumbnailPreview.class);
        IncomingPackets.register((short) 42, Packet42PreviewFile.class);
        IncomingPackets.register((short) 43, Packet43PreviewImage.class);
        IncomingPackets.register((short) 45, Packet45ArchivePreview.class);

        menuItems.add(new ClientMenuItem(ClientMenu.Category.QUICK_OPEN, "File Browser", Resources.getIcon("folder-stand"), new ClientEventListener() {
            @Override
            public void emit(AbstractSlave slave) {
                new PanelFileSystem((Slave) slave).displayFrame();
            }
        }));

        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.FILES, "Files", Resources.getIcon("folders-stack"), PanelFileManager.class));
        controlPanelItems.add(new StaticControlPanelTab(ControlPanel.Category.FILES, "Transfers", Resources.getIcon("arrow-down"), PanelFileTransfers.instance));
        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.FILES, "Thumbnails", Resources.getIcon("images-stack"), PanelThumbView.class));
        controlPanelItems.add(new ControlPanelTab(ControlPanel.Category.FILES, "Search", Resources.getIcon("folder-search"), PanelSearchFiles.class));

        new ThreadTransferSpeed().start();
    }
}
