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
	public void testPrezzoNegativo() {
		creazioneProdottoSingolo("Maglietta", -9, "Maglietta basket");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPrezzoZero() {
		creazioneProdottoSingolo("Maglietta", 0, "Maglietta basket");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeNull() {
		creazioneProdottoSingolo(null, 5, "Maglietta basket");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNomeVuoto() {
		creazioneProdottoSingolo("", 5, "Maglietta basket");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDescrizioneVuota() {
		creazioneProdottoSingolo("Maglietta", 5, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDescrizioneNull() {
		creazioneProdottoSingolo("Maglietta", 5, null);
	}

	@Test
	public void testTipoNome() {
		assertAttributiStringhe(prodotto.getNome());
	}

	@Test
	public void testTipoDescrizione() {
		assertAttributiStringhe(prodotto.getDescrizione());
	}
	
	private void assertAttributiStringhe(String stringaDaTestare) {
		assertTrue(stringaDaTestare instanceof String);
	}
	
}
