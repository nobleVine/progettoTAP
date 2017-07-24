package com.lorenzo.marco.database;

import java.net.UnknownHostException;

public interface DatabaseLatoClienteTest extends Database {
	
	public void testLogin(String nickname, String password) throws UnknownHostException;
	public void testRegistrazioneCliente(String nome, String cognome) throws UnknownHostException;
	
}