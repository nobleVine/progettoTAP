package com.lorenzo.marco.database.redis;

import java.util.*;

import com.lorenzo.marco.database.DatabaseLatoCliente;
import com.lorenzo.marco.database.DatabaseLatoAmministratore;

import redis.clients.jedis.Jedis;

public class RedisDatabaseWrapper implements DatabaseLatoCliente, DatabaseLatoAmministratore {

	private static final String NICKNAME_NON_ESISTENTE = "Nickname non esistente";
	private Jedis jedis;
	private Map<String, List<String>> campiClienti;
	private long numeroAcquisti;

	public RedisDatabaseWrapper(Jedis jedis) {
		this.jedis = jedis;
		this.campiClienti = new HashMap<>();
		this.jedis.flushDB();
	}

	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password) {
		if (this.jedis.exists(nickname))
			throw new IllegalArgumentException("Registrazione fallita: nickname già in uso!");
		this.jedis.rpush(nickname, nome);
		this.jedis.rpush(nickname, cognome);
		this.jedis.rpush(nickname, password);
		memorizzazioneCampiCliente(nome, cognome, nickname, password);
		return "Registrazione riuscita";
	}

	@Override
	public String login(String nickname, String password) {
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
		throw new IllegalArgumentException(NICKNAME_NON_ESISTENTE);
	}

	public String registrazioneListaAcquistiCliente(String nickname, List<String> listaAcquisti) {
		if (this.jedis.exists(nickname)) {
			for (String acquisto : listaAcquisti) {
				this.jedis.rpush(nickname, acquisto);
			}
			this.numeroAcquisti = listaAcquisti.size();
			return "Lista creata con successo";
		} else {
			throw new IllegalArgumentException(NICKNAME_NON_ESISTENTE);
		}
	}

	public List<String> restituzioneAcquistiCliente(String nickname) {
		if (this.jedis.exists(nickname)) {
			long numeroValoriNickname = this.jedis.llen(nickname);
			return this.jedis.lrange(nickname, numeroValoriNickname - this.numeroAcquisti, numeroValoriNickname);
		} else {
			throw new IllegalArgumentException(NICKNAME_NON_ESISTENTE);
		}
	}

	private void memorizzazioneCampiCliente(String nome, String cognome, String nickname, String password) {
		List<String> listaCampi = new ArrayList<>();
		listaCampi.add(nome);
		listaCampi.add(cognome);
		listaCampi.add(password);
		this.campiClienti.put(nickname, listaCampi);
	}

}
