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
	 * @return
	 */
	public String toUTF32() {
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
