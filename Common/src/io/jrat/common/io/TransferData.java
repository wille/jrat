package io.jrat.common.io;

import io.jrat.common.TransferRunnable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class TransferData {
	
	public enum State {
		IN_PROGRESS, PAUSED, COMPLETED, ERROR
    }
	
	private State state;
	private boolean memory;
	private String remote;
	private File local;
	private OutputStream out;
	private int read;
	private long total;
	private TransferRunnable runnable;
	private Object object;
	
	private long lastRead;
	private long speed;
	
	public TransferData() {
		this(false);
	}
	
	public TransferData(boolean memory) {
		this.memory = memory;
	}

	public OutputStream getOutputStream() throws Exception {
		if (out == null) {
			if (memory) {
				out = new ByteArrayOutputStream();
			} else {
				out = new FileOutputStream(local);
			}
		}
		
		return out;
	}
	
	public void increaseRead(int read) {
		this.read += read;
	}

	public String getRemoteFile() {
		return remote;
	}

	public void setRemoteFile(String remote) {
		this.remote = remote;
	}

	public File getLocalFile() {
		return local;
	}

	public void setLocalFile(File local) {
		this.local = local;
	}

	public int getRead() {
		return read;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public void setRunnable(TransferRunnable runnable) {
		this.runnable = runnable;
	}
	
	public TransferRunnable getRunnable() {
		return this.runnable;
	}
	
	public void start() {
		runnable.start();
	}
	
	public boolean isUpload() {
		return runnable != null;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public long getLastRead() {
		return lastRead;
	}

	public void setLastRead(long lastRead) {
		this.lastRead = lastRead;
	}
	
	public long getDiff() {
		return read - lastRead;
	}
	
	public long getSpeed() {
		return this.speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public long getWhatsLeft() {
		return total - read;
	}
	
}