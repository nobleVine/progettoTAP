package com.lorenzo.marco.main;

import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class Main {

	public static void main(String[] args) {
		
		String redisHost = "localhost";
		
		if (args.length > 0) {
			redisHost = args[0];
		}
		
		Jedis jedis = new Jedis(redisHost, 6379);
		Database database = new RedisDatabaseWrapper(jedis);
		Applicazione applicazione = new Applicazione(database);
		applicazione.eseguiApplicazione();		
		
	}

}
