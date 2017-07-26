package com.lorenzo.marco.carrello;

import static org.junit.Assert.*;

import org.junit.*;

import com.lorenzo.marco.prodotti.Pacchetto;
import com.lorenzo.marco.prodotti.ProdottoSingolo;

public class CarrelloTest {

	private Carrello carrello;
	private ProdottoSingolo prodotto;
	private Pacchetto pacchetto;
		
	@Before
	public void setUp() {
		this.carrello = new Carrello();
		this.prodotto = new ProdottoSingolo("Pacchetto1", 50, "Questo è uno dei pacchetti più interessanti del negozio");
	}
	
	@Test
	public void testAggiuntaProdotto() {
		this.carrello.aggiungiAlCarrello(prodotto);
		this.assertNumeroProdottiAggiunti(1);
	}
	
	@Test
	public void testAggiuntaProdotti() {
		this.pacchetto = new Pacchetto("Pacchetto2", "Pacchetto conveniente");
		this.carrello.aggiungiAlCarrello(prodotto);
		this.carrello.aggiungiAlCarrello(pacchetto);
		this.assertNumeroProdottiAggiunti(2);		
	}

	@Test
	public void testRimuoviProdotto() {
		this.carrello.aggiungiAlCarrello(prodotto);
		this.carrello.rimuoviDalCarrello(prodotto);
		this.assertCarrelloVuoto();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRimuoviDaCarrelloVuoto() {
		this.carrello.rimuoviDalCarrello(prodotto);
	}
	
	@Test
	public void testSpesaTotale() {
		ProdottoSingolo prodottoSingolo1 = new ProdottoSingolo("maglia", 50, "Marca Nike");
		ProdottoSingolo prodottoSingolo2 = new ProdottoSingolo("felpa", 60, "Marca Adidas");
		ProdottoSingolo prodottoSingolo3 = new ProdottoSingolo("pantaloncini", 50, "Marca Diadora");
		Pacchetto pacchetto = new Pacchetto("Pacchetto3", "descrizione");
		pacchetto.aggiungiProdotto(prodottoSingolo2);
		pacchetto.aggiungiProdotto(prodottoSingolo3);
		this.carrello.aggiungiAlCarrello(prodottoSingolo1);
		this.carrello.aggiungiAlCarrello(pacchetto);
		assertSpesaTotale(160);
	}

	@Test
	public void testSpesaTotaleZero() {
		assertSpesaTotale(0);
	}
	
	private void assertNumeroProdottiAggiunti(int numeroProdottiAggiunti) {
		assertEquals(numeroProdottiAggiunti, carrello.lunghezzaCarrello());
		assertEquals("Pacchetto1", prodotto.getNome());
		assertEquals("Questo è uno dei pacchetti più interessanti del negozio", prodotto.getDescrizione());
	}
	
	private void assertCarrelloVuoto() {
		assertEquals(0, this.carrello.lunghezzaCarrello(), 0);
	}
	
	private void assertSpesaTotale(double spesaTotale) {
		assertEquals(spesaTotale, this.carrello.spesaTotale(), 0);
	}

}