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
	
	protected abstract void testNome();
	protected abstract void testDescrizione();
	protected abstract void testNomeVuoto();
	protected abstract void testDescrizioneVuota();
	protected abstract void testNomeNull();
	protected abstract void testDescrizioneNull();

}