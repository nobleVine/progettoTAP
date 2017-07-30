package com.lorenzo.marco.main;

public class ApplicazioneRedisTest extends ApplicazioneTest {

	@Override
	protected void inizializzazioneApplicazioneTest() {
		this.applicazione = new ApplicazioneRedis();
	}

}
