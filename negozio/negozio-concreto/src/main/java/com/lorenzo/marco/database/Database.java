package com.lorenzo.marco.database;

import java.net.UnknownHostException;

public interface Database {

	public String registrazioneCliente(String nome, String cognome, String nickname, String password) throws UnknownHostException;
	public String login(String nickname, String password) throws UnknownHostException;

}