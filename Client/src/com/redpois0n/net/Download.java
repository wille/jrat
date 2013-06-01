package com.redpois0n.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;

import com.redpois0n.ErrorDialog;
import com.redpois0n.listeners.DownloadListener;


public class Download extends Thread {

	public File file;
	public String url;
	public DownloadListener l;

	public Download(DownloadListener l, File file, String url) {
		this.url = url;
		this.file = file;
		this.l = l;
	}
	
	public int getLength() throws Exception {		
        try {
        	HttpURLConnection conn = (HttpURLConnection) WebRequest.getUrl(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            int i = conn.getContentLength();
            return i;
        } catch (Exception e) {
            return -1;
        }     
	}

	public void run() {
		try {
			Thread.sleep(1000L);
			int len = getLength();
			if (len == -1) {
				throw new Exception("Failed to get length");
			}
			BufferedInputStream in = null;
			FileOutputStream fout = null;
			try {
				in = new BufferedInputStream(WebRequest.getUrl(url).openStream());
				fout = new FileOutputStream(file);
				byte data[] = new byte[1024];
				int all = 0;
				int count;
				while ((count = in.read(data, 0, 1024)) != -1) {
					all += count;
					l.reportProgress(all, len);
					fout.write(data, 0, count);
				}
				in.close();
				fout.close();
			} finally {
				l.done("Finished downloading");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
			l.fail(ex.getMessage());
		}

	}

}
