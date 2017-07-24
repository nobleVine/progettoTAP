package com.lorenzo.marco.it;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

import redis.clients.jedis.Jedis;

public class AmministratoreRedisIT extends AmministratoreIT {

	private Jedis jedis;
	
	@Override
	protected void inizializzazioneDatabase() {
		database = new RedisDatabaseWrapper();
		this.jedis = new Jedis("localhost", 6379);
		this.jedis.flushDB();
	}

	@Test
	public void testNicknameRestiuiti() throws UnknownHostException {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname2", "pass", database);
		this.cliente.richiestaRegistrazione();
		cliente1.richiestaRegistrazione();
		List<String> listaNickname = new ArrayList<>();
		listaNickname.add("nickname");
		listaNickname.add("nickname2");
		assertEquals(listaNickname, amministratore.restituzioneListaNickname());
	}
	
	@Test
	public void testProfiloClienteRestituito() throws UnknownHostException {
		this.cliente.richiestaRegistrazione();
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Marco");
		listaValori.add("Vignini");
		listaValori.add("password");
		assertEquals(listaValori, amministratore.restituzioneListaCampiCliente("nickname"));
	}

}
