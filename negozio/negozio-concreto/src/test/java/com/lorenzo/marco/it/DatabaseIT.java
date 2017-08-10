package com.lorenzo.marco.it;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.lorenzo.marco.amministratore.Amministratore;
import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.Database;

public abstract class DatabaseIT  {

	protected Database database;
	protected Cliente cliente;
	protected Amministratore amministratore;
	
	private String esitoRegistrazione;

	@Before
	public void setUp() {
		inizializzazioneDatabase();
		this.cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
		this.amministratore = new Amministratore(database);
		esitoRegistrazione = this.cliente.richiestaRegistrazione();
	}
	
	/*Integration test Amministratore*/
	
	@Test
	public void testNicknameRestituiti() {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname2", "pass", database);
		cliente1.richiestaRegistrazione();
		Set<String> listaNickname = new HashSet<>();
		listaNickname.add("nickname");
		listaNickname.add("nickname2");
		assertEquals(listaNickname, amministratore.restituzioneListaNickname());
	}
	
	@Test
	public void testProfiloClienteRestituito() {
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Marco");
		listaValori.add("Vignini");
		listaValori.add("password");
		assertEquals(listaValori, amministratore.restituzioneListaCampiCliente("nickname"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testProfiloClienteRestituitoSenzaSuccesso() {
		this.amministratore.restituzioneListaCampiCliente("nick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreazioneListaAcquistiClienteSenzaSuccesso() {
		List<String> listaAcquisti = new ArrayList<>();
		this.amministratore.creazioneAcquisti("nick", listaAcquisti);
	}
	
	@Test
	public void testRestituzioneAcquistiCliente() {
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta");
		listaAcquisti.add("Calzini");
		assertEquals("Lista creata con successo", this.amministratore.creazioneAcquisti("nickname", listaAcquisti));
		assertEquals(listaAcquisti, amministratore.restituzioneListaAcquistiCliente("nickname"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneAcquistiClienteSenzaSuccesso() {
		amministratore.restituzioneListaAcquistiCliente("nick");
	}
	
	/*Integration test Cliente*/
	
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
	
	protected abstract void inizializzazioneDatabase();

}
