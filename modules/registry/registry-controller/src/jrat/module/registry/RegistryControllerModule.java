package jrat.module.registry;

import apiv2.ControllerModule;
import iconlib.IconUtils;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Slave;
import io.jrat.controller.packets.incoming.IncomingPackets;
import io.jrat.controller.ui.frames.Frame;
import io.jrat.controller.ui.panels.PanelMainClients;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistryControllerModule extends ControllerModule {

    public void init() {
        IncomingPackets.register((short) 54, Packet54Registry.class);

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

        System.out.println("RegistryControllerModule initialized");
    }

}
