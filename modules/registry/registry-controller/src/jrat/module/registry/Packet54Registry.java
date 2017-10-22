package jrat.module.registry;

import com.redpois0n.pathtree.FolderTreeNode;
import com.redpois0n.pathtree.PathTreeNode;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.incoming.AbstractIncomingPacket;
import java.io.DataInputStream;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

public class Packet54Registry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();

		while (dis.readBoolean()) {
			String line = slave.readLine();
			String[] args = line.trim().split("    ");

			String name = args[0];
			name = name.substring(name.lastIndexOf("\\") + 1, name.length());

			FrameRemoteRegistry frame = FrameRemoteRegistry.INSTANCES.get(slave);

			if (frame != null) {
				if (args.length == 3) {
					ImageIcon icon;
					if (args[1].equalsIgnoreCase("REG_SZ")) {
						icon = FrameRemoteRegistry.ICON_REGSZ;
					} else {
						icon = FrameRemoteRegistry.ICON_REG01;
					}

					frame.getRenderer().getIconMap().put(name, icon);
					frame.getModel().addRow(new Object[] { name, args[2], args[1] });
				} else if (line.length() > 0) {
					if (frame.getTree().exists(path + "\\" + name)) {
						continue;
					}
					
					PathTreeNode parent = frame.getTree().getNodeFromPath(path);

					PathTreeNode node = new FolderTreeNode(name, FrameRemoteRegistry.ICON_FOLDER);
					try {
						if (!name.equals(parent.toString())) {
							frame.getTree().getPathModel().insertNodeInto(node, parent, parent.getChildCount());
						}

						TreePath tp = new TreePath(parent.getPath());
						frame.getTree().expandPath(tp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
