package com.lorenzo.marco.database.redis;

import java.net.UnknownHostException;
import java.util.*;

import com.lorenzo.marco.database.DatabaseLatoCliente;
import com.lorenzo.marco.database.DatabaseLatoAmministratore;

import redis.clients.jedis.Jedis;

public class RedisDatabaseWrapper implements DatabaseLatoCliente, DatabaseLatoAmministratore {

	private Jedis jedis;
	private Map<String, List<String>> campiClienti;
	private int numeroAcquisti;

	public RedisDatabaseWrapper() {
		jedis = new Jedis();
		this.campiClienti = new HashMap<>();
	}

	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password)
			throws UnknownHostException {
		if (this.jedis.exists(nickname))
			throw new IllegalArgumentException("Registrazione fallita: nickname già in uso!");
		this.jedis.rpush(nickname, nome);
		this.jedis.rpush(nickname, cognome);
		this.jedis.rpush(nickname, password);
		List<String> listaCampi = new ArrayList<>();
		listaCampi.add(nome);
		listaCampi.add(cognome);
		listaCampi.add(password);
		this.campiClienti.put(nickname, listaCampi);
		return "Registrazione riuscita";
	}

	@Override
	public String login(String nickname, String password) throws UnknownHostException {
		if (!this.jedis.exists(nickname))
			throw new IllegalAccessError("Nickname non registrato!");
		if (this.campiClienti.get(nickname).get(2).equals(password))
			return "Login riuscito";
		throw new IllegalAccessError("Password errata!");
	}

	public Set<String> restituzioneNickname() {
		return this.jedis.keys("*");
	}

	public List<String> restituzioneProfiloCliente(String nickname) {
		if (this.jedis.exists(nickname))
			return this.jedis.lrange(nickname, 0, this.campiClienti.get(nickname).size());
		throw new IllegalArgumentException("Nickname non esistente");
	}

	public void creaListaAcquisti(String nickname, List<String> listaAcquisti) {
		for (String acquisto : listaAcquisti) {
			this.jedis.rpush(nickname, acquisto);
		}
		numeroAcquisti = listaAcquisti.size();
	}

	public List<String> restituzioneAcquistiCliente(String nickname) {
		return this.jedis.lrange(nickname, 3, numeroAcquisti + 3l);
	}

}
