package jrat.controller.threads;

import jrat.controller.VersionChecker;
import jrat.controller.ui.frames.FrameChangelog;

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
