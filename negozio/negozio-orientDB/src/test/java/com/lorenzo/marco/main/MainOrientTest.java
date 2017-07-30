package com.lorenzo.marco.main;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MainOrientTest {

	@Test
	public void testMain() {
		MainOrient.main(null);
	}
	
	@Test
	public void testMainInstanziazione() {
		MainOrient main = new MainOrient();
		assertNotNull(main);
	}
}