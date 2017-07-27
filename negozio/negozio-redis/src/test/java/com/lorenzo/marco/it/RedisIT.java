package com.lorenzo.marco.it;

import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class RedisIT extends AmministratoreIT implements DatabaseIT {

	private Jedis jedis;

	@Override
	public void inizializzazioneDatabase() {
		database = new RedisDatabaseWrapper(new Jedis("localhost", 6379));
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB();
	}

}
