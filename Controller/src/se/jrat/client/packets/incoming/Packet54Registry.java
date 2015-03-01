package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import se.jrat.client.Slave;
import se.jrat.client.ui.components.pathtree.PathTreeNode;
import se.jrat.client.ui.frames.FrameRemoteRegistry;


public class Packet54Registry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();
		
		int count = slave.readInt();
		
		String[] args = new String[count];
		for (int i = 0; i < count; i++) {
			args[i] = slave.readLine();
		}
		
		String name = args[0];

		if (name.length() == 0) {
			return;
		}
		
		name = name.substring(name.lastIndexOf("\\") + 1, name.length());

		FrameRemoteRegistry frame = FrameRemoteRegistry.instances.get(slave);

		if (frame != null) {
			if (args.length == 3) {
				ImageIcon icon;
				if (args[1].equalsIgnoreCase("REG_SZ")) {
					icon = FrameRemoteRegistry.REGSZ_ICON;
				} else {
					icon = FrameRemoteRegistry.REG01_ICON;
				}

				frame.getRenderer().icons.put(name, icon);
				frame.getModel().addRow(new Object[] { name, args[2], args[1] });
			} else {
				System.out.println(path);
				frame.getTreeModel().insertNodeInto(new PathTreeNode(name, FrameRemoteRegistry.FOLDER_ICON), (DefaultMutableTreeNode) frame.getTree().getNodeFromPath(path), 0);
			}
		}

	}

}
