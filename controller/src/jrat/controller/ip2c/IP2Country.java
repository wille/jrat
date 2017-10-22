package jrat.controller.ip2c;

import java.io.IOException;
import java.util.StringTokenizer;

public class IP2Country {
	public static final int WEBHOSTING_INFO_CSV_FORMAT = 0;
	public static final int SOFTWARE77_CSV_FORMAT = 1;
	public static final int NO_CACHE = 0;
	public static final int MEMORY_MAPPED = 1;
	public static final int MEMORY_CACHE = 2;
	private RandomAccessInput m_input;
	private int m_firstTableOffset;
	private int m_secondTableOffset;
	private int m_countriesOffset;
	private int m_numRangesFirstTable;
	private int m_numRangesSecondTable;
	private int m_numCountries;

	public IP2Country() throws IOException {
		this(0);
	}

	public IP2Country(int paramInt) throws IOException {
		this("ip-to-country.bin", paramInt);
	}

	public IP2Country(String paramString, int paramInt) throws IOException {
		switch (paramInt) {
		case 0:
			this.m_input = new RandomAccessFile2(paramString, "r");
			break;
		case 1:
			this.m_input = new MemoryMappedRandomAccessFile(paramString, "r");
			break;
		case 2:
			this.m_input = new RandomAccessBuffer(paramString);
		}
		byte[] arrayOfByte = new byte[4];
		readFully(arrayOfByte);
		if ((arrayOfByte[0] != 105) || (arrayOfByte[1] != 112) || (arrayOfByte[2] != 50) || (arrayOfByte[3] != 99))
			throw new IOException("Invalid file signature");
		if (readInt() != 2)
			throw new IOException("Invalid format version");
		this.m_firstTableOffset = readInt();
		this.m_numRangesFirstTable = readInt();
		this.m_secondTableOffset = readInt();
		this.m_numRangesSecondTable = readInt();
		this.m_countriesOffset = readInt();
		this.m_numCountries = readInt();
	}

	public Country getCountry(String paramString) throws IOException {
		return getCountry(parseIP(paramString));
	}

	private static int parseIP(String paramString) {
		StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ".");
		int i = Short.parseShort(localStringTokenizer.nextToken());
		int j = Short.parseShort(localStringTokenizer.nextToken());
		int k = Short.parseShort(localStringTokenizer.nextToken());
		int m = Short.parseShort(localStringTokenizer.nextToken());
		int n = i << 24 & 0xFF000000 | j << 16 & 0xFF0000 | k << 8 & 0xFF00 | m & 0xFF;
		return n;
	}

	public Country getCountry(int paramInt) throws IOException {
		short s;
		if (paramInt >= 0) {
			s = findCountyCode(paramInt, 0, this.m_numRangesFirstTable, true);
		} else {
			int i = paramInt - 2147483647;
			s = findCountyCode(i, 0, this.m_numRangesSecondTable, false);
		}
		if (s == 0)
			return null;
		return findCountry(s, 0, this.m_numCountries);
	}

	private short findCountyCode(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException {
		int i = (paramInt1 + paramInt2) / 2;
		Pair localPair1 = getPair(i, paramBoolean);
		long l = localPair1.m_ip;
		if (paramLong < l) {
			if (paramInt1 + 1 == paramInt2)
				return 0;
			return findCountyCode(paramLong, paramInt1, i, paramBoolean);
		}
		if (paramLong > l) {
			Pair localPair2 = getPair(i + 1, paramBoolean);
			if (paramLong < localPair2.m_ip)
				return localPair1.m_key;
			if (paramInt1 + 1 == paramInt2)
				return 0;
			return findCountyCode(paramLong, i, paramInt2, paramBoolean);
		}
		return localPair1.m_key;
	}

	public Country findCountry(short paramShort) throws IOException {
		return findCountry(paramShort, 0, this.m_numCountries);
	}

	public Country findCountry(short paramShort, int paramInt1, int paramInt2) throws IOException {
		int i = (paramInt1 + paramInt2) / 2;
		short s = getCountryCode(i);
		if (s == paramShort)
			return loadCountry(i);
		if (paramShort > s)
			return findCountry(paramShort, i, paramInt2);
		return findCountry(paramShort, paramInt1, i);
	}

	private Country loadCountry(int paramInt) throws IOException {
		int i = this.m_countriesOffset + paramInt * 10;
		seek(i);
		short s = readShort();
		int j = readInt();
		int k = readInt();
		seek(k);
		int m = readShort();
		byte[] arrayOfByte = new byte[m];
		readFully(arrayOfByte);
		return new Country(s, j, new String(arrayOfByte));
	}

	private short getCountryCode(int paramInt) throws IOException {
		int i = this.m_countriesOffset + paramInt * 10;
		seek(i);
		return readShort();
	}

	private Pair getPair(int paramInt, boolean paramBoolean) throws IOException {
		int i;
		if (paramBoolean) {
			if (paramInt > this.m_numRangesFirstTable)
				return new Pair();
			i = this.m_firstTableOffset + paramInt * 6;
		} else {
			if (paramInt > this.m_numRangesSecondTable)
				return new Pair();
			i = this.m_secondTableOffset + paramInt * 6;
		}
		seek(i);
		Pair localPair = new Pair();
		byte[] arrayOfByte = new byte[4];
		readFully(arrayOfByte);
		localPair.m_ip = toInt(arrayOfByte);
		localPair.m_key = readShort();
		return localPair;
	}

	static int toInt(byte[] paramArrayOfByte) {
		return paramArrayOfByte[0] << 24 & 0xFF000000 | paramArrayOfByte[1] << 16 & 0xFF0000 | paramArrayOfByte[2] << 8 & 0xFF00 | paramArrayOfByte[3] & 0xFF;
	}

	static byte[] toBytes(long paramLong) {
		byte[] arrayOfByte = new byte[4];
		arrayOfByte[0] = ((byte) (int) ((paramLong & 0xFF000000) >> 24));
		arrayOfByte[1] = ((byte) (int) ((paramLong & 0xFF0000) >> 16));
		arrayOfByte[2] = ((byte) (int) ((paramLong & 0xFF00) >> 8));
		arrayOfByte[3] = ((byte) (int) ((paramLong & 0xFF) >> 0));
		return arrayOfByte;
	}

	private int readInt() throws IOException {
		return this.m_input.readInt();
	}

	private short readShort() throws IOException {
		return this.m_input.readShort();
	}

	private void readFully(byte[] paramArrayOfByte) throws IOException {
		this.m_input.readFully(paramArrayOfByte);
	}

	private void seek(int paramInt) throws IOException {
		this.m_input.seek(paramInt);
	}

	static class Pair {
		int m_ip;
		short m_key;
	}
}
