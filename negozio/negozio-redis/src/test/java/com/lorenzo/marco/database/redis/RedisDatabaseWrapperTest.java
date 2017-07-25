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
	public void setUp() {
		this.redisDatabaseWrapper = new RedisDatabaseWrapper();
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB(); // Clean key
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
		assertEquals("Registrazione riuscita",
				this.redisDatabaseWrapper.registrazioneCliente("Marco", "Vignini", "nick", "pass"));
		List<String> listaNick = profiloCliente("nick");
		assertEquals("Marco", listaNick.get(0));
		assertEquals("Vignini", listaNick.get(1));
		assertEquals("pass", listaNick.get(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneSbagliata() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick", "pass");
		this.redisDatabaseWrapper.registrazioneCliente("Alberto", "Verdi", "nick", "pass1");
	}

	@Test
	public void testLoginCorretto() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick", "pass");
		assertEquals("Login riuscito", this.redisDatabaseWrapper.login("nick", "pass"));
	}

	@Test(expected = IllegalAccessError.class)
	public void testNicknameNonRegistrato() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick", "pass");
		this.redisDatabaseWrapper.login("Lorenzo", "pass");
	}

	@Test(expected = IllegalAccessError.class)
	public void testPasswordErrata() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick", "pass");
		this.redisDatabaseWrapper.login("nick", "java");
	}

	@Test
	public void testRestituzioneNickname() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick1", "pass");
		this.redisDatabaseWrapper.registrazioneCliente("Ugo", "Rolando", "nick2", "pass2");
		Set<String> listaNickname = new HashSet<>();
		listaNickname.add("nick2");
		listaNickname.add("nick1");
		assertEquals(listaNickname, this.redisDatabaseWrapper.restituzioneNickname());
	}

	@Test
	public void testRestituzioneProfiloClienteConSuccesso() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick1", "pass");
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Alessio");
		listaValori.add("Rossi");
		listaValori.add("pass");
		assertEquals(listaValori, this.redisDatabaseWrapper.restituzioneProfiloCliente("nick1"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneProfiloClienteSenzaSuccesso() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick1", "pass");
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Alessio");
		listaValori.add("Rossi");
		listaValori.add("pass");
		assertEquals(listaValori, this.redisDatabaseWrapper.restituzioneProfiloCliente("nick"));
	}
	
	@Test
	public void testRestituzioneAcquistiCliente() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick", "pass");
		this.redisDatabaseWrapper.login("nick", "pass");
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta Kobe");
		listaAcquisti.add("Maglietta Stephen");
		this.redisDatabaseWrapper.creaListaAcquisti("nick", listaAcquisti);
		assertEquals(listaAcquisti, this.redisDatabaseWrapper.restituzioneAcquistiCliente("nick"));
		this.redisDatabaseWrapper.registrazioneCliente("Alessandro", "Verdi", "nickname", "pass1");
		this.redisDatabaseWrapper.login("nickname", "pass1");
		List<String> listaAcquisti2 = new ArrayList<>();
		listaAcquisti2.add("Maglietta Naso");
		listaAcquisti2.add("Maglietta Stephen Curry");
		this.redisDatabaseWrapper.creaListaAcquisti("nickname", listaAcquisti2);
		assertEquals(listaAcquisti2, this.redisDatabaseWrapper.restituzioneAcquistiCliente("nickname"));	
	}
	
	private List<String> profiloCliente(String nickname) {
		List<String> listaNick = this.jedis.lrange(nickname, 0, 2);
		return listaNick;
	}

}
