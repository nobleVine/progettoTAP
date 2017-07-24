package com.lorenzo.marco.it;

import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class AmministratoreRedisIT extends AmministratoreIT {

	private Jedis jedis;

	@Override
	public void inizializzazioneDatabase() {
		database = new RedisDatabaseWrapper();
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB();
	}

}
