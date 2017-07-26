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
import com.lorenzo.marco.database.DatabaseLatoAmministratore;

public abstract class AmministratoreIT implements ITI {

	protected DatabaseLatoAmministratore database;
	protected Cliente cliente;
	protected Amministratore amministratore;

	@Before
	public void setUp() {
		inizializzazioneDatabase();
		cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
		amministratore = new Amministratore(database);
	}

	@Test
	public void testNicknameRestiuiti() throws UnknownHostException {
		Cliente cliente1 = new Cliente("Lorenzo", "Rossi", "nickname2", "pass", database);
		this.cliente.richiestaRegistrazione();
		cliente1.richiestaRegistrazione();
		Set<String> listaNickname = new HashSet<>();
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
	
	@Test
	public void testRestituzioneAcquistiCliente() throws UnknownHostException {
		this.cliente.richiestaRegistrazione();
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta");
		listaAcquisti.add("Calzini");
		this.amministratore.creazioneAcquisti("nickname", listaAcquisti);
		assertEquals(listaAcquisti, amministratore.restituzioneListaAcquistiCliente("nickname"));
	}

	//public abstract void inizializzazioneDatabase();

}
