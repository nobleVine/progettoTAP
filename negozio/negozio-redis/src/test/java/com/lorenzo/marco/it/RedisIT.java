package com.lorenzo.marco.it;

import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class RedisIT extends DatabaseIT {

	private Jedis jedis;

	@Override
	public void inizializzazioneDatabase() {
		this.jedis = new Jedis("localhost", 6379);
		database = new RedisDatabaseWrapper(jedis);
		this.jedis.flushDB();
	}

}
