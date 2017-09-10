package com.lorenzo.marco.main;

import com.lorenzo.marco.database.orientdb.OrientDBdatabaseWrapper;

public class DimostrazioneApplicazioneOrient extends DimostrazioneApplicazione {

	@Override
	protected void inizializzazioneDatabaseSpecifico() {
		this.database = new OrientDBdatabaseWrapper();
	}

}
