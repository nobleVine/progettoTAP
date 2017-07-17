package com.lorenzo.marco.prodotti;

public abstract class ProdottoGenerico {

	protected String nome;
	protected double prezzo;
	protected String descrizione;

	public ProdottoGenerico(String nome, String descrizione) {
		controlloParametriProdotti(nome, descrizione);
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	protected void controlloParametriProdotti(String nome, String descrizione) {
		if (nome == "" || nome == null) {
			throw new IllegalArgumentException("Il nome del pacchetto non può essere vuoto");
		} else {
			this.nome = nome;
		}

		if (descrizione == "" || descrizione == null) {
			throw new IllegalArgumentException("La descrizione del pacchetto non può essere vuoto");
		} else {
			this.descrizione = descrizione;
		}
	}

	public abstract double getPrezzo();

	public abstract void controlloPrezzo(double prezzo);

}