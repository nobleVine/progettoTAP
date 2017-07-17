package com.lorenzo.marco.it;

import java.net.UnknownHostException;

public interface ClienteIT {
	
	public String testLogin(String nickname, String password) throws UnknownHostException;
	public String testRegistrazioneCliente(String name, String cognome) throws UnknownHostException;
	
}
