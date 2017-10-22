package io.jrat.controller.build.shellcode;

public interface Shellcode {

	String generate(String arrayName, byte[] array) throws Exception;
}
