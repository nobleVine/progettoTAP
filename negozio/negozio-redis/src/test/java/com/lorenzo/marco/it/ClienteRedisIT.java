package com.lorenzo.marco.it;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.*;

import org.junit.*;

import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class ClienteRedisIT {

	private Database database;
	private Jedis jedis;
	private Cliente cliente;
	
	@Before
	public void setUp() {
		database = new RedisDatabaseWrapper();
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB();
		cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
	}
	
	@Test
	public void testRegistrazioneConSuccesso() throws UnknownHostException {
		assertEquals("Registrazione riuscita", cliente.richiestaRegistrazione());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRegistrazioneSenzaSuccesso() throws UnknownHostException {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname", "pass", database);
		assertEquals("Registrazione riuscita", cliente.richiestaRegistrazione());
		cliente1.richiestaRegistrazione();		
	}
	
	@Test
	public void testLoginRiuscito() throws UnknownHostException {
		this.cliente.richiestaRegistrazione();
		assertEquals("Login riuscito", this.cliente.richiestaAutenticazione("nickname", "password"));
	}
	
	@Test(expected = IllegalAccessError.class)
	public void testNicknameErrato() throws UnknownHostException {
		this.cliente.richiestaRegistrazione();
		this.cliente.richiestaAutenticazione("nick", "password");
	}
	
	@Test(expected = IllegalAccessError.class)
	public void testPasswordSbagliata() throws UnknownHostException {
		this.cliente.richiestaRegistrazione();
		this.cliente.richiestaAutenticazione("nickname", "pass");
	}
	
	@Test
	public void testNicknameRestiuiti() throws UnknownHostException {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname2", "pass", database);
		this.cliente.richiestaRegistrazione();
		cliente1.richiestaRegistrazione();
		List<String> listaNickname = new ArrayList<>();
		listaNickname.add("nickname");
		listaNickname.add("nickname2");
		assertEquals(listaNickname, cliente.restituzioneNicknamePresentiNelDatabase());
	}
	
	@Test
	public void testProfiloClienteRestituito() throws UnknownHostException {
		this.cliente.richiestaRegistrazione();
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Marco");
		listaValori.add("Vignini");
		listaValori.add("password");
		assertEquals(listaValori, cliente.restituzioneProfiloClientePresenteNelDatabase());
	}	

}
