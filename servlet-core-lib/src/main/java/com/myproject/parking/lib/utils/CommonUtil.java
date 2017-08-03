package com.myproject.parking.lib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

public class CommonUtil {
	private static SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //("dd-MMM-yyyy HH:mm:ss");
	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
	private static NumberFormat nfNoDecimal = new DecimalFormat("#,##0");
	
	private static final Random r = new Random();
	
	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		}
		return str;
	}

	public static String trimAll(String str) {
		if (str != null) {
			str = trim(str).replaceAll("\\s+", " ");
			return str;
		}
		return str;
	}

	/*
	 * Convert from HexByte to HexString
	 */
	public static String toHexString(byte[] data) {
		return new String(Hex.encode(data)).toUpperCase();
	}

	/*
	 * http://www.exampledepot.com/egs/java.util/Bits2Array.html Returns a
	 * bitset containing the values in bytes. The byte-ordering of bytes must be
	 * big-endian which means the most significant bit is in element 0
	 */
	public static BitSet fromByteArray(byte[] bytes) {
		BitSet bits = new BitSet();
		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
				bits.set(i);
			}
		}
		return bits;
	}
	
	/**
	 * normalize phone no to 08xxx
	 * @param phoneNo
	 * @return normalizePhoneNo (08xxx)
	 */
	public static String normalizePhone(String phoneNo) {
		if (phoneNo == null)
			return phoneNo;
		phoneNo = phoneNo.trim();
		if (phoneNo.startsWith("+"))
			phoneNo = phoneNo.substring(1);
		if (phoneNo.startsWith("62"))
			phoneNo = "0" + phoneNo.substring(2);
		
		return phoneNo;
	}

