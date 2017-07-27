package com.lorenzo.marco.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.lorenzo.marco.amministratore.Amministratore;
import com.lorenzo.marco.cliente.Cliente;
import com.lorenzo.marco.database.Database;

public class Applicazione {
	
	private Database database;

	final Logger logger = Logger.getLogger(Applicazione.class);
	
	public Applicazione (Database database) {
		this.database = database;
	}
	
	public void eseguiApplicazione() {
		Cliente cliente1 = new Cliente("Marco", "Vignini", "vigna", "pass1", database);
		Cliente cliente2 = new Cliente("Lorenzo", "Marino", "lore", "pass2", database);
		
		logger.info("Registrazione e autenticazione cliente1:");
		logger.info(cliente1.richiestaRegistrazione());
		logger.info(cliente1.richiestaAutenticazione("vigna", "pass1"));
				
		logger.info("Registrazione e autenticazione cliente2:");
		logger.info(cliente2.richiestaRegistrazione());
		logger.info(cliente2.richiestaAutenticazione("lore", "pass2"));
		
		Amministratore amministratore = new Amministratore(database);
		Set<String> listaNickname =	amministratore.restituzioneListaNickname();
		logger.info("Elenco dei nickname appartenenti ai clienti registrati:");
		logger.info(listaNickname);
	
		logger.info("Profilo di cliente1: ");
		logger.info(amministratore.restituzioneListaCampiCliente("vigna"));
		
		logger.info("Profilo di cliente2: ");
		logger.info(amministratore.restituzioneListaCampiCliente("lore"));
		
		logger.info("Creazione lista prodotti di cliente1:");
		List<String> listaAcquisti = new ArrayList<>();
		listaAcquisti.add("Maglietta");
		listaAcquisti.add("Pallone da Basket");
		logger.info(amministratore.creazioneAcquisti("vigna", listaAcquisti ));
		logger.info("Elenco acquisti di cliente1:");
		logger.info(amministratore.restituzioneListaAcquistiCliente("vigna"));
				
		logger.info("Creazione lista prodotti di cliente2:");
		List<String> listaAcquisti2 = new ArrayList<>();
		listaAcquisti2.add("Felpa nike");
		listaAcquisti2.add("Maglietta basket");
		logger.info(amministratore.creazioneAcquisti("lore", listaAcquisti2 ));
		logger.info("Elenco acquisti di cliente2:");
		logger.info(amministratore.restituzioneListaAcquistiCliente("lore"));
	}
	
}
