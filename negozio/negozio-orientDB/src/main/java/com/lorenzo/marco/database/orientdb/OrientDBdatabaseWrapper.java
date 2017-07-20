package com.lorenzo.marco.database.orientdb;

import java.net.UnknownHostException;
import java.util.*;

import com.lorenzo.marco.database.Database;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBdatabaseWrapper implements Database {

	private static final String NICKNAME = "nickname";
	private static final String CAMPOPASS = "password";
	private List<ODocument> elencoClienti;
		
	public OrientDBdatabaseWrapper() {
		this.elencoClienti = new ArrayList<>();
	}

	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password) throws UnknownHostException {
		for (ODocument documentoCliente : this.elencoClienti) {
			if (documentoCliente.field(NICKNAME) == nickname) {
				throw new IllegalAccessError("Nickname già esistente");
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

	public List<String> restituzioneNickname() {
		List<String> listaNickname = new ArrayList<>();
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
			}
		}
		return profiloCliente;
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

}
