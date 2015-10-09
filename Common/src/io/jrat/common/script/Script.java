package io.jrat.common.script;


public enum Script {
	
	HTML("html", HTMLScript.class),
	VBS("vbs", VisualBasicScript.class),
	BATCH("bat", BatchScript.class),
	SHELL("sh", ShellScript.class),
	JAVASCRIPT("js", JavaScript.class),
	PYTHON("py", PythonScript.class);
	
	private String identifier;
	private Class<? extends AbstractScript> clazz;

	private Script(String identifier, Class<? extends AbstractScript> clazz) {
		this.identifier = identifier;
		this.clazz = clazz;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Class<? extends AbstractScript> getClazz() {
		return clazz;
	}

}
