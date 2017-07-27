package com.lorenzo.marco.database.orientdb;

import com.lorenzo.marco.database.DatabaseLatoCliente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lorenzo.marco.database.DatabaseLatoAmministratore;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBdatabaseWrapper implements DatabaseLatoCliente, DatabaseLatoAmministratore {

	private static final String NICKNAME_NON_ESISTENTE = "Nickname non esistente";
	private static final String NICKNAME = "nickname";
	private static final String CAMPOPASS = "password";
	private List<ODocument> elencoClienti;

	public OrientDBdatabaseWrapper() {
		this.elencoClienti = new ArrayList<>();
	}

	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password) {
		for (ODocument documentoCliente : this.elencoClienti) {
			if (documentoCliente.field(NICKNAME) == nickname) {
				throw new IllegalArgumentException("Nickname già esistente");
			}
		}
		return inserimentoClienteNelDatabase(nome, cognome, nickname, password);
	}

	@Override
	public String login(String nickname, String password) {
		for (ODocument documentoCliente : this.elencoClienti) {
			if (documentoCliente.field(NICKNAME) == nickname && documentoCliente.field(CAMPOPASS) == password) {
				return "Login riuscito";
			}
		}
		throw new IllegalAccessError("Credenziali errate!");
	}

	public List<ODocument> getElencoClienti() {
		return elencoClienti;
	}

	public Set<String> restituzioneNickname() {
		Set<String> listaNickname = new HashSet<>();
		for (ODocument documentoCliente : this.elencoClienti) {
			listaNickname.add(documentoCliente.field(NICKNAME));
		}
		return listaNickname;
	}

	public List<String> restituzioneProfiloCliente(String nickname) {
		List<String> profiloCliente = new ArrayList<>();
		for (ODocument documentoCliente : this.elencoClienti) {
			if (documentoCliente.field(NICKNAME) == nickname) {
				profiloCliente.add(documentoCliente.field("nome"));
				profiloCliente.add(documentoCliente.field("cognome"));
				profiloCliente.add(documentoCliente.field(CAMPOPASS));
				return profiloCliente;
			}
		}
		throw new IllegalArgumentException(NICKNAME_NON_ESISTENTE);
	}

	public String creaListaAcquisti(String nickname, List<String> listaProdotti) {
		for (ODocument cliente : this.elencoClienti) {
			if (cliente.field(NICKNAME) == nickname) {
				cliente.field("acquisti", listaProdotti);
				return "Lista creata con successo";
			}
		}
		throw new IllegalArgumentException(NICKNAME_NON_ESISTENTE);
	}

	public List<String> restituzioneAcquistiCliente(String nickname) {
		for (ODocument cliente : this.elencoClienti) {
			if (cliente.field(NICKNAME) == nickname) {
				return cliente.field("acquisti");
			}
		}
		throw new IllegalArgumentException(NICKNAME_NON_ESISTENTE);
	}
	
	private String inserimentoClienteNelDatabase(String nome, String cognome, String nickname, String password) {
		ODocument cliente = new ODocument("Cliente");
		cliente.field("nome", nome);
		cliente.field("cognome", cognome);
		cliente.field(NICKNAME, nickname);
		cliente.field(CAMPOPASS, password);
		elencoClienti.add(cliente);
		return "Registrazione riuscita";
	}

	

}
