package jrat.controller.utils;

import java.io.File;
import java.io.FileWriter;

public final class ShellcodeUtils {

	public static final String LINE_SEPARATOR = "\r\n";

	public static String get2DigitByte(byte b) {
		return String.format("%02X", b);
	}

	public static String getPythonByte(byte b) {
		return "\\x" + ShellcodeUtils.get2DigitByte(b);
	}

	public static String get0XByte(byte b) {
		return "0x" + ShellcodeUtils.get2DigitByte(b);
	}

	public static void writeToFile(File file, String string) throws Exception {
		FileWriter writer = new FileWriter(file);
		writer.write(string);
		writer.close();
	}

}
