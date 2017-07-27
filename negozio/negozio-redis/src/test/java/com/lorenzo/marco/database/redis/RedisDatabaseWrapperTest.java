package com.lorenzo.marco.database.redis;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisDatabaseWrapperTest {

	private RedisDatabaseWrapper redisDatabaseWrapper;
	private Jedis jedis;

	@Before
	public void setUp() throws UnknownHostException {
		this.redisDatabaseWrapper = new RedisDatabaseWrapper(new Jedis("localhost", 6379));
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB(); // Clean key
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick", "pass");
	}

	@Test
	public void testConnessione() {
		assertEquals("PONG", this.jedis.ping());
	}

	@Test(expected = JedisConnectionException.class)
	public void testNoConnessione() {
		this.jedis.quit();
		this.jedis.ping();
	}

	@Test
	public void testRegistrazioneAvvenutaConSuccesso() throws UnknownHostException {
		assertEquals("Registrazione riuscita", this.redisDatabaseWrapper.registrazioneCliente("Marco", "Vignini", "nick1", "pass"));
		List<String> listaNick = profiloCliente("nick1");
		assertEquals("Marco", listaNick.get(0));
		assertEquals("Vignini", listaNick.get(1));
		assertEquals("pass", listaNick.get(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneSbagliata() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alberto", "Verdi", "nick", "pass1");
	}

	@Test
	public void testLoginCorretto() throws UnknownHostException {
		assertEquals("Login riuscito", this.redisDatabaseWrapper.login("nick", "pass"));
	}

	@Test(expected = IllegalAccessError.class)
	public void testNicknameNonRegistrato() throws UnknownHostException {
		this.redisDatabaseWrapper.login("Lorenzo", "pass");
	}

	@Test(expected = IllegalAccessError.class)
	public void testPasswordErrata() throws UnknownHostException {
		this.redisDatabaseWrapper.login("nick", "passwordErrata");
	}

	@Test
	public void testRestituzioneNickname() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Ugo", "Rolando", "nick2", "pass2");
		Set<String> listaNickname = new HashSet<>();
		listaNickname.add("nick2");
		listaNickname.add("nick");
		assertEquals(listaNickname, this.redisDatabaseWrapper.restituzioneNickname());
	}

	@Test
	public void testRestituzioneProfiloClienteConSuccesso() throws UnknownHostException {
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Alessio");
		listaValori.add("Rossi");
		listaValori.add("pass");
		assertEquals(listaValori, this.redisDatabaseWrapper.restituzioneProfiloCliente("nick"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneProfiloClienteSenzaSuccesso() throws UnknownHostException {
		this.redisDatabaseWrapper.restituzioneProfiloCliente("nick1");
	}
	
	@Test
	public void testRestituzioneAcquistiCliente() throws UnknownHostException {
		assertRestituzioneAcquistiClienti("Alessio2", "Rossi2", "nick2", "pass2");
		assertRestituzioneAcquistiClienti("Alessio1", "Rossi1", "nick1", "pass1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneCreazioneListaAcquistiClienteSenzaSuccesso() throws UnknownHostException {
		List<String> listaAcquisti = new ArrayList<>();
		this.redisDatabaseWrapper.creaListaAcquisti("nickNonEsistente", listaAcquisti);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneAcquistiClienteSenzaSuccesso() {
		this.redisDatabaseWrapper.restituzioneAcquistiCliente("nickNonEsistente");
	}

	private void assertRestituzioneAcquistiClienti(String nome, String cognome, String nickname, String password) {
		this.redisDatabaseWrapper.registrazioneCliente(nome, cognome, nickname, password);
		this.redisDatabaseWrapper.login(nickname, password);
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta1");
		listaAcquisti.add("Maglietta2");
		assertEquals("Lista creata con successo",this.redisDatabaseWrapper.creaListaAcquisti(nickname, listaAcquisti));
		assertEquals(listaAcquisti, this.redisDatabaseWrapper.restituzioneAcquistiCliente(nickname));
	}
	
	private List<String> profiloCliente(String nickname) {
		List<String> listaNick = this.jedis.lrange(nickname, 0, 2);
		return listaNick;
	}

}
