package com.lorenzo.marco.database;

import java.net.UnknownHostException;
import java.util.List;

public interface Database {

	public String registrazioneCliente(String nome, String cognome, String nickname, String password) throws UnknownHostException;
	public String login(String nickname, String password) throws UnknownHostException;
	
	// Da decidere cosa farne...
	public List<String> restituzioneNickname();
	public List<String> restituzioneProfiloCliente(String nickname);

}