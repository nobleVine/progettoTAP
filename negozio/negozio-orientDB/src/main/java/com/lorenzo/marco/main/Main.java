package com.lorenzo.marco.main;

import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.database.orientdb.OrientDBdatabaseWrapper;
import com.lorenzo.marco.main.Applicazione;

public class Main {

	public static void main(String[] args) {
		Database database = new OrientDBdatabaseWrapper();
		Applicazione applicazione = new Applicazione(database);
		applicazione.eseguiApplicazione();
	}

}
