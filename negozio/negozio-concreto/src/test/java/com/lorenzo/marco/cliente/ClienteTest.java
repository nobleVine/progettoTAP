package com.lorenzo.marco.cliente;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.*;
import static org.mockito.Mockito.*;

import com.lorenzo.marco.database.Database;

public class ClienteTest {

	private Cliente cliente;

	private Database database;

	@Before
	public void setUp() throws UnknownHostException {
		database = mock(Database.class);
	}

	@Test
	public void testNome() {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", "pass", database);
		assertEquals("Marco", this.cliente.getNome());
	}

	@Test
	public void testCognome() {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", "pass", database);
		assertEquals("Vignini", this.cliente.getCognome());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeVuoto() {
		this.cliente = creazioneCliente("", "Vignini", "nick", "pass", database);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCognomeVuoto() {
		this.cliente = creazioneCliente("Marco", "", "nick", "pass", database);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNicknameVuoto() {
		this.cliente = creazioneCliente("Marco", "Vignini", null, "pass", database);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPasswordVuoto() {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", null, database);
	}

	/*@Test(expected = IllegalArgumentException.class)
	public void testTuttiCampiVuoti() {
		this.cliente = creazioneCliente("", "", "", "", database);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTuttiCampiNull() {
		this.cliente = creazioneCliente(null, null, "", "", database);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeNull() {
		this.cliente = creazioneCliente(null, "", "", "", database);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCognomeNull() {
		this.cliente = creazioneCliente("", null, "", "", database);
	}*/
	
	/* Metodi che usano il mock */

	@Test
	public void testRichiestaAutenticazioneRiuscita() throws UnknownHostException {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", "", database);
		when(database.login("nick", "")).thenReturn("Richiesta di autenticatione riuscita");
		assertEquals("Richiesta di autenticatione riuscita", this.cliente.richiestaAutenticazione());
		verify(database, times(1)).login("nick", "");
	}

	@Test
	public void testRichiestaAutenticazioneNonRiuscita() throws UnknownHostException {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", "", database);
		when(database.login("", "")).thenReturn("Richiesta di autenticatione riuscita");
		assertNotEquals("Richiesta di autenticatione riuscita", this.cliente.richiestaAutenticazione());
		verify(database, times(1)).login("nick", "");
	}

	@Test
	public void testRichiestaRegistrazioneRiuscita() throws UnknownHostException {
		this.cliente = creazioneCliente("Marco", "Vignini", "", "", database);
		when(database.registrazioneCliente(cliente.getNome(), cliente.getCognome()))
				.thenReturn("Registrazione riuscita");
		assertEquals("Registrazione riuscita", this.cliente.richiestaRegistrazione());
		verify(database, times(1)).registrazioneCliente(cliente.getNome(), cliente.getCognome());
	}

	@Test
	public void testRichiestaRegistrazioneNonRiuscita() throws UnknownHostException {
		this.cliente = creazioneCliente("Marco", "Vignini", "", "", database);
		when(database.registrazioneCliente(cliente.getNome(), cliente.getCognome()))
				.thenReturn("Registrazione non riuscita");
		assertNotEquals("Registrazione riuscita", this.cliente.richiestaRegistrazione());
		verify(database, times(1)).registrazioneCliente(cliente.getNome(), cliente.getCognome());
	}

	private Cliente creazioneCliente(String nome, String cognome, String nickname, String password, Database database) {
		return new Cliente(nome, cognome, nickname, password, database);
	}

}