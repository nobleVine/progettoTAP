package com.lorenzo.marco.prodotti;

public class ProdottoSingolo extends ProdottoGenerico {

	public ProdottoSingolo(String nome, double prezzo, String descrizione) {
		super(nome, descrizione);
		super.controlloParametriProdotti(nome, descrizione);
		controlloPrezzo(prezzo);
	}

	public double getPrezzo() {
		return prezzo;
	}

	@Override
	public void controlloPrezzo(double prezzo) {
		if (prezzo > 0) {
			super.prezzo = prezzo;
		} else {
			throw new IllegalArgumentException("Il prezzo minimo deve essere maggiore di zero");
		}

	}
}
