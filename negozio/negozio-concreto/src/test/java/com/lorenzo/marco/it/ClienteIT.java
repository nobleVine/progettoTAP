package com.lorenzo.marco.it;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.DatabaseLatoCliente;

public abstract class ClienteIT implements ITI {
	
	protected Cliente cliente;
	protected DatabaseLatoCliente database;
	private String esitoRegistrazione;
	
	@Before
	public void setUp() throws UnknownHostException {
		inizializzazioneDatabase();		
		cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
		esitoRegistrazione = this.cliente.richiestaRegistrazione();
	}
	
	@Test
	public void testRegistrazioneConSuccesso() throws UnknownHostException {
		assertEquals("Registrazione riuscita", esitoRegistrazione);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneSenzaSuccesso() throws UnknownHostException {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname", "pass", database);
		assertEquals("Registrazione riuscita", esitoRegistrazione);
		cliente1.richiestaRegistrazione();
	}

	@Test
	public void testLoginRiuscito() throws UnknownHostException {
		assertEquals("Login riuscito", this.cliente.richiestaAutenticazione("nickname", "password"));
	}

	@Test(expected = IllegalAccessError.class)
	public void testNicknameErrato() throws UnknownHostException {
		this.cliente.richiestaAutenticazione("nick", "password");
	}

	@Test(expected = IllegalAccessError.class)
	public void testPasswordSbagliata() throws UnknownHostException {
		this.cliente.richiestaAutenticazione("nickname", "pass");
	}
	
}
