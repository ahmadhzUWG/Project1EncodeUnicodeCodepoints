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
		return String.format("%08x", Integer.parseInt(this.hexString, 16));
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
		
		int valueOfHex = Integer.parseInt(this.hexString, 16);
		boolean isBetweenFirst2ByteRange = valueOfHex >= min2ByteRange1 && valueOfHex <= max2ByteRange1;
		boolean isBetweenSecond2ByteRange = valueOfHex >= min2ByteRange2 && valueOfHex <= max2ByteRange2;
		
		if (isBetweenFirst2ByteRange || isBetweenSecond2ByteRange) {
			return String.format("%04x", valueOfHex);
		} else if (valueOfHex >= min4ByteRange && valueOfHex <= max4ByteRange) {
			int point = valueOfHex - min4ByteRange;
			int decimalValue = Integer.parseInt(Integer.toHexString(point), 16);
			
			String pointAs20Bits = String.format("%20s", Integer.toBinaryString(decimalValue)).replaceAll(" ", "0");
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
		throw new UnsupportedOperationException(); 
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
			int codepoint = Integer.parseInt(hexString, 16);
			
			return codepoint >= UNICODE_MIN && codepoint <= UNICODE_MAX;
		} catch (Exception abc) { 
			return false;
		}
	}
	
	private String decodeHighSurrogate(String upperBits) {
		int higherDigit = Integer.parseInt(upperBits.substring(0, 2), 2);
		String higherDigitAsHex = Integer.toHexString(higherDigit);
		
		int middleDigit = Integer.parseInt(upperBits.substring(2, 6), 2);
		String middleDigitAsHex = Integer.toHexString(middleDigit);
		
		int lowerDigit = Integer.parseInt(upperBits.substring(6), 2);
		String lowerDigitAsHex = Integer.toHexString(lowerDigit);
		
		String hex = higherDigitAsHex + middleDigitAsHex + lowerDigitAsHex;
		return Integer.toHexString(0xD800 + Integer.parseInt(hex, 16));
	}
	
	private String decodeLowerSurrogate(String lowerBits) {
		int higherDigit = Integer.parseInt(lowerBits.substring(0, 2), 2);
		String higherDigitAsHex = Integer.toHexString(higherDigit);
		
		int middleDigit = Integer.parseInt(lowerBits.substring(2, 6), 2);
		String middleDigitAsHex = Integer.toHexString(middleDigit);
		
		int lowerDigit = Integer.parseInt(lowerBits.substring(6), 2);
		String lowerDigitAsHex = Integer.toHexString(lowerDigit);
		
		String hex = higherDigitAsHex + middleDigitAsHex + lowerDigitAsHex;
		return Integer.toHexString(0xDC00 + Integer.parseInt(hex, 16));
	}
}
