package com.lorenzo.marco.cliente;

import com.lorenzo.marco.banca.Banca;

public class ProfiloBancarioCliente {

	private Cliente cliente;
	private int idConto;
	private Banca banca;

	public ProfiloBancarioCliente(Cliente cliente, int idConto, Banca banca) {
		this.controlloParametri(cliente, idConto);
		this.banca = banca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getIdConto() {
		return idConto;
	}

	private void controlloParametri(Cliente cliente, int idConto) {
		if (cliente == null) {
			throw new IllegalArgumentException("Il parametro cliente non può essere null");
		} else {
			this.cliente = cliente;
		}

		if (idConto <= 0) {
			throw new IllegalArgumentException("L'id del conto non può essere minore o uguale a zero");
		} else {
			this.idConto = idConto;
		}

	}

	public String faiAcquisto(double spesaTotale) {
		return this.banca.pagamento(this.getIdConto(), spesaTotale);
	}

}
