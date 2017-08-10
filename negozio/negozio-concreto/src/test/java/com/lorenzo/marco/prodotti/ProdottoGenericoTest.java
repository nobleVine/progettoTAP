package com.lorenzo.marco.prodotti;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class ProdottoGenericoTest {
	
	protected ProdottoSingolo prodotto;
	
	protected ProdottoSingolo creazioneProdottoSingolo(String nome, double prezzo, String descrizione) {
		return new ProdottoSingolo(nome, prezzo, descrizione);
	}
	
	@Test
	public void testTipoNome() {
		assertAttributiStringhe(prodotto.getNome());
	}

	@Test
	public void testTipoDescrizione() {
		assertAttributiStringhe(prodotto.getDescrizione());
	}
	
	private void assertAttributiStringhe(String stringaDaTestare) {
		assertTrue(stringaDaTestare instanceof String);
	}
	
	public abstract void testNome();
	public abstract void testDescrizione();
	public abstract void testNomeVuoto();
	public abstract void testDescrizioneVuota();
	public abstract void testNomeNull();
	public abstract void testDescrizioneNull();

}
