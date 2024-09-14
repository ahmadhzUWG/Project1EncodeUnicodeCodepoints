package edu.westga.cs3110.unicoder.test.codepoint;

import static org.junit.jupiter.api.Assertions.*;

class TestHelper {

	/**
	 * Case insensitive assert equals method for strings only
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param expected the expected string
	 * @param actual the actual string
	 */
	public static void assertEquals(String expected, String actual) {
		if (expected == null && actual == null) { 
			return;
		}
		if (expected == null) {
			fail("Expected string is null and actual is: " + actual);
		}
		if (actual == null) {
			fail("Expected string is: " + expected + " and actual is null.");
		}
		if (!expected.equalsIgnoreCase(actual)) {
			fail("Expected: " + expected + " | but was: " + actual);
		}
	}
}
