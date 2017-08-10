package com.lorenzo.marco.amministratore;

import java.util.List;
import java.util.Set;

import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.database.DatabaseLatoAmministratore;

public class Amministratore {

	private Database database;

	public Amministratore(Database database) {
		this.database = database;
	}

	public Set<String> restituzioneListaNickname() {
		return ((DatabaseLatoAmministratore) this.database).restituzioneNickname();
	}
	
	public List<String> restituzioneListaCampiCliente(String nickname) {
		return ((DatabaseLatoAmministratore) this.database).restituzioneProfiloCliente(nickname);
	}
	
	public String creazioneAcquisti(String nickname, List<String> listaAcquisti) {
		return ((DatabaseLatoAmministratore) this.database).registrazioneListaAcquistiCliente(nickname, listaAcquisti);
	}
	
	public List<String> restituzioneListaAcquistiCliente(String nickname) {
		return ((DatabaseLatoAmministratore) this.database).restituzioneAcquistiCliente(nickname);
	}

}
