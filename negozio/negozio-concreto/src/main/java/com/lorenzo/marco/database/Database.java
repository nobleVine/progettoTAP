package com.lorenzo.marco.database;

import java.net.UnknownHostException;

public interface Database {

	public String registrazioneCliente(String name, String cognome) throws UnknownHostException;
	public String login(String nickname, String password) throws UnknownHostException;

}