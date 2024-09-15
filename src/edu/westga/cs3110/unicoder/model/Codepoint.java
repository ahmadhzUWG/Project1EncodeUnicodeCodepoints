package edu.westga.cs3110.unicoder.model;

/**
 * Represents a Unicode codepoint
 * 
 * @author ahmad
 * @version Fall 2024
 * 
 */
public class Codepoint {
	private static final int UNICODE_MIN = 0x0000;
	private static final int UNICODE_MAX = 0x10FFFF;
	private static final int HEX_BASE = 16;
	private static final int BINARY_BASE = 2;
	
	private String hexString;
	
	/**
	 * Creates a codepoint with the given hexadecimal string
	 * 
	 * @precondition hexString != null && !hexString.isBlank() && isValidCodepoint(hexString)
	 * @postcondition codepoint is created
	 * 
	 * @param hexString the hexadecimal string associated with the codepoint, without spaces or 0x prefix
	 */
	public Codepoint(String hexString) {
		if (hexString == null) {
			throw new IllegalArgumentException("Hexadecimal string cannot be null");
		}
		if (hexString.isBlank()) {
			throw new IllegalArgumentException("Hexadecimal string cannot be empty");
		}
		if (!isValidCodepoint(hexString)) {
			throw new IllegalArgumentException("The hexadecimal string isn't a valid codepoint");
		} else {
			this.hexString = hexString;    
		}
		
	}
	
	/**
	 * Returns the codepoint in UTF-32 encoding, as an 
	 * 8-digit hexadecimal string, without spaces or 0x prefix
	 * 
	 * @precondition none
	 * @postcondition none
	 *  
	 * @return the codepoint as a 4-byte UTF-32 encoded string, without spaces or 0x prefix
	 */
	public String toUTF32() {
		return String.format("%08x", Integer.parseInt(this.hexString, HEX_BASE));
	}
	
	/**
	 * Returns the codepoint in UTF-16 encoding, as either a
	 * 4-digit or 8-digit hexadecimal string (as appropriate), without spaces or 0x prefix.
	 * Will return null if not possible to encode string
	 * 
	 * @precondition none
	 * @postcondition none
	 *  
	 * @return the codepoint as a 2-byte/4-byte (as appropriate) UTF-16 encoded string, without spaces or 0x prefix or null if isn't possible
	 */
	public String toUTF16() {
		int min2ByteRange1 = 0x0000;
		int max2ByteRange1 = 0xD7FF; 
		
		int min2ByteRange2 = 0xE000;
		int max2ByteRange2 = 0xFFFF;
		
		int min4ByteRange = 0x10000;
		int max4ByteRange = 0x10FFFF;
		
		int valueOfHex = Integer.parseInt(this.hexString, HEX_BASE);
		boolean isBetweenFirst2ByteRange = valueOfHex >= min2ByteRange1 && valueOfHex <= max2ByteRange1;
		boolean isBetweenSecond2ByteRange = valueOfHex >= min2ByteRange2 && valueOfHex <= max2ByteRange2;
		
		if (isBetweenFirst2ByteRange || isBetweenSecond2ByteRange) {
			return String.format("%04x", valueOfHex);
		} else if (valueOfHex >= min4ByteRange && valueOfHex <= max4ByteRange) {
			int point = valueOfHex - min4ByteRange;
			
			String pointAs20Bits = String.format("%20s", Integer.toBinaryString(point)).replaceAll(" ", "0");
			String first10Bits = pointAs20Bits.substring(0, 10);
			String last10Bits = pointAs20Bits.substring(10);
			
			return this.decodeHighSurrogate(first10Bits) + this.decodeLowerSurrogate(last10Bits);
		}
		
		return null;
	}
	
	/**
	 * Returns the codepoint in UTF-8 encoding, as either a 
	 * 2-digit, 4-digit, 6-digit, or 8-digit hexadecimal string (as appropriate), without spaces or 0x prefix
	 * 
	 * @precondition none
	 * @postcondition none
	 *  
	 * @return the codepoint as a 1-byte/2-byte/3-byte/4-byte (as appropriate) UTF-8 encoded string, without spaces or 0x prefix
	 */
	public String toUTF8() {
		int min1ByteRange = 0x0000;
		int max1ByteRange = 0x007F;
		 
		int min2ByteRange = 0x0080;
		int max2ByteRange = 0x07FF;
		
		int min3ByteRange = 0x0800;
		int max3ByteRange = 0xFFFF;
		
		int valueOfHex = Integer.parseInt(this.hexString, HEX_BASE);
		
		if (valueOfHex >= min1ByteRange && valueOfHex <= max1ByteRange) {
			return String.format("%02x", valueOfHex);
		} else if (valueOfHex >= min2ByteRange && valueOfHex <= max2ByteRange) {
			return this.encodeAs2ByteUTF8();
		} else if (valueOfHex >= min3ByteRange && valueOfHex <= max3ByteRange) {
			return this.encodeAs3ByteUTF8();
		}
		
		return this.encodeAs4ByteUTF8();
	}
	
