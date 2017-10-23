package jrat.module.registry;

import jrat.api.Module;
import jrat.api.Resources;
import jrat.controller.AbstractSlave;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.controller.ui.frames.Frame;
import jrat.module.registry.ui.FrameRemoteRegistry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistryControllerModule extends Module {

    public RegistryControllerModule() {
        super("Registry Viewer");
    }

    public void init() throws Exception {
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
        mntmRemoteRegistry.setIcon(Resources.getIcon("registry"));

        Frame.panelClients.mnQuickOpen.add(mntmRemoteRegistry);
    }

}
