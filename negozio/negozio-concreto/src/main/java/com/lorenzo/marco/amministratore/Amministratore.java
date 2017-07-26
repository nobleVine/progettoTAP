package com.lorenzo.marco.amministratore;

import java.util.List;
import java.util.Set;

import com.lorenzo.marco.database.DatabaseLatoAmministratore;

public class Amministratore {

	private DatabaseLatoAmministratore databaseLatoAmministratore;

	public Amministratore(DatabaseLatoAmministratore databaseLatoAmministratore) {
		this.databaseLatoAmministratore = databaseLatoAmministratore;
	}

	public Set<String> restituzioneListaNickname() {
		return this.databaseLatoAmministratore.restituzioneNickname();
	}
	
	public List<String> restituzioneListaCampiCliente(String nickname) {
		return this.databaseLatoAmministratore.restituzioneProfiloCliente(nickname);
	}
	
	public List<String> restituzioneListaAcquistiCliente(String nickname) {
		return this.databaseLatoAmministratore.restituzioneAcquistiCliente(nickname);
	}

	public void creazioneAcquisti(String nickname, List<String> listaAcquisti) {
		this.databaseLatoAmministratore.creaListaAcquisti(nickname, listaAcquisti);
	}	

}
