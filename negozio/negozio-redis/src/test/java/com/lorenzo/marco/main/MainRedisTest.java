package com.lorenzo.marco.main;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MainRedisTest {

	@Test
	public void testMain() {
		MainRedis.main(null);
	}
	
	@Test
	public void testMainInstanziazione() {
		MainRedis main = new MainRedis();
		assertNotNull(main);
	}
}
