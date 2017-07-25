package com.lorenzo.marco.database.orientdb;

import java.net.UnknownHostException;
import java.util.*;

import com.lorenzo.marco.database.DatabaseLatoCliente;
import com.lorenzo.marco.database.DatabaseLatoAmministratore;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBdatabaseWrapper implements DatabaseLatoCliente, DatabaseLatoAmministratore {

	private static final String NICKNAME = "nickname";
	private static final String CAMPOPASS = "password";
	private List<ODocument> elencoClienti;

	public OrientDBdatabaseWrapper() {
		this.elencoClienti = new ArrayList<>();
	}

	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password)
			throws UnknownHostException {
		for (ODocument documentoCliente : this.elencoClienti) {
			if (documentoCliente.field(NICKNAME) == nickname) {
				throw new IllegalArgumentException("Nickname già esistente");
			}
		}
		return creazioneCliente(nome, cognome, nickname, password);
	}

	@Override
	public String login(String nickname, String password) throws UnknownHostException {
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
		boolean esistente = false;
		List<String> profiloCliente = new ArrayList<>();
		for (ODocument documentoCliente : this.elencoClienti) {
			if (documentoCliente.field(NICKNAME) == nickname) {
				esistente = true;
				profiloCliente.add(documentoCliente.field("nome"));
				profiloCliente.add(documentoCliente.field("cognome"));
				profiloCliente.add(documentoCliente.field(CAMPOPASS));
			}
		}
		if (esistente)
			return profiloCliente;
		throw new IllegalArgumentException("Nickname non esistente");
	}

	private String creazioneCliente(String nome, String cognome, String nickname, String password) {
		ODocument cliente = new ODocument("Cliente");
		cliente.field("nome", nome);
		cliente.field("cognome", cognome);
		cliente.field(NICKNAME, nickname);
		cliente.field(CAMPOPASS, password);
		cliente.save();
		elencoClienti.add(cliente);
		return "Registrazione riuscita";
	}

	public void creazioneListaAcquisti(String nickname, List<String> listaProdotti) {
		for (ODocument cliente : this.elencoClienti) {
			if (cliente.field(NICKNAME) == nickname) {
				cliente.field("acquisti", listaProdotti);
			}
		}
	}

	public List<String> restituzioneAcquistiCliente(String nickname) {
		for (ODocument cliente : this.elencoClienti) {
			if (cliente.field(NICKNAME) == nickname) {
				return cliente.field("acquisti");
			}
		}
		throw new IllegalArgumentException("Nickname non esistente");
	}

}
