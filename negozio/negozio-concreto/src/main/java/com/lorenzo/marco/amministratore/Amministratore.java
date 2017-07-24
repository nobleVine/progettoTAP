package com.lorenzo.marco.amministratore;

import java.util.List;

import com.lorenzo.marco.database.DatabaseLatoAmministratore;

public class Amministratore {

	private DatabaseLatoAmministratore databaseLatoAmministratore;

	public Amministratore(DatabaseLatoAmministratore databaseLatoAmministratore) {
		this.databaseLatoAmministratore = databaseLatoAmministratore;
	}

	public List<String> restituzioneListaNickname() {
		return this.databaseLatoAmministratore.restituzioneNickname();
	}
	
	public List<String> restituzioneListaCampiCliente(String nickname) {
		return this.databaseLatoAmministratore.restituzioneProfiloCliente(nickname);
	}

}