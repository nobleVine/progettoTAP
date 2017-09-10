package com.lorenzo.marco.it;

import org.junit.Test;

import com.lorenzo.marco.main.MainRedis;

public class DimostrazioneApplicazioneRedisIT implements DimostrazioneApplicazioneIT {

	@Test
	public void testApplicazioneIT() {
		MainRedis.main(null);
	}

}
