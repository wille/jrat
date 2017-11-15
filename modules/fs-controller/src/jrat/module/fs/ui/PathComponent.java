package jrat.module.fs.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PathComponent extends JPanel {

    private List<DirectorySelectListener> listeners = new ArrayList<>();

    private String separator;

    public PathComponent(String separator) {
        this.separator = separator;
        setLayout(new FlowLayout());
    }

    /**
     * Clear all components and build clickable labels displaying the path
     * @param path
     */
    public void setDirectory(String path) {
        removeAll();

        String current = "";

        for (String sub : path.split(this.separator)) {
            current += sub + separator;

            JLabel lbl = new JLabel(sub);
            lbl.setForeground(Color.blue);
            add(lbl);

            final String c = current;

            lbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    for (DirectorySelectListener listener : listeners) {
                        listener.onSelect(c);
                    }
                }
            });

            addSeparator();
        }
    }

    public void addListener(DirectorySelectListener l) {
        this.listeners.add(l);
    }

    /**
     * Add a directory separator
     */
    private void addSeparator() {
        JLabel lbl = new JLabel(this.separator);
        add(lbl);
    }

    public interface DirectorySelectListener {

        void onSelect(String directory);
    }
}
