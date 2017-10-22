package io.jrat.controller.threads;

import io.jrat.controller.Constants;
import io.jrat.controller.VersionChecker;
import io.jrat.controller.net.WebRequest;
import io.jrat.controller.ui.frames.FrameChangelog;

public class ThreadCheckVersion extends Thread {
    
    public ThreadCheckVersion() {
        super("Version checker thread");
    }

    public void run() {
       try {
    	   VersionChecker checker = new VersionChecker();
           
           if (!checker.isUpToDate()) {
        	   FrameChangelog frame = new FrameChangelog();
    			frame.setVisible(true);
    			frame.setTitle("New version! - " + checker.getLatest());
           }
       } catch (Exception ex) {
    	   ex.printStackTrace();
       }
    }

}
