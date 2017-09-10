package com.lorenzo.marco.it;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lorenzo.marco.database.orientdb.OrientDBdatabaseWrapper;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class OrientIT extends DatabaseIT {

	private ODatabaseDocumentTx db;
	private OrientDBdatabaseWrapper orientDBdatabaseWrapper;

	public void inizializzazioneDatabase() {
		this.database = new OrientDBdatabaseWrapper();
		this.db = new ODatabaseDocumentTx("plocal:localhost/negozioDB");
		if (!db.exists()) {
			this.db.create();
		}
		this.db.open("admin", "admin");
		orientDBdatabaseWrapper = new OrientDBdatabaseWrapper();
	}

	@Test
	public void testGetElencoClientiListaVuota() {
		assertEquals(0, orientDBdatabaseWrapper.getElencoClienti().size(), 0);
	}

	@Test
	public void testGetElencoClienti() {
		this.orientDBdatabaseWrapper.registrazioneCliente("Giovanni", "Rossi", "n1", "p1");
		this.orientDBdatabaseWrapper.registrazioneCliente("Giovanni2", "Rossi2", "n2", "p2");
		assertEquals(2, orientDBdatabaseWrapper.getElencoClienti().size(), 0);
		assertEquals(this.orientDBdatabaseWrapper.getElencoClienti().get(0).field("nickname"), "n1");
		assertEquals(this.orientDBdatabaseWrapper.getElencoClienti().get(1).field("nickname"), "n2");
	}

}
