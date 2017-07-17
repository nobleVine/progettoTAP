
package com.lorenzo.marco.cliente;

import java.net.UnknownHostException;

import com.lorenzo.marco.database.Database;

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

	private String getPassword() {
		return password;
	}

	private String getNickname() {
		return nickname;
	}

	public String richiestaAutenticazione() throws UnknownHostException {
		return this.database.login(this.getNickname(), this.getPassword());
	}

	public String richiestaRegistrazione() throws UnknownHostException {
		return this.database.registrazioneCliente(nome, cognome);
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

		if (nickname == null) {
			throw new IllegalArgumentException("nickname non può essere vuoto");
		} else {
			this.nickname = nickname;
		}
		
		if (password == null) {
			throw new IllegalArgumentException("password non può essere vuoto");
		} else {
			this.password = password;
		}

	}
}