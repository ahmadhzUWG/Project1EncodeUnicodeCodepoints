package edu.westga.cs3110.unicoder.test.codepoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestToUTF32 {

	@Test
	void testWhenStringIsFourCharacters() {
		Codepoint ex = new Codepoint("0F15");
		
		TestHelper.assertEquals("00000f15", ex.toUTF32());
	}

	@Test
	void testWhenStringIsFiveCharacters() {
		Codepoint ex = new Codepoint("DFF15");
		
		TestHelper.assertEquals("000dfF15", ex.toUTF32());
	}
	
	@Test
	void testWhenStringIsSixCharacters() {
		Codepoint ex = new Codepoint("10000F");
		
		TestHelper.assertEquals("0010000F", ex.toUTF32());
	}
} 
