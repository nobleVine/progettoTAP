package com.lorenzo.marco.it;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.*;

import org.junit.*;

import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class ClienteRedisIT extends ClienteIT {

	private Jedis jedis;
	
	/*@Test
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
	}*/

	@Override
	protected void inizializzazioneDatabase() {
		database = new RedisDatabaseWrapper();
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB();
	}

}
