package com.redpois0n.build;

import com.redpois0n.ui.frames.FrameExecutableInfo;

public class BuildExecutable {

	public static void build(String input, String output, FrameExecutableInfo frame) throws Exception {
		BuildExecutable.build(input, output, frame.useIcon() ? frame.getIcon() : "NULL", frame.getAssemblyTitle(), frame.getDescription(), frame.getCompany(), frame.getProduct(), frame.getCopyright(), frame.getTrademark(), frame.getVersion(), frame.getAssemblyVersion());
	}

	public static void build(String input, String output, String icon, String title, String description, String company, String product, String copyright, String trademark, String version, String assemblyversion) throws Exception {
		String[] p = new String[] { "files\\Builder.exe", input, output, icon, title, description, company, product, copyright, trademark, version, assemblyversion };
		Runtime.getRuntime().exec(p).waitFor();
	}

}
