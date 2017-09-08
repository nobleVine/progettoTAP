package com.lorenzo.marco.main;

import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class DimostrazioneApplicazioneRedis extends DimostrazioneApplicazione {

	@Override
	protected void inizializzazioneDatabaseSpecifico() {
		String redisHost = "localhost";
		this.database = new RedisDatabaseWrapper(new Jedis(redisHost));
	}
	
}
