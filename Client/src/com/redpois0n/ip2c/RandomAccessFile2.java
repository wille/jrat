package com.redpois0n.ip2c;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RandomAccessFile2 extends RandomAccessFile implements RandomAccessInput {
	
	public RandomAccessFile2(String paramString1, String paramString2) throws FileNotFoundException {
		super(paramString1, paramString2);
	}
}
