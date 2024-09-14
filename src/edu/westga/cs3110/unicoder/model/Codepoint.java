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
	 * @precondition hexString != null && !hexString.isBlank()
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
		throw new UnsupportedOperationException(); 
	}
	
	/**
	 * Returns the codepoint in UTF-16 encoding, as either a
	 * 4-digit or 8-digit hexadecimal string (as appropriate), without spaces or 0x prefix
	 * 
	 * @precondition none
	 * @postcondition none
	 *  
	 * @return the codepoint as a 2-byte/4-byte (as appropriate) UTF-16 encoded string, without spaces or 0x prefix
	 */
	public String toUTF16() {
		throw new UnsupportedOperationException(); 
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
}
