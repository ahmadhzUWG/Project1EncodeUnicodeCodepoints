package edu.westga.cs3110.unicoder.test.codepoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestConstructor {

	public static void assertEquals(String expected, String actual) {
		if (expected == null && actual == null) {
			return;
		}
		if (expected == null) {
			fail("Expected string is null and actual is not.");
		}
		if (actual == null) {
			fail("Expected string is not null and actual is null.");
		}
		if (!expected.equalsIgnoreCase(actual)) {
			fail("Expected: " + expected + " | but was: " + actual);
		}
	}
	
	@Test
	void testWhenNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Codepoint(null);
		});
	}
	
	@Test
	void testWhenBlank() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Codepoint(" ");
		});
	}
	
	@Test
	void testTopBoundaryInvalidCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Codepoint("9999999");
		});
	}
	
	@Test
	void testLowBoundaryInvalidCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Codepoint("-000000001");
		});
	}
	
	@Test
	void testInvalidCodepointOfSpecialCharacters() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Codepoint("-----------");
		});
	}
	
	@Test 
	void testValidCodepoint() {
		Codepoint train = new Codepoint("1F682");
		
		assertEquals("1f682", train.getHexString());  
	}

}