//	public static void main(String[] args) {
//		String x = "6E9EE33C921E0F7C1906E4BF7F57E192CA7318DCE2AF505BB57D4C1BE8FED3B821B10A0E235A1C5E0085B32CD9685476638E020F8444E115EA2666935E23E8B967D4BD930E54377300AB14B58855687FC77C8132E6D360B0844DDBCDA201012A";
//		String b = "testing";
//		String g = toHexString(b.getBytes());
//		System.out.println(g);
//		System.out.println(toHexsString(g.getBytes()));
//	}
//	
//	public static String toHexsString(byte[] data) {
//		return new String(Hex.decode(data)).toUpperCase();
//	}
	
	/*
	 * http://www.exampledepot.com/egs/java.util/Bits2Array.html Returns a byte
	 * array of at least length 1. The most significant bit in the result is
	 * guaranteed not to be a 1 (since BitSet does not support sign extension).
	 * The byte-ordering of the result is big-endian which means the most
	 * significant bit is in element 0. The bit at index 0 of the bit set is
	 * assumed to be the least significant bit.
	 */
	public static byte[] toByteArray(BitSet bits) {
		// byte[] bytes = new byte[bits.length()/8+1];
		// WARNING !!! set fixed to 8 byte, only to be used for ISOMsg
		byte[] bytes = new byte[8];
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i)) {
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
			}
		}
		return bytes;
	}
	
	public static String toHexString(BitSet bits) {
		return toHexString(toByteArray(bits));
	}

	public static BitSet toBitSet(String hexStr) {
		return fromByteArray(toHexByte(hexStr));
	}
	
	/*
	 * Convert from HexString to HexByte
	 */
	public static byte[] toHexByte(String input) {
		return Hex.decode(input);
	}
	
	public static String toBase64(byte[] data) {
		return new String(Base64.encode(data));
	}
	
	public static String hexToAscii(String hex){       
        if (hex.length()%2 != 0){
        	return null;
        }
        StringBuilder sb = new StringBuilder();               
        //Convert Hex 0232343536AB into two characters stream.
        for( int i=0; i < hex.length()-1; i+=2 ){
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);                 
            sb.append((char)decimal);             
        }           
        return sb.toString();
	}
	
	public static String binToHex(String bin) {
	    String hex = Long.toHexString(Long.parseLong(bin, 2));
	    return String.format("%" + (int)(Math.ceil(bin.length() / 4.0)) + "s", hex).replace(' ', '0');
	}
	
	public static String repeat(String str, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static String padRight(String str, char c, int length) {
		if (str == null) str = "";
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() < length)
			sb.append(c);
		return sb.toString();
	}
	
	public static String padRight(String str, int length) {
		return padRight(str, ' ', length);
	}

	public static String padLeft(String str, char c, int length) {
		if (str == null) str = "";
		str = str.trim();
		StringBuilder sb = new StringBuilder();
		if (str.length() >= length) {
			return str;
		}
		int fill = length - str.length();
		while (fill-- > 0)
			sb.append(c);
		sb.append(str);
		return sb.toString();
	}
	
	public static String zeroPadLeft(String str, int length) {
		return padLeft(str, '0', length);
	}
	
	public static String truncate(String str, int length) {
		if (str == null)
			return str;
		if (str.length() <= length) {
			return str;
		}
		else {
			return str.substring(0, length);
		}
	}
	
	/*
	 * The string length return is strict to the length,
	 * it will cut the string if necessary.
	 * This function is important in composing ISO which require an exact length.
	 * return pad right if string < length
	 */
	public static String cutOrPad(String str, int length) {
		if (str == null) {
			return repeat(" ", length);
		}
		if (str.length() < length) {
			return padRight(str, length);
		}
		else if (str.length() > length) {
			return truncate(str, length);
		}
		return str;
	}
	
	/*
	 * return the last x chars from right
	 */
	public static String rightSubstring(String str, int length) {
		if (str.length() <= length) {
			return str;
		}
		return str.substring(str.length() - length);
	}
	
	/**
	 * Parse QueryString from HTTP Get Method, e.g: ?query=1&param1=value1
	 * 
	 * @param query
	 *            Input
	 * @param params
	 *            Output Properties that hold the parameter that has been parsed
	 */
	public static void parseQueryString(String query, Properties params) {
		StringTokenizer st = new StringTokenizer(query, "?&=", true);
		String previous = null;
		while (st.hasMoreTokens()) {
			String current = st.nextToken();
			if ("?".equals(current) || "&".equals(current)) {
				// ignore
			} else if ("=".equals(current)) {
				String value = "";
				if (st.hasMoreTokens())
					value = st.nextToken();
				if ("&".equals(value))
					value = ""; // ignore &
				params.setProperty(previous, value);
			} else {
				previous = current;
			}
		}
	}
	
	/**
	 * format phone number to International / ISO format: +628xxxx
	 * @param phoneNo
	 * @return
	 */
	public static String formatPhoneIntl(String phoneNo) {
		if (phoneNo == null)
			return phoneNo;
		phoneNo = phoneNo.trim();
		// TODO: this always assume that country is Indonesian, it must be
		// refactored to support other country
		if (phoneNo.startsWith("0000")) // for 021900xxx, 0812xxxx
			phoneNo = "+62" + phoneNo.substring(4);
		else if (phoneNo.startsWith("000")) // for 0081286904196
			phoneNo = "+62" + phoneNo.substring(3);
		else if (phoneNo.startsWith("00")) // for 0008128690419
			phoneNo = "+62" + phoneNo.substring(2);
		else if (phoneNo.startsWith("0")) // for 0000812869041
			phoneNo = "+62" + phoneNo.substring(1);
		else if (phoneNo.startsWith("+0")) // for +021900xxx
			phoneNo = "+62" + phoneNo.substring(2);
		else if (!phoneNo.startsWith("+")) // for 6221xxxx
			phoneNo = "+" + phoneNo;
		return phoneNo;
	}
	
	/**
	 * format phone number to Local: 08xxxx
	 * @param phoneNo
	 * @return
	 */
	public static String formatPhoneLocal(String phoneNo) {
		if (phoneNo == null)
			return phoneNo;
		if (phoneNo.startsWith("+")) {
			phoneNo = phoneNo.substring(1);
		}
		if (phoneNo.startsWith("62")) {
			phoneNo = 0 + phoneNo.substring(2);
		}
		if (!phoneNo.startsWith("0")) {
			phoneNo = 0 + phoneNo;
		}
		return phoneNo;
	}
	
	/*
	 * refer to: http://www.javapractices.com/topic/TopicAction.do?Id=62
	 */
	public static int random(int range) {
		return random(0, range);
	}

	public static int random(int start, int end) {
		long range = (long) end - (long) start + 1;
		long fraction = (long) (range * r.nextDouble());
		int randomNumber = (int) (fraction + start);
		return randomNumber;
	}
	
	public static String displayDateTime(Date dateTime) {
		return sdfDateTime.format(dateTime);
	}
	
	public static String displayDate(Date dateTime) {
		return sdfDate.format(dateTime);
	}
	
	public static Date strToDateTime(String pattern, String input) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.parse(input);
	}
	
	public static String strDbToDateTime(Date date) {
		if (date == null) return null;
			return sdfDateTime.format(date);
	}
	
	// This function returns start hour of today
	// e.g: 2010-01-01 12:00:00.000
	public static Date getEarlyDate(Date date) {
		Calendar calFr = Calendar.getInstance();
		calFr.setTime(date);

		Date dateFr = calFr.getTime();
		
		calFr.set(Calendar.HOUR, 0);
		calFr.set(Calendar.MINUTE, 0);
		calFr.set(Calendar.SECOND, 0);
		calFr.set(Calendar.MILLISECOND, 0);
		calFr.set(Calendar.AM_PM, Calendar.AM);

		dateFr = calFr.getTime();
		
		return dateFr;
	}

	// This function returns start hour of today
	// e.g: 2010-01-01 23:59:59.999
	public static Date getLaterDate(Date date) {
		Calendar calFr = Calendar.getInstance();
		calFr.setTime(date);
		Calendar calTo = Calendar.getInstance();
		calTo.set(calFr.get(Calendar.YEAR), calFr.get(Calendar.MONTH), calFr.get(Calendar.DATE));

		calTo.set(Calendar.HOUR, 11);
		calTo.set(Calendar.MINUTE, 59);
		calTo.set(Calendar.SECOND, 59);
		calFr.set(Calendar.MILLISECOND, 999);
		calFr.set(Calendar.AM_PM, Calendar.PM);
		
		Date dateTo = calTo.getTime();
		
		return dateTo;
	}
	
	public static String displayNumberNoDecimal(double number) {
		return nfNoDecimal.format(number);
	}
	
	public static byte[] intToByteArray(int value) {
	    return new byte[] {
	    		(byte)(value >>> 24),
	    		(byte)(value >>> 16),
	    		(byte)(value >>> 8),
	    		(byte)value};
	}
	
	public static int byteArrayToInt(byte[] b) {
		return   b[3] & 0xFF |
				(b[2] & 0xFF) << 8 |
				(b[1] & 0xFF) << 16 |
				(b[0] & 0xFF) << 24;
	}
	
	// convert money into indonesian format
	// e.g: 7,500.00 --> "7.500" (digits 0)
	public static String formatMoneyIndo(double money, int digits){
		Locale lokal = new Locale("id","ID"); // language code and country code from ISO 639 googling
		NumberFormat nf = NumberFormat.getInstance(lokal);
		nf.setMaximumFractionDigits(digits); // for max digit after comma e.g Rp7.000,00
		nf.setMinimumFractionDigits(digits);
		return nf.format(money);
	}
	
	// convert integer into indonesian format
	// e.g: 7,500.00 --> "7.500" (digits 0)
	public static String formatIntegerIndo(int money, int digits){
		Locale lokal = new Locale("id","ID"); // language code and country code from ISO 639 googling
		NumberFormat nf = NumberFormat.getInstance(lokal);
		nf.setMaximumFractionDigits(digits); // for max digit after comma e.g Rp7.000,00
		nf.setMinimumFractionDigits(digits);
		return nf.format(money);
	}
	
	// convert indonesian money format into double
	// e.g: "7.500" --> 7,500
	public static Number parseMoneyIndo(String money) {
		try {
			Locale lokal = new Locale("id","ID");
			NumberFormat nf = NumberFormat.getInstance(lokal);
			return nf.parse(money);
		} catch (ParseException e) {
			return 0;
		}
	}
	
	/*
	 * list file in directory
	 */
	public static String[] listDir(String path) {
		File file = new File(path);

		File files[];

		files = file.listFiles();
		Arrays.sort(files);

		String[] list = new String[files.length];
		for (int i = 0, n = files.length; i < n; i++) {
			list[i] = files[i].toString();
		}
		return list;
	}

	/*
	 * Compress files into a single zip file
	 */
	public static void createZipFile(Set<String> inputFiles, String outputFile)
			throws IOException {
		byte[] buf = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outputFile));
			Iterator<String> it = inputFiles.iterator();
			while (it.hasNext()) {
				String file = it.next();

				FileInputStream in = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(extractFileName(file)));

				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			throw e;
		}
	}

	public static String extractFileName(String path) {
		String result = "";
		int pos = path.lastIndexOf(".");
		if (pos > 0) {
			if (path.indexOf("/") >= 0) {
				result = path.substring(path.lastIndexOf("/") + 1);
			} else {
				result = path.substring(path.lastIndexOf("\\") + 1);
			}
		}
		return result;
	}

	public static boolean compareEqual(String str1, String str2) {
		if (str1 == null) str1 = "";
		if (str2 == null) str2 = "";
		
		str1 = str1.trim();
		str2 = str2.trim();
		
		return str1.equalsIgnoreCase(str2);
	}

	public static String cleanString(String str) {
		return str.replaceAll("[^a-zA-Z0-9 \\s\\.\\,]", " ").trim();
	}

	public static String formatDestroy(String input) {		
		SimpleDateFormat df = new SimpleDateFormat("yyMMd");
		String temp = df.format(new Date());	
		String tanggal = temp.substring(4);
		String bulan = temp.substring(2,4);
		String tahun = temp.substring(1, 2);
		if (Integer.parseInt(bulan) > 9) {
			if (bulan.equals("10")) {
				bulan = "A";
			} else {
				if (bulan.equals("11")) {
					bulan = "B";
				} else {
					if (bulan.equals("12"))
						bulan = "C";
				}
			}
		} 
		return tahun + bulan + tanggal + "#" + input;
	}
	
	public static String generateRandomPin(int pinLength) {
		StringBuffer sb = new StringBuffer(pinLength);
		for (int i = 0; i < pinLength; i++)
			sb.append("" + r.nextInt(10));
		return sb.toString();
	}
	
	public static String generateAlphaNumeric(int length) {
		String C = "QWERTYUIOPLKJHGFDAZXCVBNM0987654321";
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			int idx = r.nextInt(C.length());
			sb.append(C.substring(idx, idx + 1));
		}
		return sb.toString();
	}
	

}
