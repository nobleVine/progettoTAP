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
	
	@Before
	public void setUp() {
		inizializzazioneDatabase();		
		cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
	}
	
	@Test
	public void testRegistrazioneConSuccesso() throws UnknownHostException {
		assertEquals("Registrazione riuscita", this.cliente.richiestaRegistrazione());
	}

	@Test(expected = IllegalArgumentException.class)
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
	
	//protected abstract void inizializzazioneDatabase();		

}
