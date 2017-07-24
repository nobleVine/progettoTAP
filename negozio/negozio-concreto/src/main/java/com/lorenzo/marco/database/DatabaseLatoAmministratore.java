package com.lorenzo.marco.database;

import java.util.List;

public interface DatabaseLatoAmministratore extends Database {
	
	public List<String> restituzioneNickname();
	public List<String> restituzioneProfiloCliente(String nickname);

}
