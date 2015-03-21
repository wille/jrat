package se.jrat.common.script;

import java.io.File;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JavaScript extends Script {

	public JavaScript(String content) {
		super(content);
	}

	@Override
	public void perform(File file) throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		engine.eval(content);
	}

	@Override
	public String getExtension() {
		return null;
	}

}
