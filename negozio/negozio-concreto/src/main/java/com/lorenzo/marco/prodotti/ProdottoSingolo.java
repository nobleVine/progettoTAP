package com.lorenzo.marco.prodotti;

public class ProdottoSingolo extends ProdottoGenerico {

	private int limiteInferiore;

	public ProdottoSingolo(String nome, double prezzo, String descrizione) {
		super(nome, descrizione);
		controlloPrezzo(prezzo, limiteInferiore);
	}

	public double getPrezzo() {
		return prezzo;
	}

	@Override
	public void controlloPrezzo(double prezzo, int limiteInferiore) {
		if (prezzo > limiteInferiore) {
			super.prezzo = prezzo;
		} else {
			throw new IllegalArgumentException("Il prezzo minimo deve essere maggiore di zero");
		}

	}
}
