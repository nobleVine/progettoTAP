package com.lorenzo.marco.database.redis;

import java.net.UnknownHostException;
import java.util.*;

import com.lorenzo.marco.database.Database;

import redis.clients.jedis.Jedis;

public class RedisDatabaseWrapper implements Database {

	private Jedis jedis;
	private List<String> listaNickname;
	private List<String> listaCampi;
	
	public RedisDatabaseWrapper() {
		jedis = new Jedis();
		this.listaNickname = new ArrayList<>();
		listaCampi = new ArrayList<>();
	}

	@Override
	public String login(String nickname, String password) throws UnknownHostException {
		if (!this.jedis.exists(nickname))
			throw new IllegalAccessError("Nickname non registrato!");
		//List<String> listaCampi = this.jedis.lrange(nickname, 0, 2);
		if(this.listaCampi.get(2).equals(password))
			return "Login riuscito";
		throw new IllegalAccessError("Password errata!");
	}
	
	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password) throws UnknownHostException {
		if (this.jedis.exists(nickname))
			throw new IllegalArgumentException("Registrazione fallita: nickname già in uso!");
		this.jedis.rpush(nickname, nome);
		this.jedis.rpush(nickname, cognome);
		this.jedis.rpush(nickname, password);
		this.listaNickname.add(nickname);
		this.listaCampi.add(nome);
		this.listaCampi.add(cognome);
		this.listaCampi.add(password);
		return "Registrazione riuscita";
	}

	public List<String> restituzioneNickname() {
		return this.listaNickname;
	}

	public List<String> restituzioneProfiloCliente(String chiave) {
		return this.jedis.lrange(chiave, 0, this.listaCampi.size());
	}
	
}
