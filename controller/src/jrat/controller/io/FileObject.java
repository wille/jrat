package jrat.controller.io;

import javax.swing.*;

public class FileObject {

	private String path;
	private boolean hidden;
	private Icon icon;
	private long date;
	private long size;

	public FileObject(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public Icon getIcon() {
		return this.icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public boolean isDirectory() {
		return size == 0;
	}
	
	@Override
	public String toString() {
		return this.path;
	}

}
