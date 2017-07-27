package com.lorenzo.marco.main;

import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.database.redis.RedisDatabaseWrapper;

public class Main {

	public static void main(String[] args) {
		
		Database database = new RedisDatabaseWrapper();
		Applicazione applicazione = new Applicazione(database);
		applicazione.eseguiApplicazione();
	}

}
