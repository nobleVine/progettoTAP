package com.lorenzo.marco.it;

import java.net.UnknownHostException;

import org.junit.Before;

import com.lorenzo.marco.amministratore.Amministratore;
import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.DatabaseLatoAmministratore;

public abstract class AmministratoreIT {
	
	protected DatabaseLatoAmministratore database;
	protected Cliente cliente;
	protected Amministratore amministratore;
	
	@Before
	public void setUp() {
		inizializzazioneDatabase();
		cliente = new Cliente("Marco", "Vignini", "nickname", "password", database);
		amministratore = new Amministratore(database);

	}

	public abstract void testNicknameRestiuiti() throws UnknownHostException;
	public abstract void testProfiloClienteRestituito() throws UnknownHostException;
	
	protected abstract void inizializzazioneDatabase();
}
