package com.lorenzo.marco.amministratore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.DatabaseLatoAmministratore;
import com.lorenzo.marco.database.DatabaseLatoCliente;

public class AmministratoreTest {

	private DatabaseLatoAmministratore databaseLatoAmministratore;
	private Amministratore amministratore;
	private Set<String> listaNickname;
	private List<String> listaCampi;
	private DatabaseLatoCliente databaseLatoCliente;
	private List<String> listaAcquisti;

	@Before
	public void setUp() {
		this.databaseLatoAmministratore = mock(DatabaseLatoAmministratore.class);
		this.databaseLatoCliente = mock(DatabaseLatoCliente.class);
		this.amministratore = new Amministratore(databaseLatoAmministratore);
		this.listaNickname = new HashSet<>();
		this.listaCampi = new ArrayList<String>();
		this.listaAcquisti = new ArrayList<>();
	}

	@Test
	public void testRestituzioneListaNicknameVuota() {
		assertRestituzioneNickname(0);
	}

	@Test
	public void testRestituzioneListaNicknameNonVuota() {
		this.listaNickname.add("nick");
		assertRestituzioneNickname(1);
	}

	@Test
	public void testRestituzioneProfiloClienteConSuccesso() {
		this.listaCampi.add("nome1");
		this.listaCampi.add("cognome1");
		this.listaCampi.add("pass1");
		when(databaseLatoAmministratore.restituzioneProfiloCliente("nick")).thenReturn(listaCampi);
		assertEquals(3, this.amministratore.restituzioneListaCampiCliente("nick").size());
		assertEquals(this.listaCampi, this.amministratore.restituzioneListaCampiCliente("nick"));
		verify(databaseLatoAmministratore, times(2)).restituzioneProfiloCliente("nick");
		verifyNoMoreInteractions(databaseLatoAmministratore);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneProfiloClienteSenzaSuccesso() {
		when(databaseLatoAmministratore.restituzioneProfiloCliente("nick")).thenThrow(new IllegalArgumentException("Nickname non esistente"));
		this.amministratore.restituzioneListaCampiCliente("nick");
	}

	@Test
	public void testCreazioneListaAcquistiConSuccesso() throws UnknownHostException {
		Cliente cliente = new Cliente("Marco", "Verdi", "nick", "pass", databaseLatoCliente);
		cliente.richiestaRegistrazione();
		listaAcquisti.add("canotta");
		when(databaseLatoAmministratore.creaListaAcquisti("nick", listaAcquisti)).thenReturn("Lista creata con successo");
		assertEquals("Lista creata con successo", amministratore.creazioneAcquisti("nick", listaAcquisti));
		verify(databaseLatoAmministratore, times(1)).creaListaAcquisti("nick", this.listaAcquisti);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreazioneListaAcquistiSenzaSucesso() {
		when(databaseLatoAmministratore.creaListaAcquisti("nick", listaAcquisti)).thenThrow(new IllegalArgumentException("Nickname non esistente"));
		amministratore.creazioneAcquisti("nick", this.listaAcquisti);
	}
	
	@Test
	public void testRestituzioneListaAcquistiClienteConSuccesso() {
		Cliente cliente = new Cliente("Marco", "Verdi", "nick", "pass", databaseLatoCliente);
		cliente.richiestaRegistrazione();
		listaAcquisti.add("pantaloncini");
		listaAcquisti.add("cappellino");
		when(databaseLatoAmministratore.restituzioneAcquistiCliente("nick")).thenReturn(this.listaAcquisti);
		assertEquals(this.listaAcquisti, amministratore.restituzioneListaAcquistiCliente("nick"));
		verify(databaseLatoAmministratore, times(1)).restituzioneAcquistiCliente("nick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneListaAcquistiClienteSenzaSuccesso() throws UnknownHostException {
		when(databaseLatoAmministratore.restituzioneAcquistiCliente("NicknameNonEsistente")).thenThrow(new IllegalArgumentException("Nickname non esistente"));
		amministratore.restituzioneListaAcquistiCliente("NicknameNonEsistente");
	}

	private void assertRestituzioneNickname(int numeroClienti) {
		when(databaseLatoAmministratore.restituzioneNickname()).thenReturn(listaNickname);
		assertEquals(numeroClienti, this.amministratore.restituzioneListaNickname().size());
		verify(databaseLatoAmministratore, times(1)).restituzioneNickname();
		verifyNoMoreInteractions(this.databaseLatoAmministratore);
	}

}
