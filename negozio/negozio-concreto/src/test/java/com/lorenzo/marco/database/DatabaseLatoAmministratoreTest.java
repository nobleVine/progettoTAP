package com.lorenzo.marco.database;

public interface DatabaseLatoAmministratoreTest extends DatabaseTest {
	
	public void testRestituzioneNickname();
	public void testRestituzioneProfiloClienteConSuccesso();
	public void testRestituzioneProfiloClienteSenzaSuccesso();
	public void testRestituzioneAcquistiClienteConSuccesso();
	public void testRestituzioneAcquistiClienteSenzaSuccesso();
	
}
