package pro.jrat.build.shellcode;

import java.io.File;
import java.io.FileWriter;

public final class Util {
	
	public static final String lineSeparator = "\r\n";
	
	public static final String get2DigitByte(byte b) {
		return String.format("%02X", b);
	}
	
	public static final String getPythonByte(byte b) {
		return "\\x" + Util.get2DigitByte(b);
	}
	
	public static final String get0XByte(byte b) {
		return "0x" + Util.get2DigitByte(b);
	}
	
	public static final void writeToFile(File file, String string) throws Exception {
		FileWriter writer = new FileWriter(file);
		writer.write(string);
		writer.close();
	}

}
