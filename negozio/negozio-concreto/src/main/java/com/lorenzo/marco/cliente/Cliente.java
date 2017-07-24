
package com.lorenzo.marco.cliente;

import java.net.UnknownHostException;

import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.database.DatabaseLatoCliente;

public class Cliente {

	private String nome;
	private String cognome;
	private String password;
	private String nickname;
	
	private Database database;

	public Cliente(String nome, String cognome, String nickname, String password, Database database) {
		controlloParametriDiInizializzazioneCliente(nome, cognome, nickname, password);
		this.database = database;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String richiestaAutenticazione(String nickname, String password) throws UnknownHostException {
		return ((DatabaseLatoCliente) this.database).login(nickname, password);
	}

	public String richiestaRegistrazione() throws UnknownHostException {
		return ((DatabaseLatoCliente) this.database).registrazioneCliente(nome, cognome, nickname, password);
	}

	private void controlloParametriDiInizializzazioneCliente(String nome, String cognome, String nickname,
			String password) {
		if (nome == "" || nome == null) {
			throw new IllegalArgumentException("Il nome del cliente non può essere vuoto");
		} else {
			this.nome = nome;
		}

		if (cognome == "" || cognome == null) {
			throw new IllegalArgumentException("Il cognome del cliente non può essere vuoto");
		} else {
			this.cognome = cognome;
		}

		if (nickname == "" || nickname == null) {
			throw new IllegalArgumentException("Il nickname non può essere vuoto");
		} else {
			this.nickname = nickname;
		}
		
		if (password == "" || password == null) {
			throw new IllegalArgumentException("La password non può essere vuota");
		} else {
			this.password = password;
		}

	}
	
}