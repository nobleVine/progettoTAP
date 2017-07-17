package com.lorenzo.marco.prodotti;

import static org.junit.Assert.*;

import org.junit.*;

public class ProdottoSingoloTest extends ProdottoGenericoAstratto {
	
	@Before
	public void setUp() {
		prodotto = creazioneProdottoSingolo("Maglietta", 50, "Maglietta basket");
	}

	@Test
	public void testNome() {
		assertEquals("Maglietta", prodotto.getNome());
	}

	@Test
	public void testPrezzo() {
		assertEquals(50, prodotto.getPrezzo(), 0);
	}

	@Test
	public void testDescrizione() {
		assertEquals("Maglietta basket", prodotto.getDescrizione());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputNegativo() {
		prodotto = creazioneProdottoSingolo("Maglietta", -9, "Maglietta basket");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeNonVuoto() {
		prodotto = creazioneProdottoSingolo(null, 5, "Maglietta basket");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDescrizioneNonVuoto() {
		prodotto = creazioneProdottoSingolo("Maglietta", 5, "");
	}

	@Test
	public void testTipoNome() {
		assertTrue(prodotto.getNome() instanceof String);
	}

	@Test
	public void testTipoDescrizione() {
		assertTrue(prodotto.getDescrizione() instanceof String);
	}

}
