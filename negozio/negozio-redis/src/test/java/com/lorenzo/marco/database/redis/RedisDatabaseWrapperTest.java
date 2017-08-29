package com.lorenzo.marco.database.redis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.lorenzo.marco.database.DatabaseLatoAmministratoreTest;
import com.lorenzo.marco.database.DatabaseLatoClienteTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisDatabaseWrapperTest implements DatabaseLatoAmministratoreTest, DatabaseLatoClienteTest {

	private RedisDatabaseWrapper redisDatabaseWrapper;
	private Jedis jedis;
	int contatoreChiamate = 0;

	@Before
	public void setUp() {
		this.jedis = new Jedis("localhost", 6379);
		this.redisDatabaseWrapper = new RedisDatabaseWrapper(jedis);
		testNessunoRegistrato();
		this.jedis.flushDB();
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
	public void testNessunoRegistrato() {
		assertEquals(contatoreChiamate, jedis.keys("*").size(), 0);
		contatoreChiamate++;
	}
	
	/*Cliente*/

	@Test
	public void testRegistrazioneClienteConSuccesso() {
		assertEquals("Registrazione riuscita", this.redisDatabaseWrapper.registrazioneCliente("Marco", "Vignini", "nick1", "pass"));
		List<String> listaNick = profiloCliente("nick1");
		assertEquals("Marco", listaNick.get(0));
		assertEquals("Vignini", listaNick.get(1));
		assertEquals("pass", listaNick.get(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneClienteSenzaSuccesso() {
		this.redisDatabaseWrapper.registrazioneCliente("Alberto", "Verdi", "nick", "pass1");
	}
	
	@Override
	public void testRegistrazioneClientiConSuccesso() {
		assertEquals("Registrazione riuscita", this.redisDatabaseWrapper.registrazioneCliente("Marco", "Vignini", "nick1", "pass"));
		assertEquals(2, this.jedis.keys("*").size(), 0);
	}

	@Test
	public void testLoginConSuccesso() {
		assertEquals("Login riuscito", this.redisDatabaseWrapper.login("nick", "pass"));
	}

	@Test(expected = IllegalAccessError.class)
	public void testLoginNicknameNonRegistrato() {
		this.redisDatabaseWrapper.login("Lorenzo", "pass");
	}

	@Test(expected = IllegalAccessError.class)
	public void testLoginPasswordErrata() {
		this.redisDatabaseWrapper.login("nick", "passwordErrata");
	}
	
	@Test(expected = IllegalAccessError.class)
	public void testLoginCredenzialiSbagliate() {
		this.redisDatabaseWrapper.login("vigna2", "pass2");
	}
	
	/*Amministratore*/

	@Test
	public void testRestituzioneNickname() {
		this.redisDatabaseWrapper.registrazioneCliente("Ugo", "Rolando", "nick2", "pass2");
		Set<String> listaNickname = new HashSet<>();
		listaNickname.add("nick2");
		listaNickname.add("nick");
		assertEquals(listaNickname, this.redisDatabaseWrapper.restituzioneNickname());
	}

	@Test
	public void testRestituzioneProfiloClienteConSuccesso() {
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Alessio");
		listaValori.add("Rossi");
		listaValori.add("pass");
		assertEquals(listaValori, this.redisDatabaseWrapper.restituzioneProfiloCliente("nick"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneProfiloClienteSenzaSuccesso() {
		this.redisDatabaseWrapper.restituzioneProfiloCliente("nick1");
	}
	
	@Override
	public void testRestituzioneAcquistiClienteConSuccesso() {
		assertRestituzioneAcquistiClienti("Giovanni", "Bianchi", "nick3", "pass3");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneAcquistiClienteSenzaSuccesso() {
		this.redisDatabaseWrapper.restituzioneAcquistiCliente("nickNonEsistente");
	}
	
	@Test
	public void testRestituzioneAcquistiClientiConSuccesso() {
		assertRestituzioneAcquistiClienti("Alessio2", "Rossi2", "nick2", "pass2");
		assertRestituzioneAcquistiClienti("Alessio1", "Rossi1", "nick1", "pass1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneListaAcquistiClienteSenzaSuccesso() {
		List<String> listaAcquisti = new ArrayList<>();
		this.redisDatabaseWrapper.registrazioneListaAcquistiCliente("nickNonEsistente", listaAcquisti);
	}
		
	private void assertRestituzioneAcquistiClienti(String nome, String cognome, String nickname, String password) {
		this.redisDatabaseWrapper.registrazioneCliente(nome, cognome, nickname, password);
		this.redisDatabaseWrapper.login(nickname, password);
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta1");
		listaAcquisti.add("Maglietta2");
		assertEquals("Lista creata con successo",this.redisDatabaseWrapper.registrazioneListaAcquistiCliente(nickname, listaAcquisti));
		assertEquals(listaAcquisti, this.redisDatabaseWrapper.restituzioneAcquistiCliente(nickname));
	}
	
	private List<String> profiloCliente(String nickname) {
		List<String> listaNick = this.jedis.lrange(nickname, 0, 2);
		return listaNick;
	}

}
