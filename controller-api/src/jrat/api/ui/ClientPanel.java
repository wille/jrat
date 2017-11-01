package jrat.api.ui;

import jrat.controller.Slave;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * A panel with components that are owned by a client
 */
public abstract class ClientPanel extends JPanel {

    protected Slave slave;

    public final String title;
    public final ImageIcon icon;

    public ClientPanel(Slave slave, String title, ImageIcon icon) {
        this.slave = slave;
        this.slave.addFrame(getClass(), this);

        this.title = title;
        this.icon = icon;
    }

    /**
     * Called when the panel is not used anymore or the frame is manually closed
     */
    public void dispose() {
        this.slave.removePanel(this.getClass());
    }

    /**
     * Called when the panel is shown or the frame is opened
     */
    public void opened() {

    }

    /**
     * Creates a new window and puts this panel inside it, setting title and icon
     */
    public Frame displayFrame() {
        Frame frame = new Frame(this);
        frame.setBounds(0, 0, 500, 300);
        frame.setVisible(true);
        this.opened();

        return frame;
    }

    /**
     * Wrap a client panel in a JFrame
     */
    public static class Frame extends JFrame implements WindowListener {

        private ClientPanel child;

        public Frame(ClientPanel child) {
            this.child = child;

            if (child.icon != null) {
                super.setIconImage(child.icon.getImage());
            }

            super.setTitle(child.title);

            super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            super.add(this.child);
        }

        public void windowClosing(WindowEvent event) {
            this.child.dispose();
        }

        public void windowOpened(WindowEvent e) { }

        public void windowClosed(WindowEvent e) { }

        public void windowIconified(WindowEvent e) { }

        public void windowDeiconified(WindowEvent e) { }

        public void windowActivated(WindowEvent e) { }

        public void windowDeactivated(WindowEvent e) { }
    }
}