	private String encodeAs4ByteUTF8() {
		int valueOfHex = Integer.parseInt(this.hexString, HEX_BASE);
		String hexInBinary = String.format("%24s", Integer.toBinaryString(valueOfHex)).replaceAll(" ", "0");
		int lower6BitsStartingIndex = hexInBinary.length() - 6;
		int next6BitsStartingIndex = hexInBinary.length() - 12;
		int sixBitsAfterNextStartingIndex = hexInBinary.length() - 18;
		int upper3BitsStartingIndex = hexInBinary.length() - 21;
		
		String lower6Bits = hexInBinary.substring(lower6BitsStartingIndex);
		String next6Bits = hexInBinary.substring(next6BitsStartingIndex, lower6BitsStartingIndex);
		String sixBitsAfterNext = hexInBinary.substring(sixBitsAfterNextStartingIndex, next6BitsStartingIndex);
		String upper3Bits = hexInBinary.substring(upper3BitsStartingIndex, sixBitsAfterNextStartingIndex);
	
		String byte1 = "11110" + upper3Bits; 
		String byte2 = "10" + sixBitsAfterNext;
		String byte3 = "10" + next6Bits;
		String byte4 = "10" + lower6Bits;
		String bytes = byte1 + byte2 + byte3 + byte4;
		
		return Long.toHexString(Long.parseLong(bytes, BINARY_BASE));
	}

	private String encodeAs3ByteUTF8() {
		int valueOfHex = Integer.parseInt(this.hexString, HEX_BASE);
		String hexInBinary = String.format("%16s", Integer.toBinaryString(valueOfHex)).replaceAll(" ", "0");
		int lower6BitsStartingIndex = hexInBinary.length() - 6;
		int next6BitsStartingIndex = hexInBinary.length() - 12;
		
		String lower6Bits = hexInBinary.substring(lower6BitsStartingIndex);
		String next6Bits = hexInBinary.substring(next6BitsStartingIndex, lower6BitsStartingIndex);
		String upper4Bits = hexInBinary.substring(0, next6BitsStartingIndex);
		
		String byte1 = "1110" + upper4Bits;
		String byte2 = "10" + next6Bits;
		String byte3 = "10" + lower6Bits;
		String bytes = byte1 + byte2 + byte3;
		
		return Integer.toHexString(Integer.parseInt(bytes, BINARY_BASE));
	}

	private String encodeAs2ByteUTF8() {
		int valueOfHex = Integer.parseInt(this.hexString, HEX_BASE);
		String hexInBinary = String.format("%16s", Integer.toBinaryString(valueOfHex)).replaceAll(" ", "0");
		int lower6BitsStartingIndex = hexInBinary.length() - 6;
		int next5BitsStartingIndex = hexInBinary.length() - 11;
		
		String lower6Bits = hexInBinary.substring(lower6BitsStartingIndex);
		String next5Bits = hexInBinary.substring(next5BitsStartingIndex, lower6BitsStartingIndex);
		
		String byte1 = "110" + next5Bits;
		String byte2 = "10" + lower6Bits;
		String bytes = byte1 + byte2;
		
		return Integer.toHexString(Integer.parseInt(bytes, BINARY_BASE));
	}

	/**
	 * Gets the hexadecimal string and returns it
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the hexadecimal string
	 */
	public String getHexString() {
		return this.hexString; 
	}
	
	private static boolean isValidCodepoint(String hexString) {
		try {
			int codepoint = Integer.parseInt(hexString, HEX_BASE);
			
			return codepoint >= UNICODE_MIN && codepoint <= UNICODE_MAX;
		} catch (Exception abc) { 
			return false;
		}
	}
	
	private String decodeHighSurrogate(String upperBits) {
		String hex = Integer.toHexString(Integer.parseInt(upperBits, BINARY_BASE));
		return Integer.toHexString(0xD800 + Integer.parseInt(hex, HEX_BASE));
	}

	private String decodeLowerSurrogate(String lowerBits) {
		String hex = Integer.toHexString(Integer.parseInt(lowerBits, BINARY_BASE));
		return Integer.toHexString(0xDC00 + Integer.parseInt(hex, HEX_BASE)); 
	}
	
	
}
