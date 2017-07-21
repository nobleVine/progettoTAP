package com.lorenzo.marco.it;

import com.lorenzo.marco.database.orientdb.OrientDBdatabaseWrapper;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class ClienteOrientIT extends ClienteIT {

	private ODatabaseDocumentTx db;

	@Override
	protected void inizializzazioneDatabase() {
		this.database = new OrientDBdatabaseWrapper();
		this.db = new ODatabaseDocumentTx("plocal:localhost/negozioDB");
		if (!db.exists()) {
			this.db.create();
		}
		this.db.open("admin", "admin");
	}

}
