package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IncomingPackets {

	private static final Map<Short, Class<? extends IncomingPacket>> PACKETS_INCOMING = new HashMap<>();

	static {
		reload();
	}

	private static void reload() {
		PACKETS_INCOMING.clear();
		PACKETS_INCOMING.put((short) 2, Packet2Status.class);
		PACKETS_INCOMING.put((short) 3, Packet3Initialized.class);
		PACKETS_INCOMING.put((short) 4, Packet4InitOperatingSystem.class);
		PACKETS_INCOMING.put((short) 5, Packet5InitUserHost.class);
		PACKETS_INCOMING.put((short) 6, Packet6InitVersion.class);
		PACKETS_INCOMING.put((short) 7, Packet7InitServerID.class); //
		PACKETS_INCOMING.put((short) 8, Packet8InitCountry.class);
		PACKETS_INCOMING.put((short) 9, Packet9InitJavaVersion.class);
		PACKETS_INCOMING.put((short) 10, Packet10InitInstallPath.class);
		PACKETS_INCOMING.put((short) 11, Packet11InitInstallationDate.class);
		PACKETS_INCOMING.put((short) 12, Packet12InitLocalAddress.class);
		PACKETS_INCOMING.put((short) 13, Packet13InitTotalMemory.class);
		PACKETS_INCOMING.put((short) 14, Packet14InitAvailableCores.class);
		PACKETS_INCOMING.put((short) 15, Packet15InitJavaPath.class);
		PACKETS_INCOMING.put((short) 17, Packet17InitDrives.class);
		PACKETS_INCOMING.put((short) 18, Packet18InitMonitors.class);	
		PACKETS_INCOMING.put((short) 19, Packet19InitCPU.class);
		PACKETS_INCOMING.put((short) 20, Packet20Headless.class);
		PACKETS_INCOMING.put((short) 21, Packet21RemoteShell.class);
		PACKETS_INCOMING.put((short) 24, Packet24UsedMemory.class);
        PACKETS_INCOMING.put((short) 29, Packet29ServerDownloadPart.class);
		PACKETS_INCOMING.put((short) 30, Packet30BeginServerDownload.class);
		PACKETS_INCOMING.put((short) 31, Packet31CompleteServerDownload.class);
		PACKETS_INCOMING.put((short) 32, Packet32SystemProperties.class);
		PACKETS_INCOMING.put((short) 33, Packet33Thumbnail.class);
		PACKETS_INCOMING.put((short) 41, Packet41Clipboard.class);
		PACKETS_INCOMING.put((short) 44, Packet44SystemJavaProperty.class);
		PACKETS_INCOMING.put((short) 49, Packet49LanDevices.class);
		PACKETS_INCOMING.put((short) 51, Packet51ActivePort.class);
		PACKETS_INCOMING.put((short) 52, Packet52WindowsService.class);
		PACKETS_INCOMING.put((short) 53, Packet53RegistryStartup.class);
		PACKETS_INCOMING.put((short) 55, Packet55InstalledProgram.class);
		PACKETS_INCOMING.put((short) 56, Packet56NetworkAdapter.class);
		PACKETS_INCOMING.put((short) 58, Packet58ServerDownloadSoundCapture.class);
		PACKETS_INCOMING.put((short) 60, Packet60Error.class);
		PACKETS_INCOMING.put((short) 65, Packet65ErrorLog.class);
	}

    /**
     * Registers an incoming packet
     * @param header
     * @param clazz
     * @throws Exception if the header already is registered
     */
	public static void register(short header, Class<? extends IncomingPacket> clazz) throws Exception {
		if (PACKETS_INCOMING.containsKey(header)) {
			throw new Exception("header '" + header + "' already exists");
		}

	    PACKETS_INCOMING.put(header, clazz);
    }

	public static boolean execute(short header, Slave slave) {
		try {
			IncomingPacket ac = null;
			Set<Short> set = PACKETS_INCOMING.keySet();

			for (Short s : set) {
				if (s == header) {
					ac = PACKETS_INCOMING.get(s).newInstance();
					break;
				}
			}

			if (ac != null) {
				ac.read(slave);
			}

			return ac != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}