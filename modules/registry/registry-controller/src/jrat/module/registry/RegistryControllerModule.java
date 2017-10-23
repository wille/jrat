package jrat.module.registry;

import jrat.api.Module;
import iconlib.IconUtils;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.controller.ui.frames.Frame;
import jrat.module.registry.ui.FrameRemoteRegistry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistryControllerModule extends Module {

    public RegistryControllerModule(int seed) {
        super("Registry Viewer", seed);
    }

    public void init() {
        IncomingPackets.register((short) 54, PacketResult.class);

        final JMenuItem mntmRemoteRegistry = new JMenuItem("Remote Registry");
        mntmRemoteRegistry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                AbstractSlave slave = Frame.panelClients.getSelectedSlave();
                if (slave != null && slave instanceof Slave) {
                    FrameRemoteRegistry frame = new FrameRemoteRegistry((Slave) slave);
                    frame.setVisible(true);
                }
            }
        });
        mntmRemoteRegistry.setIcon(IconUtils.getIcon("registry"));

        Frame.panelClients.mnQuickOpen.add(mntmRemoteRegistry);
    }

}
