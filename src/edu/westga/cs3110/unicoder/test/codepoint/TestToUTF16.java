package edu.westga.cs3110.unicoder.test.codepoint;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestToUTF16 {

	@Test
	void testLowerBoundaryOfTheFirst2ByteRange() {
		Codepoint ex = new Codepoint("0000");
		
		TestHelper.assertEquals("0000", ex.toUTF16());
	}
	
	@Test
	void testUpperBoundaryOfTheFirst2ByteRange() {
		Codepoint ex = new Codepoint("D7FF");
		
		TestHelper.assertEquals("d7ff", ex.toUTF16());
	}

	@Test
	void testCodepointOfTheFirst2ByteRange() {
		Codepoint ex = new Codepoint("c100");
		
		TestHelper.assertEquals("C100", ex.toUTF16());
	}
	
	@Test
	void testLowerBoundaryOfTheSecond2ByteRange() {
		Codepoint ex = new Codepoint("E000");
		
		TestHelper.assertEquals("e000", ex.toUTF16());
	}
	
	@Test
	void testUpperBoundaryOfTheSecond2ByteRange() {
		Codepoint ex = new Codepoint("fffF");
		
		TestHelper.assertEquals("fFfF", ex.toUTF16());
	}
	
	@Test
	void testCodepointOfTheSecond2ByteRange() {
		Codepoint ex = new Codepoint("F1CC");
		
		TestHelper.assertEquals("F1CC", ex.toUTF16());
	}
	
	@Test
	void testLowerBoundaryOfTheInvalidByteRange() {
		Codepoint ex = new Codepoint("D800");
		
		assertNull(ex.toUTF16());
	}
	
	@Test
	void testCodepointOfTheInvalidByteRange() {
		Codepoint ex = new Codepoint("D911");
		
		assertNull(ex.toUTF16());
	}
	
	@Test
	void testUpperBoundaryOfTheInvalidByteRange() {
		Codepoint ex = new Codepoint("DFFF");
		
		assertNull(ex.toUTF16());
	}
	
	@Test
	void testLowerBoundaryOfThe4ByteRange() {
		Codepoint ex = new Codepoint("10000");
		
		TestHelper.assertEquals("D800DC00", ex.toUTF16());
	}
	
	@Test
	void testCodepointOfThe4ByteRange() {
		Codepoint ex = new Codepoint("1FFFF");
		
		TestHelper.assertEquals("D83FDFFF", ex.toUTF16());
	}
	
	@Test
	void testUpperBoundaryOfThe4ByteRange() {
		Codepoint ex = new Codepoint("10FFFF");
		
		TestHelper.assertEquals("DBFFDFFF", ex.toUTF16());
	}
}
