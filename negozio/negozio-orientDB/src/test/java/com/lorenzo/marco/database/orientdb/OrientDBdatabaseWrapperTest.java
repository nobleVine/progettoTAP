package com.lorenzo.marco.database.orientdb;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBdatabaseWrapperTest {

	private OrientDBdatabaseWrapper orientDBdatabaseWrapper;
	private ODatabaseDocumentTx db;

	@Before
	public void setUp() {
		this.orientDBdatabaseWrapper = new OrientDBdatabaseWrapper();
		this.db = new ODatabaseDocumentTx("plocal:localhost/negozioDB");
		if (!db.exists()) {
			this.db.create();
		}
		this.db.open("admin", "admin");
		this.orientDBdatabaseWrapper.registrazioneCliente("Marco", "James", "vigna", "pass");
	}

	@Test
	public void testCreazioneDB() {
		assertEquals(false, db.isClosed());
		db.close();
		assertEquals(true, db.isClosed());
	}

	@Test
	public void testNessunoRegistrato() {
		this.orientDBdatabaseWrapper.getElencoClienti().clear();
		assertTrue(this.orientDBdatabaseWrapper.getElencoClienti().isEmpty());
	}

	@Test
	public void testRegistrazioneClienteConSuccesso() {
		assertEquals("Registrazione riuscita", this.orientDBdatabaseWrapper.registrazioneCliente("nome1", "cognome1", "Nick", "pass1"));
		assertEquals("nome1", this.orientDBdatabaseWrapper.getElencoClienti().get(1).field("nome"));
		assertEquals("cognome1", this.orientDBdatabaseWrapper.getElencoClienti().get(1).field("cognome"));
		assertEquals("Nick", this.orientDBdatabaseWrapper.getElencoClienti().get(1).field("nickname"));
		assertEquals("pass1", this.orientDBdatabaseWrapper.getElencoClienti().get(1).field("password"));
	}

	@Test
	public void testRegistrazioneClientiConSuccesso()  {
		this.orientDBdatabaseWrapper.registrazioneCliente("Marco2", "James2", "vigna2", "pass2");
		List<ODocument> listaClienti = new ArrayList<>();
		listaClienti.add(creaCliente("Marco", "James", "vigna", "pass"));
		listaClienti.add(creaCliente("Marco2", "James2", "vigna2", "pass2"));
		for (int i = 0; i < listaClienti.size(); i++) {
			assertTrue(listaClienti.get(i).field("nome") == orientDBdatabaseWrapper.getElencoClienti().get(i)
					.field("nome"));
			assertTrue(listaClienti.get(i).field("cognome") == orientDBdatabaseWrapper.getElencoClienti().get(i)
					.field("cognome"));
			assertTrue(listaClienti.get(i).field("nickname") == orientDBdatabaseWrapper.getElencoClienti().get(i)
					.field("nickname"));
			assertTrue(listaClienti.get(i).field("password") == orientDBdatabaseWrapper.getElencoClienti().get(i)
					.field("password"));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegistrazioneClienteSbagliata() {
		this.orientDBdatabaseWrapper.registrazioneCliente("Alessio", "Rossi", "vigna", "pass1");
	}

	@Test
	public void testLoginConSuccesso() {
		assertEquals("Login riuscito", this.orientDBdatabaseWrapper.login("vigna", "pass"));
	}

	@Test(expected = IllegalAccessError.class)
	public void testLoginNicknameSbagliato() {
		this.orientDBdatabaseWrapper.login("vigna2", "pass");
	}

	@Test(expected = IllegalAccessError.class)
	public void testLoginPasswordSbagliata() {
		this.orientDBdatabaseWrapper.login("vigna", "pass2");
	}

	@Test(expected = IllegalAccessError.class)
	public void testLoginCredenzialiSbagliate() {
		this.orientDBdatabaseWrapper.login("vigna2", "pass2");
	}

	@Test
	public void testRestituzioneNickname() {
		Set<String> listaNickname = new HashSet<>();
		this.orientDBdatabaseWrapper.registrazioneCliente("Marco2", "James2", "vigna2", "pass2");
		listaNickname.add(creaCliente("Marco", "James", "vigna", "pass").field("nickname"));
		listaNickname.add(creaCliente("Marco2", "James2", "vigna2", "pass2").field("nickname"));
		assertEquals(listaNickname, this.orientDBdatabaseWrapper.restituzioneNickname());
	}

	@Test
	public void testRestituzioneProfiloClienteConSuccesso() {
		List<String> profiloCliente = new ArrayList<>();
		profiloCliente.add("Marco");
		profiloCliente.add("James");
		profiloCliente.add("pass");
		assertEquals(profiloCliente, this.orientDBdatabaseWrapper.restituzioneProfiloCliente("vigna"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneProfiloClienteSbagliato() {
		this.orientDBdatabaseWrapper.restituzioneProfiloCliente("nick");
	}
	
	@Test
	public void testRestituzioneAcquistiCliente() {
		assertRestituzioneAcquistiCliente("Marco", "James", "vigna", "pass");
	}
	
	@Test
	public void testRestituzioneAcquistiClienti()  {
		assertRestituzioneAcquistiCliente("Marco", "James", "vigna", "pass");
		this.orientDBdatabaseWrapper.registrazioneCliente("nome1", "cognome1", "Nick", "pass1");
		assertRestituzioneAcquistiCliente("nome1", "cognome1", "Nick", "pass1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneAcquistiClientiSenzaSuccesso() {
		this.orientDBdatabaseWrapper.restituzioneAcquistiCliente("nick");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void testRestituzioneCreazioneListaAcquistiClientiSenzaSuccesso() {
		List<String> listaAcquisti = null;
		this.orientDBdatabaseWrapper.creaListaAcquisti("nick", listaAcquisti);
	}
	
	private void assertRestituzioneAcquistiCliente(String nome, String cognome, String nickname, String password)  {
		this.orientDBdatabaseWrapper.login(nickname, password);
		List<String> listaProdotti = new ArrayList<>();
		listaProdotti.add("Maglietta");
		listaProdotti.add("calzini");
		assertEquals("Lista creata con successo", this.orientDBdatabaseWrapper.creaListaAcquisti(nickname, listaProdotti));
		assertEquals(listaProdotti, this.orientDBdatabaseWrapper.restituzioneAcquistiCliente(nickname));
	}
		
	private ODocument creaCliente(String nome, String cognome, String nickname, String password) {
		ODocument cliente = new ODocument("Cliente");
		cliente.field("nome", nome);
		cliente.field("cognome", cognome);
		cliente.field("nickname", nickname);
		cliente.field("password", password);
		cliente.save();
		return cliente;
	}

}
