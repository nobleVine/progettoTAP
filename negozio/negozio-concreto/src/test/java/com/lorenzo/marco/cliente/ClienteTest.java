package com.lorenzo.marco.cliente;

import static org.junit.Assert.*;

import org.junit.*;
import static org.mockito.Mockito.*;

import com.lorenzo.marco.database.DatabaseLatoCliente;

public class ClienteTest {

	private Cliente cliente;

	private DatabaseLatoCliente database;

	@Before
	public void setUp() {
		database = mock(DatabaseLatoCliente.class);
	}

	@Test
	public void testNome() {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", "pass");
		assertEquals("Marco", this.cliente.getNome());
	}

	@Test
	public void testCognome() {
		this.cliente = creazioneCliente("Marco", "Vignini", "nick", "pass");
		assertEquals("Vignini", this.cliente.getCognome());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeVuoto() {
		this.creazioneCliente("", "Vignini", "nick", "pass");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeNull() {
		this.creazioneCliente(null, "Vignini", "nick", "pass");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCognomeVuoto() {
		this.creazioneCliente("Marco", "", "nick", "pass");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCognomeNull() {
		this.creazioneCliente("Marco", null, "nick", "pass");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNicknameNull() {
		this.creazioneCliente("Marco", "Vignini", null, "pass");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNicknameVuoto() {
		this.creazioneCliente("Marco", "Vignini", "", "pass");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPasswordNull() {
		this.creazioneCliente("Marco", "Vignini", "nick", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPasswordVuoto() {
		this.creazioneCliente("Marco", "Vignini", "nick", "");
	}
	
	// Test dei metodi che usano il mock.

	@Test
	public void testRichiestaAutenticazioneRiuscita() {
		assertAutenticazione("Richiesta di autenticatione riuscita", "Marco", "Vignini", "nick", "pass");
	}

	@Test
	public void testRichiestaAutenticazioneNonRiuscita() {
		this.assertAutenticazione("Richiesta di autenticatione non riuscita", "Marco", "Vignini", "nick", "pass2");
	}
	
	@Test
	public void testRichiestaRegistrazioneRiuscita()  {
		this.assertRegistrazione("Registrazione riuscita", "Marco", "Vignini", "nick", "pass");
	}
	
	@Test
	public void testRichiestaRegistrazioneNonRiuscita() {
		this.assertRegistrazione("Registrazione non riuscita", "Marco", "Vignini", "nick", "pass");
	}

	private Cliente creazioneCliente(String nome, String cognome, String nickname, String password) {
		return new Cliente(nome, cognome, nickname, password, database);
	}
	
	private void assertAutenticazione(String esitoAutenticazione, String nome, String cognome, String nickname, String password) {
		this.cliente = creazioneCliente(nome, cognome, nickname, password);
		when(database.login(nickname, password)).thenReturn(esitoAutenticazione);
		assertEquals(esitoAutenticazione, this.cliente.richiestaAutenticazione(nickname, password));
		verify(database, times(1)).login(nickname, password);
		verifyNoMoreInteractions(this.database);
	}
	
	private void assertRegistrazione(String esitoRegistrazione, String nome, String cognome, String nickname, String password) {
		this.cliente = creazioneCliente(nome, cognome, nickname, password);
		when(database.registrazioneCliente(cliente.getNome(), cliente.getCognome(), nickname, password))
				.thenReturn(esitoRegistrazione);
		assertEquals(esitoRegistrazione, this.cliente.richiestaRegistrazione());
		verify(database, times(1)).registrazioneCliente(cliente.getNome(), cliente.getCognome(), nickname, password);
		verifyNoMoreInteractions(this.database);
	}

}