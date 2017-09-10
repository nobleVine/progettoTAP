package com.lorenzo.marco.database;

import java.util.List;
import java.util.Set;

public interface DatabaseLatoAmministratore extends Database {

	public Set<String> restituzioneNickname();

	public List<String> restituzioneProfiloCliente(String nickname);

	public String registrazioneListaAcquistiCliente(String nickname, List<String> listaAcquisti);

	public List<String> restituzioneAcquistiCliente(String nickname);

}