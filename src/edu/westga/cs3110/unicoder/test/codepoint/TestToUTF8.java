package edu.westga.cs3110.unicoder.test.codepoint;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestToUTF8 {

	@Test
	void testLowerBoundaryOfThe1ByteRange() {
		Codepoint ex = new Codepoint("0000");
		
		TestHelper.assertEquals("00", ex.toUTF8());
	}
	
	@Test
	void testUpperBoundaryOfThe1ByteRange() {
		Codepoint ex = new Codepoint("007F");
		
		TestHelper.assertEquals("7f", ex.toUTF8());
	}
	
	@Test
	void testCodepointOfThe1ByteRange() {
		Codepoint ex = new Codepoint("005a");
		
		TestHelper.assertEquals("5a", ex.toUTF8());
	}
	
	@Test
	void testLowerBoundaryOfThe2ByteRange() {
		Codepoint ex = new Codepoint("0080");
		
		TestHelper.assertEquals("C280", ex.toUTF8());
	}
	
	@Test
	void testUpperBoundaryOfThe2ByteRange() {
		Codepoint ex = new Codepoint("07FF");
		
		TestHelper.assertEquals("DFBf", ex.toUTF8());
	}
	
	@Test
	void testCodepointOfThe2ByteRange() {
		Codepoint ex = new Codepoint("03FA");
		
		TestHelper.assertEquals("CFBA", ex.toUTF8());
	}
	
	@Test
	void testLowerBoundaryOfThe3ByteRange() {
		Codepoint ex = new Codepoint("0800");
		
		TestHelper.assertEquals("E0A080", ex.toUTF8());
	}
	
	@Test
	void testUpperBoundaryOfThe3ByteRange() {
		Codepoint ex = new Codepoint("FFFF");
		
		TestHelper.assertEquals("EFBFBF", ex.toUTF8());
	}
	
	@Test
	void testCodepointOfThe3ByteRange() {
		Codepoint ex = new Codepoint("34AA");
		
		TestHelper.assertEquals("E392AA", ex.toUTF8());
	}
	
	@Test
	void testLowerBoundaryOfThe4ByteRange() {
		Codepoint ex = new Codepoint("10000");
		
		TestHelper.assertEquals("f0908080", ex.toUTF8());
	}

	@Test
	void testUpperBoundaryOfThe4ByteRange() {
		Codepoint ex = new Codepoint("10FFFF");
		
		TestHelper.assertEquals("F48fbfBF", ex.toUTF8());
	}
	
	@Test
	void testCodepointOfThe4ByteRange() {
		Codepoint ex = new Codepoint("1888A");
		
		TestHelper.assertEquals("F098A28A", ex.toUTF8());	
	}
}
