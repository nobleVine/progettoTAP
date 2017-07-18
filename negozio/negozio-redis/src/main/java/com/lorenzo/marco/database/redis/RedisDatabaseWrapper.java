package com.lorenzo.marco.database.redis;

import java.net.UnknownHostException;
import java.util.List;

import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.Database;

import redis.clients.jedis.Jedis;

public class RedisDatabaseWrapper implements Database {

	private Jedis jedis;

	public RedisDatabaseWrapper() {
		jedis = new Jedis();
	}

	@Override
	public String registrazioneCliente(String name, String cognome) throws UnknownHostException {
		return null;
	}

	@Override
	public String login(String nickname, String password) throws UnknownHostException {
		if (!this.jedis.exists(nickname))
			throw new IllegalAccessError("Nickname non registrato!");
		List<String> l = this.jedis.lrange(nickname, 0, 0);
		if(l.get(0).equals(password))
			return "Login riuscito";
		throw new IllegalAccessError("Password errata!");
	}
	
	public String aggiungiCliente(Cliente cliente, String nickname, String password) {
		if (this.jedis.exists(nickname))
			throw new IllegalArgumentException("Registrazione fallita: nickname già in uso!");
		this.jedis.lpush(nickname, cliente.getNome());
		this.jedis.lpush(nickname, cliente.getCognome());
		this.jedis.lpush(nickname, password);
		return "Registrazione riuscita";

	}

}
