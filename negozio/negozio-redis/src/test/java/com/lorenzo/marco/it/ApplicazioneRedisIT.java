package com.lorenzo.marco.it;

import org.junit.Test;

import com.lorenzo.marco.main.MainRedis;

public class ApplicazioneRedisIT implements ApplicazioneIT {

	@Test
	public void testApplicazioneIT() {
		MainRedis.main(null);
	}
	
}
