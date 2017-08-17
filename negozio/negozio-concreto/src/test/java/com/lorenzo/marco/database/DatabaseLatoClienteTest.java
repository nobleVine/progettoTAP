package com.lorenzo.marco.database;

public interface DatabaseLatoClienteTest extends DatabaseTest {
	
	public void testRegistrazioneClienteConSuccesso();
	public void testRegistrazioneClienteSenzaSuccesso();
	public void testRegistrazioneClientiConSuccesso();
	public void testLoginConSuccesso();
	public void testLoginNicknameNonRegistrato();
	public void testLoginPasswordErrata();
	public void testLoginCredenzialiSbagliate();
	
}