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
		this.prodotto = new ProdottoSingolo("Pack King", 50, "il pacchetto di Lebron");
	}
	
	@Test
	public void testCarrelloVuoto() {
		assertEquals(0, this.carrello.lunghezzaCarrello(), 0);
	}
	
	@Test
	public void testAggiuntaProdotto() {
		this.carrello.aggiungiAlCarrello(prodotto);
		assertEquals(1, carrello.lunghezzaCarrello());
		assertEquals("Pack King", prodotto.getNome());
		assertEquals("il pacchetto di Lebron", prodotto.getDescrizione());	
	}
	
	@Test
	public void testAggiuntaProdotti() {
		this.pacchetto = new Pacchetto("Starter pack 1", "iniziale");
		this.carrello.aggiungiAlCarrello(prodotto);
		this.carrello.aggiungiAlCarrello(pacchetto);
		assertEquals(2, carrello.lunghezzaCarrello());
		assertEquals("Pack King", prodotto.getNome());
		assertEquals("il pacchetto di Lebron", prodotto.getDescrizione());		
	}
	
	@Test
	public void testRimuoviProdotto() {
		this.carrello.aggiungiAlCarrello(prodotto);
		this.carrello.rimuoviDalCarrello(prodotto);
		assertEquals(0,carrello.lunghezzaCarrello(), 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRimuoviDaCarrelloVuoto() {
		this.carrello.rimuoviDalCarrello(prodotto);
	}
	
	@Test
	public void testSpesaTotale() {
		ProdottoSingolo prodottoSingolo1 = new ProdottoSingolo("maglia", 50, "lebron");
		ProdottoSingolo prodottoSingolo2 = new ProdottoSingolo("maglietta", 60, "Steph");
		ProdottoSingolo prodottoSingolo3 = new ProdottoSingolo("pantaloncini", 50, "Heat");
		Pacchetto pacchetto = new Pacchetto("pack1", "descrizione");
		pacchetto.aggiungiProdotto(prodottoSingolo2);
		pacchetto.aggiungiProdotto(prodottoSingolo3);
		this.carrello.aggiungiAlCarrello(prodottoSingolo1);
		this.carrello.aggiungiAlCarrello(pacchetto);
		assertEquals(160, this.carrello.spesaTotale(),0);
	}

}