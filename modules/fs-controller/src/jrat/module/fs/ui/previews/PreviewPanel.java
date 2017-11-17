package jrat.module.fs.ui.previews;

import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PreviewPanel<T> extends ClientPanel {

    private List<CloseListener> listeners = new ArrayList<>();

    public PreviewPanel(Slave slave, String title, ImageIcon icon) {
        super(slave, title, icon);
    }

    /**
     * Add preview data to this preview panel
     * @param data
     */
    public abstract void addData(T data);

    public final void close() {
        for (CloseListener l : listeners) {
            l.close();
        }
    }

    public void addCloseListener(CloseListener l) {
        listeners.add(l);
    }

    public interface CloseListener {
        void close();
    }
}
