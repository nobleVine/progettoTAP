package com.lorenzo.marco.main;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public abstract class ApplicazioneTest {

	Applicazione applicazione;
	
	@Before
	public void setUp() {
		inizializzazioneApplicazioneTest();
	}
	
	@Test
	public void testEseguiApplicazione() {
		assertEquals(0, this.applicazione.eseguiApplicazione(), 0);
	}

	protected abstract void inizializzazioneApplicazioneTest();
	
}
