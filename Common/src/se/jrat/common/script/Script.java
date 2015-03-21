package se.jrat.common.script;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class Script {
	
	public static final Map<String, Class<? extends Script>> CLASSES = new HashMap<String, Class<? extends Script>>();
	
	static {
		CLASSES.put("html", HTMLScript.class);
		CLASSES.put("vbs", VisualBasicScript.class);
		CLASSES.put("bat", BatchScript.class);
		CLASSES.put("sh", ShellScript.class);
		CLASSES.put("js", JavaScript.class);
		CLASSES.put("py", PythonScript.class);
	}

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
		return File.createTempFile("s", "." + getExtension());
	}
	
	public static Script getScript(String s) throws Exception {
		return CLASSES.get(s).newInstance();
	}

}
