package com.lorenzo.marco.cliente;

import static org.junit.Assert.*;

import org.junit.*;
import static org.mockito.Mockito.*;

import com.lorenzo.marco.banca.Banca;
import com.lorenzo.marco.database.Database;

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
		when(banca.pagamento(this.profiloBancarioCliente.getIdConto(),
				this.profiloBancarioCliente.getCarrello().spesaTotale())).thenReturn("Acquisto riuscito");
		String risultatoPagamento = this.profiloBancarioCliente.faiAcquisto();
		assertEquals("Acquisto riuscito", risultatoPagamento);
		verify(banca, times(1)).pagamento(this.profiloBancarioCliente.getIdConto(),
				this.profiloBancarioCliente.getCarrello().spesaTotale());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAcquistoNonRiuscito() {
		when(banca.pagamento(this.profiloBancarioCliente.getIdConto(),
				this.profiloBancarioCliente.getCarrello().spesaTotale())).thenThrow(new IllegalArgumentException("Acquisto non riuscito: non hai abbastanza soldi!"));
		assertEquals("Acquisto riuscito", this.profiloBancarioCliente.faiAcquisto());
		verify(banca, times(1)).pagamento(this.profiloBancarioCliente.getIdConto(),
				this.profiloBancarioCliente.getCarrello().spesaTotale());
	}

}
