package com.lorenzo.marco.database.redis;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.*;

import com.lorenzo.marco.cliente.Cliente;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisDatabaseWrapperTest {

	private RedisDatabaseWrapper redisDatabaseWrapper;
	private Jedis jedis;

	@Before
	public void setUp() {
		this.jedis = new Jedis("localhost", 6379);
		this.redisDatabaseWrapper = new RedisDatabaseWrapper();
		this.jedis.flushDB();
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
	public void testRegistrazioneAvvenutaConSuccesso() {
		Cliente cliente = new Cliente("Marco", "Vignini", "nick", "pass", redisDatabaseWrapper);
		assertEquals("Registrazione riuscita", this.redisDatabaseWrapper.aggiungiCliente(cliente, "nick", "pass"));
		List<String> listaNick = profiloCliente("nick");
		assertEquals(cliente.getNome(), listaNick.get(2));
		assertEquals(cliente.getCognome(), listaNick.get(1));
		assertEquals("pass", listaNick.get(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneSbagliata() {
		Cliente cliente = new Cliente("Marco", "Vignini", "nick", "pass", redisDatabaseWrapper);
		Cliente cliente2 = new Cliente("Marco2", "Vignini2", "nick", "pass1", redisDatabaseWrapper);
		this.redisDatabaseWrapper.aggiungiCliente(cliente, "nick", "pass");
		this.redisDatabaseWrapper.aggiungiCliente(cliente2, "nick", "pass1");
	}

	@Test
	public void testLoginCorretto() throws UnknownHostException {
		Cliente cliente = new Cliente("Marco", "Vignini", "nick", "pass", redisDatabaseWrapper);
		this.redisDatabaseWrapper.aggiungiCliente(cliente, "nick", "pass");
		assertEquals("Login riuscito", this.redisDatabaseWrapper.login("nick", "pass"));	
	}

	private List<String> profiloCliente(String nickname) {
		List<String> listaNick = this.jedis.lrange(nickname, 0, 2);
		return listaNick;
	}

}
