package edu.westga.cs3110.unicoder.test.codepoint;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestConstructor {
	
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
		
		TestHelper.assertEquals("1f682", train.getHexString());  
	}

}
