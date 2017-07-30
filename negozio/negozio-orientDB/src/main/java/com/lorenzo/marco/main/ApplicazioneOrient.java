package com.lorenzo.marco.main;

import com.lorenzo.marco.database.orientdb.OrientDBdatabaseWrapper;

public class ApplicazioneOrient extends Applicazione {

	@Override
	protected void inizializzazioneDatabaseSpecifico() {
		this.database = new OrientDBdatabaseWrapper();
	}
	
}
