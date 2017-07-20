package com.lorenzo.marco.database.redis;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.*;

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
		assertEquals("Marco", listaNick.get(2));
		assertEquals("Vignini", listaNick.get(1));
		assertEquals("pass", listaNick.get(0));
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
		this.redisDatabaseWrapper.login("Lorenzo", "java");
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
		listaNickname.add("nick1");
		listaNickname.add("nick2");
		assertEquals(listaNickname, this.redisDatabaseWrapper.restituzioneNickname());
	}

	@Test
	public void testRestituzioneProfiloCliente() throws UnknownHostException {
		this.redisDatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "nick1", "pass");
		List<String> listaValori = new ArrayList<>();
		listaValori.add("pass");
		listaValori.add("Rossi");
		listaValori.add("Alessio");
		assertEquals(listaValori, this.redisDatabaseWrapper.restituzioneProfiloCliente("nick1"));
	}
	
	private List<String> profiloCliente(String nickname) {
		List<String> listaNick = this.jedis.lrange(nickname, 0, 2);
		return listaNick;
	}

}
