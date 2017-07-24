package com.lorenzo.marco.amministratore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.lorenzo.marco.database.DatabaseLatoAmministratore;

public class AmministratoreTest {

	private DatabaseLatoAmministratore databaseLatoAmministratore;
	private Amministratore amministratore;
	private List<String> listaNickname;
	private List<String> listaCampi;
	
	@Before
	public void setUp() {
		this.databaseLatoAmministratore = mock(DatabaseLatoAmministratore.class);
		this.amministratore = new Amministratore(databaseLatoAmministratore);
		this.listaNickname = new ArrayList<String>();
		this.listaCampi = new ArrayList<String>();
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
		this.listaCampi.add("Lorenzo");
		this.listaCampi.add("Marino");
		this.listaCampi.add("Pass");
		when(databaseLatoAmministratore.restituzioneProfiloCliente("nick")).thenReturn(listaCampi);
		assertEquals(3, this.amministratore.restituzioneListaCampiCliente("nick").size());
		assertEquals(this.listaCampi, this.amministratore.restituzioneListaCampiCliente("nick"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneProfiloClienteSenzaSuccesso() {
		when(databaseLatoAmministratore.restituzioneProfiloCliente("nick")).thenThrow(new IllegalArgumentException("Nickname non esistente"));
		this.databaseLatoAmministratore.restituzioneProfiloCliente("nick");
	}

	private void assertRestituzioneNickname(int numeroClienti) {
		when(databaseLatoAmministratore.restituzioneNickname()).thenReturn(listaNickname);
		assertEquals(numeroClienti, this.amministratore.restituzioneListaNickname().size());
		verify(databaseLatoAmministratore, times(1)).restituzioneNickname();
		verifyNoMoreInteractions(this.databaseLatoAmministratore);
	}

}
