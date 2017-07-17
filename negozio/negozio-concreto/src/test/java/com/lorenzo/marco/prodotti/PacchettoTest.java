package com.lorenzo.marco.prodotti;

import static org.junit.Assert.*;

import org.junit.*;

public class PacchettoTest extends ProdottoGenericoAstratto {
	
	private Pacchetto pacchetto;
	
	@Before
	public void setUp() {
		pacchetto = this.creazionePacchetto("Pacchetto", "Pacchetto basket");
		prodotto = creazioneProdottoSingolo("Maglietta", 50, "Maglietta basket");
	}
	
	@Test
	public void testNome() {
		assertEquals("Pacchetto", pacchetto.getNome());
	}

	@Test
	public void testDescrizione() {
		assertEquals("Pacchetto basket", pacchetto.getDescrizione());
	}

	@Test
	public void testPacchettoVuoto() {
		assertEquals(0, pacchetto.numProdotti());
	}
	
	@Test
	public void testAggiungiSingoloProdottoAlPacchetto() {
		pacchetto.aggiungiProdotto(prodotto);
		assertEquals(1, pacchetto.numProdotti());
		assertEquals("Maglietta", pacchetto.getListaProdottiSingoli().get(0).getNome());
		assertEquals(50, pacchetto.getListaProdottiSingoli().get(0).getPrezzo(), 0);
		assertEquals("Maglietta basket", pacchetto.getListaProdottiSingoli().get(0).getDescrizione());
	}

	@Test
	public void testRimuoviSingoloProdottoAlPacchetto() {
		pacchetto.aggiungiProdotto(prodotto);
		pacchetto.rimuoviProdotto(prodotto);
		assertEquals(0, pacchetto.numProdotti());
	}

	@Test(expected = RuntimeException.class)
	public void testRimuoviProdottoDaPacchettoVuoto() {
		pacchetto.rimuoviProdotto(prodotto);
	}

	@Test
	public void testGetPrezzoPacchettoConPiuDiUnProdotto() {
		ProdottoSingolo prodotto2 = creazioneProdottoSingolo("Pantaloncini", 50, "Pantaloncini basket");
		pacchetto.aggiungiProdotto(prodotto);
		pacchetto.aggiungiProdotto(prodotto2);
		assertEquals(100, pacchetto.getPrezzo(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeNonVuoto() {
		pacchetto = creazionePacchetto(null, "Pacchetto basket");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDescrizioneNonVuoto() {
		pacchetto = creazionePacchetto("Pacchetto", "");
	}
	
	@Test
	public void testTipoNome() {
		assertTrue(pacchetto.getNome() instanceof String);
	}

	@Test
	public void testTipoDescrizione() {
		assertTrue(pacchetto.getDescrizione() instanceof String);
	}
	
	@Test
	public void testControlloPrezzo() {
		pacchetto.controlloPrezzo(pacchetto.getPrezzo());
	}

	private Pacchetto creazionePacchetto(String nome, String descrizione) {
		return new Pacchetto(nome, descrizione);
	}
}
