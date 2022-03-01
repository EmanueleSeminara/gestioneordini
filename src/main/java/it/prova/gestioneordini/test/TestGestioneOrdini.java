package it.prova.gestioneordini.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;
import it.prova.gestioneordini.service.MyServiceFactory;
import it.prova.gestioneordini.service.articolo.ArticoloService;
import it.prova.gestioneordini.service.categoria.CategoriaService;
import it.prova.gestioneordini.service.ordine.OrdineService;

public class TestGestioneOrdini {

	public static void main(String[] args) {
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();

		try {

			System.out.println(
					"In tabella Categoria ci sono " + categoriaServiceInstance.listAll().size() + " elementi.");
			System.out
					.println("In tabella Articolo ci sono " + articoloServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			testInserimentoNuovoArticolo(articoloServiceInstance, ordineServiceInstance);

			testInserimentoNuovaCategoria(categoriaServiceInstance);

			testInserimentoNuovoOrdine(ordineServiceInstance);

			testRimozioneArticolo(articoloServiceInstance, ordineServiceInstance);

			testRimozioneCategoria(categoriaServiceInstance);

			testRimozioneOrdine(ordineServiceInstance);

			testAggiornamentoArticolo(articoloServiceInstance, ordineServiceInstance);

			testAggiornamentoCategoria(categoriaServiceInstance);

			testAggiornamentoOrdine(ordineServiceInstance);

			// TODO: TESTARE TUTTO IL CRUD

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println(
					"In tabella Categoria ci sono " + categoriaServiceInstance.listAll().size() + " elementi.");
			System.out
					.println("In tabella Articolo ci sono " + articoloServiceInstance.listAll().size() + " elementi.");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserimentoNuovoArticolo(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoArticolo inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovoArticolo fallito ");

		System.out.println(".......testInserimentoNuovoArticolo fine: PASSED.............");
	}

	private static void testInserimentoNuovaCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovaCategoria inizio.............");

		Categoria categoriaInstance = new Categoria("descrizione1", 14);
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);
		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaCategoria fallito ");

		categoriaServiceInstance.rimuovi(categoriaInstance);

		System.out.println(".......testInserimentoNuovaCategoria fine: PASSED.............");
	}

	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovaOrdine inizio.............");

		Ordine ordineInstance = new Ordine("nomeDestinatario2", "indirizzoDestinatario2", new Date());
		ordineServiceInstance.inserisciNuovo(ordineInstance);
		if (ordineInstance.getId() == null)
			throw new RuntimeException("testInserimentoNuovaOrdine fallito ");

		ordineServiceInstance.rimuovi(ordineInstance);

		System.out.println(".......testInserimentoNuovaOrdine fine: PASSED.............");
	}

	private static void testRimozioneArticolo(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimozioneArticolo inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testRimozioneArticolo fallito ");

		articoloServiceInstance.rimuovi(articoloInstance);
		if (articoloServiceInstance.caricaSingoloElemento(articoloInstance.getId()) != null) {
			throw new RuntimeException("testInserimentoNuovoArticolo fallito: Articolo non rimosso");
		}

		System.out.println(".......testRimozioneArticolo fine: PASSED.............");
	}

	private static void testRimozioneCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testRimozioneCategoria inizio.............");

		Categoria nuovaCategoria = new Categoria("Descrizione3", 99);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testRimozioneCategoria fallito ");

		categoriaServiceInstance.rimuovi(nuovaCategoria);
		if (categoriaServiceInstance.caricaSingoloElemento(nuovaCategoria.getId()) != null) {
			throw new RuntimeException("testRimozioneCategoria fallito: Categoria non rimosso");
		}

		System.out.println(".......testRimozioneCategoria fine: PASSED.............");
	}

	private static void testRimozioneOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimozioneOrdine inizio.............");

		Ordine nuovaOrdine = new Ordine("nomeDestinatario3", "indirizzoDestinatario3", new Date());
		ordineServiceInstance.inserisciNuovo(nuovaOrdine);

		if (nuovaOrdine.getId() == null)
			throw new RuntimeException("testRimozioneOrdine fallito ");

		ordineServiceInstance.rimuovi(nuovaOrdine);
		if (ordineServiceInstance.caricaSingoloElemento(nuovaOrdine.getId()) != null) {
			throw new RuntimeException("testRimozioneOrdine fallito: Ordine non rimosso");
		}

		System.out.println(".......testRimozioneOrdine fine: PASSED.............");
	}

	private static void testAggiornamentoArticolo(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoArticolo inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testAggiornamentoArticolo fallito ");

		articoloInstance.setDescrizione("Pluto");
		articoloServiceInstance.aggiorna(articoloInstance);
		if (!articoloServiceInstance.caricaSingoloElemento(articoloInstance.getId()).getDescrizione()
				.equals(articoloInstance.getDescrizione())) {
			throw new RuntimeException("testAggiornamentoArticolo fallito: Articolo non rimosso");
		}

		System.out.println(".......testAggiornamentoArticolo fine: PASSED.............");
	}

	private static void testAggiornamentoCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testRimozioneCategoria inizio.............");

		Categoria nuovaCategoria = new Categoria("Descrizione3", 99);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testRimozioneCategoria fallito ");
		nuovaCategoria.setDescrizione("Paperino");
		categoriaServiceInstance.aggiorna(nuovaCategoria);
		if (!categoriaServiceInstance.caricaSingoloElemento(nuovaCategoria.getId()).getDescrizione()
				.equals(nuovaCategoria.getDescrizione())) {
			throw new RuntimeException("testRimozioneCategoria fallito: Categoria non rimosso");
		}
		categoriaServiceInstance.rimuovi(nuovaCategoria);

		System.out.println(".......testRimozioneCategoria fine: PASSED.............");
	}

	private static void testAggiornamentoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoOrdine inizio.............");

		Ordine nuovaOrdine = new Ordine("nomeDestinatario4", "indirizzoDestinatario4", new Date());
		ordineServiceInstance.inserisciNuovo(nuovaOrdine);

		if (nuovaOrdine.getId() == null)
			throw new RuntimeException("testAggiornamentoOrdine fallito ");
		nuovaOrdine.setIndirizzoSpedizione("Paperino");
		ordineServiceInstance.aggiorna(nuovaOrdine);
		if (!ordineServiceInstance.caricaSingoloElemento(nuovaOrdine.getId()).getIndirizzoSpedizione()
				.equals(nuovaOrdine.getIndirizzoSpedizione())) {
			throw new RuntimeException("testRimozioneOrdine fallito: Ordine non rimosso");
		}
		ordineServiceInstance.rimuovi(nuovaOrdine);

		System.out.println(".......testAggiornamentoOrdine fine: PASSED.............");
	}

}