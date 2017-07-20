package com.lorenzo.marco.cliente;

import com.lorenzo.marco.banca.Banca;
import com.lorenzo.marco.carrello.Carrello;

public class ProfiloBancarioCliente {

	private Cliente cliente;
	private int idConto;
	private double saldo;
	private Banca banca;
	private Carrello carrello;
	
	public ProfiloBancarioCliente(Cliente cliente, int idConto, int saldo, Banca banca){
		this.controlloParametri(cliente, idConto, saldo);
		this.banca = banca;
		this.carrello = new Carrello();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public int getIdConto() {
		return idConto;
	}

	public double getSaldo() {
		return saldo;
	}
		
	private void controlloParametri(Cliente cliente, int idConto, int saldo) {
		if (cliente == null) {
			throw new IllegalArgumentException("Il parametro cliente non può essere vuoto");
		} else {
			this.cliente = cliente;
		}

		if (idConto <= 0) {
			throw new IllegalArgumentException("L'id del conto non può essere minore o uguale a zero");
		} else {
			this.idConto = idConto;
		}
		
		if (saldo <= 0) {
			throw new IllegalArgumentException("Il saldo non può essere minore o uguale a zero");
		} else {
			this.saldo = saldo;
		}
	}

	public String faiAcquisto() {
		return this.banca.pagamento(this.getIdConto(), this.carrello.spesaTotale());
	}

	public Carrello getCarrello() {
		return carrello;
	}

}
