package com.lorenzo.marco.database;

public interface DatabaseLatoCliente extends Database {

	public String registrazioneCliente(String nome, String cognome, String nickname, String password);
	public String login(String nickname, String password);

}