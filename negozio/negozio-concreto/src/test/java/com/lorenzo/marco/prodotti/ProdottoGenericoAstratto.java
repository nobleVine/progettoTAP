package com.lorenzo.marco.prodotti;

public abstract class ProdottoGenericoAstratto {
	
	protected ProdottoSingolo prodotto;
	
	protected ProdottoSingolo creazioneProdottoSingolo(String nome, double prezzo, String descrizione) {
		return new ProdottoSingolo(nome, prezzo, descrizione);
	}
	
	public abstract void testNome();
	public abstract void testDescrizione();
	public abstract void testNomeNonVuoto();
	public abstract void testDescrizioneNonVuoto();
	public abstract void testTipoNome();
	public abstract void testTipoDescrizione();

}
