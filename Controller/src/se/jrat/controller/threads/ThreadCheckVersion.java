package se.jrat.controller.threads;

import se.jrat.controller.Constants;
import se.jrat.controller.VersionChecker;
import se.jrat.controller.net.WebRequest;
import se.jrat.controller.ui.frames.FrameChangelog;

public class ThreadCheckVersion extends Thread {
    
    public ThreadCheckVersion() {
        super("Version checker thread");
    }

    public void run() {
       try {
    	   VersionChecker checker = new VersionChecker();
           
           if (!checker.isUpToDate()) {
        	   FrameChangelog frame = new FrameChangelog(WebRequest.getUrl(Constants.HOST + "/api/changelog.php"), checker.getLatest());
    			frame.setVisible(true);
    			frame.setTitle("New version! - " + checker.getLatest());
           }
       } catch (Exception ex) {
    	   ex.printStackTrace();
       }
    }

}
