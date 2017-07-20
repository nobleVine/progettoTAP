package com.lorenzo.marco.database.redis;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.lorenzo.marco.database.Database;

import redis.clients.jedis.Jedis;

public class RedisDatabaseWrapper implements Database {

	private Jedis jedis;
	
	public RedisDatabaseWrapper() {
		jedis = new Jedis();
	}

	@Override
	public String login(String nickname, String password) throws UnknownHostException {
		if (!this.jedis.exists(nickname))
			throw new IllegalAccessError("Nickname non registrato!");
		List<String> listaCampi = this.jedis.lrange(nickname, 0, 0);
		if(listaCampi.get(0).equals(password))
			return "Login riuscito";
		throw new IllegalAccessError("Password errata!");
	}
	
	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password) throws UnknownHostException {
		if (this.jedis.exists(nickname))
			throw new IllegalArgumentException("Registrazione fallita: nickname già in uso!");
		this.jedis.lpush(nickname, nome);
		this.jedis.lpush(nickname, cognome);
		this.jedis.lpush(nickname, password);
		return "Registrazione riuscita";
	}

	public Set<String> restituzioneNickname() {
		return this.jedis.keys("*");
	}

	public List<String> restituzioneProfiloCliente(String chiave) {
		return this.jedis.lrange(chiave, 0, 2);
	}
}
