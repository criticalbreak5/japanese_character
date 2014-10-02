/*!
 * JapaneseCharacter
 * https://github.com/criticalbreak5/
 *
 * Copyright 2014 criticalbreak5's
 * Released under the MIT license
 * http://opensource.org/licenses/mit-license.php
 *
 * Date: 2014-10-03T00:00Z
 */
package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class JapaneseCharacter {
	
	
	public static void main(String args[]) throws Exception {
		
		
		// JIS半角カナ
		StringBuffer buffer = new StringBuffer();
		append(buffer, "JISX0201_KANA");
		for (int i = 0xA1; i <= 0xDF; i++) {
			append(buffer, "JISX0201_KANA", true, i);
		}
		
    	// JIS第一水準漢字
		append(buffer, "JISX0208_1_8");
		for (int i = 0x8140; i <= 0x84FC; i++) { // 1区~8区
			append(buffer, "JISX0208_1_8", false, i);
		}
		
		// ＪＩＳ漢字（NEC拡張外字）
		append(buffer, "JISX0208_13");
		for (int i = 0x8740; i <= 0x879E; i++) { // 13区
			append(buffer, "JISX0208_13", false, i);
		}
		
		// JIS第一水準漢字(続)
		append(buffer, "JISX0208_16_47");
		for (int i = 0x889F; i <= 0x989E; i++) { // 16区~47区
			append(buffer, "JISX0208_16_47", false, i);
		}
		
    	// JIS第二水準漢字
		append(buffer, "JISX0208_48_84");
		for (int i = 0x989F; i <= 0xEAFC; i++) { // 48区~84区
			append(buffer, "JISX0208_48_84", false, i);
		}
		
		
		PrintWriter printWriter = null;
		
		try {
			
			printWriter = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									new FileOutputStream(new File("JapaneseCharacter.js")), "UTF-8")));
			printWriter.print(buffer.toString());
			printWriter.flush();
			
		} finally {
			
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}
	
	private static void append(StringBuffer buffer, String type) {
		
		buffer.append("var ");
		buffer.append(type);
		buffer.append(" = {};");
		buffer.append("\n");
	}
	
	private static void append(StringBuffer buffer, String type, boolean isSingleByte, int intChar) throws UnsupportedEncodingException {
		
		
	    byte[] byteChar;
	    if (isSingleByte) {
	    	byteChar = new byte[1];
	    	byteChar[0] = (byte)(Short.parseShort(Long.toHexString(intChar).substring(0, 2), 16));
	    } else {
	    	byteChar = new byte[2];
		    byteChar[0] = (byte)(Short.parseShort(Long.toHexString(intChar).substring(0, 2), 16));
		    byteChar[1] = (byte)(Short.parseShort(Long.toHexString(intChar).substring(2, 4), 16));
	    }
	    String stringChar = new String(byteChar, "Windows-31J");
	    
		if (stringChar.length() == 1 && 
				((isSingleByte && stringChar.getBytes("Windows-31J").length == 1) || (!isSingleByte && stringChar.getBytes("Windows-31J").length == 2))) {
			
			buffer.append(type);
			buffer.append("['");
			buffer.append(stringChar);
			buffer.append("']");
			buffer.append(" = ");
			buffer.append("true;");
			buffer.append("\n");
		}
	}
}