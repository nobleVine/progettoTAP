package com.lorenzo.marco.database;

import java.net.UnknownHostException;

public interface DatabaseTest {
	
	public String testLogin(String nickname, String password) throws UnknownHostException;
	public String testRegistrazioneCliente(String name, String cognome) throws UnknownHostException;
	
}
