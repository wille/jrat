package se.jrat.common.script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public abstract class AbstractScript {
	
	protected String content;
	
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void perform() {
		try {
			if (getExtension() == null) {
				perform(null);
			} else {
				File file = getFile();
				
				FileWriter fstream = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(content);
				out.close();
				
				perform(file);
			}
 		} catch (Exception ex) {
 			ex.printStackTrace();
 		}
	}

	protected abstract void perform(File file) throws Exception;
	
	public abstract String getExtension();
	
	public File getFile() throws Exception {
		return File.createTempFile("script_" + getExtension(), "." + getExtension());
	}
	
	public static AbstractScript getScript(String s) throws Exception {
		for (Script script : Script.values()) {
			if (script.getIdentifier().equals(s)) {
				return script.getClazz().newInstance();
			}
		}
		
		return null;
	}

}
