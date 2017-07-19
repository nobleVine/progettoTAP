package com.lorenzo.marco.database.orientdb;

import java.net.UnknownHostException;
import java.util.*;

import com.lorenzo.marco.database.Database;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class OrientDBdatabaseWrapper implements Database {

	private ODocument cliente;
	private List<ODocument> elencoClienti;

	public List<ODocument> getElencoClienti() {
		return elencoClienti;
	}

	public OrientDBdatabaseWrapper() {
		this.elencoClienti = new ArrayList<>();
	}

	@Override
	public String registrazioneCliente(String nome, String cognome, String nickname, String password)
			throws UnknownHostException {
		for (ODocument o : this.elencoClienti) {
			if (o.field("nickname") == nickname) {
				throw new IllegalAccessError("Nickname già esistente");
			}
		}
		this.cliente = new ODocument("Cliente");
		this.cliente.field("nome", nome);
		this.cliente.field("cognome", cognome);
		this.cliente.field("nickname", nickname);
		this.cliente.field("password", password);
		this.cliente.save();
		this.elencoClienti.add(cliente);
		return "Registrazione riuscita";
	}

	@Override
	public String login(String nickname, String password) throws UnknownHostException {
		for (ODocument o : this.elencoClienti) {
			if (o.field("nickname") == nickname && o.field("password") == password) {
				return "Login riuscito";
			}
		}
		throw new IllegalAccessError("Credenziali errate!");
	}

	public List<String> restituzioneNickname() {
		List<String> listaNickname = new ArrayList<>();
		for (ODocument cliente : this.elencoClienti) {
			listaNickname.add(cliente.field("nickname"));
		}
		return listaNickname;
	}

	public List<String> restituzioneProfiloCliente(String nickname) {
		List<String> profiloCliente = new ArrayList<>();
		for (ODocument cliente : this.elencoClienti) {
			if (cliente.field("nickname") == nickname) {
				profiloCliente.add(cliente.field("nome"));
				profiloCliente.add(cliente.field("cognome"));
				profiloCliente.add(cliente.field("password"));
			}
		}
		return profiloCliente;
	}

}
