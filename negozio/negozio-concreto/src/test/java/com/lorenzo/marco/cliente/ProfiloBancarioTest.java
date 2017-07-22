package com.lorenzo.marco.cliente;

import static org.junit.Assert.*;

import org.junit.*;
import static org.mockito.Mockito.*;

import com.lorenzo.marco.banca.Banca;
import com.lorenzo.marco.carrello.Carrello;
import com.lorenzo.marco.database.Database;
import com.lorenzo.marco.prodotti.ProdottoSingolo;

public class ProfiloBancarioTest {

	private ProfiloBancarioCliente profiloBancarioCliente;
	private Cliente cliente;
	private Database database;
	private Banca banca;

	@Before
	public void setUp() {
		banca = mock(Banca.class);
		this.cliente = new Cliente("Marco", "Vignini", "nick", "pass", database);
		this.profiloBancarioCliente = new ProfiloBancarioCliente(cliente, 123456789, 3000, banca);
	}

	@Test
	public void testGetIdConto() {
		assertEquals(123456789, this.profiloBancarioCliente.getIdConto());
	}

	@Test
	public void testGetSaldo() {
		assertEquals(3000, this.profiloBancarioCliente.getSaldo(), 0);
	}

	@Test
	public void testGetClient() {
		assertEquals(this.profiloBancarioCliente.getCliente().getNome(), this.cliente.getNome());
		assertEquals(this.profiloBancarioCliente.getCliente().getCognome(), this.cliente.getCognome());
		assertTrue(this.cliente.equals(this.profiloBancarioCliente.getCliente()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testClienteNull() {
		this.profiloBancarioCliente = new ProfiloBancarioCliente(null, 12345, 345, banca);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIdContoNonAmmissibile() {
		this.profiloBancarioCliente = new ProfiloBancarioCliente(cliente, 0, 43432, banca);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaldoNonAmmissibile() {
		this.profiloBancarioCliente = new ProfiloBancarioCliente(cliente, 1, 0, banca);
	}

	@Test
	public void testAcquistoRiuscito() {
		Carrello carrello = new Carrello();
		ProdottoSingolo prodotto1 = new ProdottoSingolo("Maglietta", 50, "Maglietta basket");
		ProdottoSingolo prodotto2 = new ProdottoSingolo("Maglietta", 60, "Maglietta basket");
		carrello.aggiungiAlCarrello(prodotto1);
		carrello.aggiungiAlCarrello(prodotto2);
		when(banca.pagamento(this.profiloBancarioCliente.getIdConto(),
				carrello.spesaTotale())).thenReturn("Acquisto riuscito");
		String risultatoPagamento = this.profiloBancarioCliente.faiAcquisto(carrello.spesaTotale());
		assertEquals("Acquisto riuscito", risultatoPagamento);
		verify(banca, times(1)).pagamento(this.profiloBancarioCliente.getIdConto(),
				110);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAcquistoNonRiuscito() {
		Carrello carrello = new Carrello();
		ProdottoSingolo prodotto1 = new ProdottoSingolo("Maglietta", 50, "Maglietta basket");
		ProdottoSingolo prodotto2 = new ProdottoSingolo("Maglietta", 60, "Maglietta basket");
		carrello.aggiungiAlCarrello(prodotto1);
		carrello.aggiungiAlCarrello(prodotto2);
		when(banca.pagamento(this.profiloBancarioCliente.getIdConto(),
				carrello.spesaTotale())).thenThrow(new IllegalArgumentException("Acquisto non riuscito: non hai abbastanza soldi!"));
		assertEquals("Acquisto riuscito", this.profiloBancarioCliente.faiAcquisto(carrello.spesaTotale()));
		verify(banca, times(1)).pagamento(this.profiloBancarioCliente.getIdConto(),
				110);
	}

}
