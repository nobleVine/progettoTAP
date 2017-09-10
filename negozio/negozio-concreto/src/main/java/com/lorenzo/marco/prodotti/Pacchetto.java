package com.lorenzo.marco.prodotti;

import java.util.*;

public class Pacchetto extends ProdottoGenerico {

	private List<ProdottoSingolo> listaProdotti;

	public Pacchetto(String nome, String descrizione) {
		super(nome, descrizione);
		this.listaProdotti = new ArrayList<>();
	}

	public double getPrezzo() {
		for (ProdottoSingolo prodottoSingolo : listaProdotti)
			prezzo = prezzo + prodottoSingolo.getPrezzo();
		return prezzo * 0.9;
	}

	public List<ProdottoSingolo> getListaProdottiSingoli() {
		return listaProdotti;
	}

	public void aggiungiProdotto(ProdottoSingolo prodotto) {
		listaProdotti.add(prodotto);
	}

	public int numProdotti() {
		return listaProdotti.size();
	}

	public void rimuoviProdotto(ProdottoSingolo prodotto) {
		if (numProdotti() == 0)
			throw new IllegalArgumentException("Non posso rimuovere un prodotto da un pacchetto vuoto");
		listaProdotti.remove(prodotto);
	}

	@Override
	public void controlloPrezzo(double prezzo, int limiteInferiore) {
		// Controllo sul prezzo non necessario
	}

}