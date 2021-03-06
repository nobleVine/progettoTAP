package com.lorenzo.marco.cliente;

import static org.junit.Assert.*;

import org.junit.*;
import static org.mockito.Mockito.*;

import com.lorenzo.marco.banca.Banca;
import com.lorenzo.marco.carrello.Carrello;
import com.lorenzo.marco.database.DatabaseLatoCliente;
import com.lorenzo.marco.prodotti.ProdottoSingolo;

public class ProfiloBancarioClienteTest {

	private ProfiloBancarioCliente profiloBancarioCliente;
	private Cliente cliente;
	private DatabaseLatoCliente database;
	private Banca banca;

	@Before
	public void setUp() {
		banca = mock(Banca.class);
		this.cliente = new Cliente("Marco", "Vignini", "nick", "pass", database);
		this.profiloBancarioCliente = this.creazioneClienteProfiloBancario(cliente, 123456789);
	}

	@Test
	public void testGetIdConto() {
		assertEquals(123456789, this.profiloBancarioCliente.getIdConto());
	}

	@Test
	public void testGetCliente() {
		assertEquals(this.profiloBancarioCliente.getCliente().getNome(), this.cliente.getNome());
		assertEquals(this.profiloBancarioCliente.getCliente().getCognome(), this.cliente.getCognome());
		assertTrue(this.cliente.equals(this.profiloBancarioCliente.getCliente()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testClienteNull() {
		this.profiloBancarioCliente = new ProfiloBancarioCliente(null, 12345, banca);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIdContoNonAmmissibile() {
		this.profiloBancarioCliente = new ProfiloBancarioCliente(cliente, 0, banca);
	}

	@Test
	public void testAcquistoRiuscito() {
		Carrello carrello = new Carrello();
		ProdottoSingolo prodotto1 = new ProdottoSingolo("Maglietta1", 70, "Maglietta basket");
		ProdottoSingolo prodotto2 = new ProdottoSingolo("Maglietta2", 90, "Maglietta basket");
		carrello.aggiungiAlCarrello(prodotto1);
		carrello.aggiungiAlCarrello(prodotto2);
		assertAcquisto("Acquisto riuscito", carrello);
	}
	
	@Test
	public void testAcquistoNonRiuscito() {
		assertAcquisto("Acquisto non riuscito", new Carrello());
	}

	private void assertAcquisto(String esitoAcquisto, Carrello carrello) {
		when(banca.pagamento(this.profiloBancarioCliente.getIdConto(), carrello.spesaTotale()))
				.thenReturn(esitoAcquisto);
		String risultatoPagamento = this.profiloBancarioCliente.faiAcquisto(carrello.spesaTotale());
		assertEquals(esitoAcquisto, risultatoPagamento);
		verify(banca, times(1)).pagamento(this.profiloBancarioCliente.getIdConto(), carrello.spesaTotale());
		verifyNoMoreInteractions(banca);
	}

	private ProfiloBancarioCliente creazioneClienteProfiloBancario(Cliente cliente, int contoId) {
		return new ProfiloBancarioCliente(cliente, contoId, this.banca);
	}

}
