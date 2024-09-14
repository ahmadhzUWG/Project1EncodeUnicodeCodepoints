package edu.westga.cs3110.unicoder.model;

/**
 * Represents a Unicode codepoint
 * 
 * @author ahmad
 * @version Fall 2024
 * 
 */
public class Codepoint {

	private String hexString;
	
	/**
	 * Creates a codepoint with the given hexadecimal string
	 * 
	 * @precondition hexString != null && !hexString.isBlank()
	 * @postcondition codepoint is created
	 * 
	 * @param hexString the hexadecimal string associated with the codepoint
	 */
	public Codepoint(String hexString) {
		if (hexString == null) {
			throw new IllegalArgumentException("Hexadecimal string cannot be null");
		}
		if (hexString.isBlank()) {
			throw new IllegalArgumentException("Hexadecimal string cannot be empty");
		}
		
		this.hexString = hexString;
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
}
