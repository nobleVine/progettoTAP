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

public abstract class AmministratoreIT implements InizializzazioneIT {

	protected Database database;
	protected Cliente cliente;
	protected Amministratore amministratore;

	@Before
	public void setUp() throws UnknownHostException {
		inizializzazioneDatabase();
		this.cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
		this.amministratore = new Amministratore(database);
		this.cliente.richiestaRegistrazione();
	}
	
	@Test
	public void testNicknameRestituiti() throws UnknownHostException {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname2", "pass", database);
		cliente1.richiestaRegistrazione();
		Set<String> listaNickname = new HashSet<>();
		listaNickname.add("nickname");
		listaNickname.add("nickname2");
		assertEquals(listaNickname, amministratore.restituzioneListaNickname());
	}
	
	@Test
	public void testProfiloClienteRestituito() throws UnknownHostException {
		List<String> listaValori = new ArrayList<>();
		listaValori.add("Marco");
		listaValori.add("Vignini");
		listaValori.add("password");
		assertEquals(listaValori, amministratore.restituzioneListaCampiCliente("nickname"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testProfiloClienteRestituitoSenzaSuccesso() throws UnknownHostException {
		this.amministratore.restituzioneListaCampiCliente("nick");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreazioneListaAcquistiClienteSenzaSuccesso() throws UnknownHostException {
		List<String> listaAcquisti = new ArrayList<>();
		this.amministratore.creazioneAcquisti("nick", listaAcquisti);
	}
	
	@Test
	public void testRestituzioneAcquistiCliente() throws UnknownHostException {
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta");
		listaAcquisti.add("Calzini");
		assertEquals("Lista creata con successo", this.amministratore.creazioneAcquisti("nickname", listaAcquisti));
		assertEquals(listaAcquisti, amministratore.restituzioneListaAcquistiCliente("nickname"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneAcquistiClienteSenzaSuccesso() throws UnknownHostException {
		amministratore.restituzioneListaAcquistiCliente("nick");
	}

}
