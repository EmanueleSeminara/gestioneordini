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

			testCollegaArticoloECategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testScollegaArticoloECategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testCollegaOrdineEArticolo(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testScollegaOrdineEArticolo(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testRimozioneForzataOrdine(ordineServiceInstance);

			testCercaTuttiGliOrdiniConCategoria(articoloServiceInstance, categoriaServiceInstance,
					ordineServiceInstance);

			testCercaTutteLeCategorieDistinteConOrdine(articoloServiceInstance, categoriaServiceInstance,
					ordineServiceInstance);

			testSommaPrezziPerCategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testCercaOrdinePiuRecenteConCategoria(articoloServiceInstance, categoriaServiceInstance,
					ordineServiceInstance);

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

	private static void testCollegaArticoloECategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCollegaArticoloECategoria inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCollegaArticoloECategoria fallito ");

		Categoria nuovaCategoria = new Categoria("descrizione3", 98);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testInserimentoNuovaCategoria fallito ");

		articoloServiceInstance.collegaArticoloECategoria(articoloInstance, nuovaCategoria);

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorieOrdini(articoloInstance.getId());

		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testCollegaArticoloECategoria fallito: categoria non collegata ");

		System.out.println(".......testCollegaArticoloECategoria fine: PASSED.............");
	}

	private static void testScollegaArticoloECategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testScollegaArticoloECategoria inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testScollegaArticoloECategoria fallito ");

		Categoria nuovaCategoria = new Categoria("descrizione3", 98);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testScollegaArticoloECategoria fallito ");

		articoloServiceInstance.collegaArticoloECategoria(articoloInstance, nuovaCategoria);

		Articolo articoloReloaded = articoloServiceInstance
				.caricaSingoloElementoEagerCategorieOrdini(articoloInstance.getId());

		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testScollegaArticoloECategoria fallito: categoria non collegata ");

		articoloServiceInstance.scollegaArticoloECategoria(articoloInstance.getId(), nuovaCategoria.getId());

		articoloReloaded = articoloServiceInstance.caricaSingoloElementoEagerCategorieOrdini(articoloReloaded.getId());
		if (!articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testScollegaArticoloECategoria fallito: categoria non scollegata ");

		System.out.println(".......testScollegaArticoloECategoria fine: PASSED.............");
	}

	private static void testCollegaOrdineEArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCollegaOrdineEArticolo inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCollegaOrdineEArticolo fallito ");

		ordineServiceInstance.collegaOrdineEArticolo(articoloInstance, nuovoOrdine);

		Ordine ordineReloaded = ordineServiceInstance.caricaSingoloElementoEagerArticoli(nuovoOrdine.getId());

		if (ordineReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testCollegaOrdineEArticolo fallito: categoria non collegata ");

		System.out.println(".......testCollegaOrdineEArticolo fine: PASSED.............");
	}

	private static void testScollegaOrdineEArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testScollegaOrdineEArticolo inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testScollegaOrdineEArticolo fallito ");

		ordineServiceInstance.collegaOrdineEArticolo(articoloInstance, nuovoOrdine);
		Ordine ordineReloaded = ordineServiceInstance.caricaSingoloElementoEagerArticoli(nuovoOrdine.getId());

		if (ordineReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testScollegaOrdineEArticolo fallito: articolo non collegato ");

		ordineServiceInstance.scollegaOrdineEArticolo(articoloInstance.getId());

		ordineReloaded = ordineServiceInstance.caricaSingoloElementoEagerArticoli(ordineReloaded.getId());
		if (!ordineReloaded.getArticoli().isEmpty())
			throw new RuntimeException("testScollegaOrdineEArticolo fallito: articolo non scollegato ");

		System.out.println(".......testScollegaOrdineEArticolo fine: PASSED.............");
	}

	private static void testRimozioneForzataOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimozioneForzataOrdine inizio.............");

		Ordine nuovaOrdine = new Ordine("nomeDestinatario3", "indirizzoDestinatario3", new Date());
		ordineServiceInstance.inserisciNuovo(nuovaOrdine);

		if (nuovaOrdine.getId() == null)
			throw new RuntimeException("testRimozioneForzataOrdine fallito ");

		ordineServiceInstance.rimuoviForzatamente(nuovaOrdine);
		if (ordineServiceInstance.caricaSingoloElemento(nuovaOrdine.getId()) != null) {
			throw new RuntimeException("testRimozioneForzataOrdine fallito: Ordine non rimosso");
		}

		System.out.println(".......testRimozioneForzataOrdine fine: PASSED.............");
	}

	public static void testCercaTuttiGliOrdiniConCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaTuttiGliOrdiniConCategoria inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		Categoria nuovaCategoria = new Categoria("descrizione9", 14);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testCercaTuttiGliOrdiniConCategoria fallito ");
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testCercaTuttiGliOrdiniConCategoria fallito ");
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaTuttiGliOrdiniConCategoria fallito ");
		articoloServiceInstance.collegaArticoloECategoria(articoloInstance, nuovaCategoria);

		if (ordineServiceInstance.cercaTuttiGliOrdiniConCategoria(nuovaCategoria).size() != 1) {
			throw new RuntimeException("testCercaTuttiGliOrdiniConCategoria fallito ");
		}

		System.out.println(".......testCercaTuttiGliOrdiniConCategoria fine: PASSED.............");
	}

	public static void testCercaTutteLeCategorieDistinteConOrdine(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaTuttiLeCategorieDistinteConOrdine inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		Categoria nuovaCategoria = new Categoria("descrizione9", 14);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testCercaTuttiLeCategorieDistinteConOrdine fallito ");
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testCercaTuttiLeCategorieDistinteConOrdine fallito ");
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaTuttiLeCategorieDistinteConOrdine fallito ");
		articoloServiceInstance.collegaArticoloECategoria(articoloInstance, nuovaCategoria);

		if (categoriaServiceInstance.cercaTuttiLeCategorieDistinteConOrdine(nuovoOrdine).size() != 1) {
			throw new RuntimeException("testCercaTuttiLeCategorieDistinteConOrdine fallito ");
		}

		System.out.println(".......testCercaTuttiLeCategorieDistinteConOrdine fine: PASSED.............");
	}

	public static void testSommaPrezziPerCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testSommaPrezziPerCategoria inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		Categoria nuovaCategoria = new Categoria("descrizione9", 14);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testSommaPrezziPerCategoria fallito ");
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testSommaPrezziPerCategoria fallito ");
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziPerCategoria fallito ");
		articoloServiceInstance.collegaArticoloECategoria(articoloInstance, nuovaCategoria);

		if (articoloServiceInstance.sommaPrezziPerCategoria(nuovaCategoria) != 14) {
			throw new RuntimeException("testSommaPrezziPerCategoria fallito ");
		}

		System.out.println(".......testSommaPrezziPerCategoria fine: PASSED.............");
	}

	public static void testCercaOrdinePiuRecenteConCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCercaOrdinePiuRecenteConCategoria inizio.............");

		Ordine nuovoOrdine = new Ordine("nomeDestinatario1", "indirizzoDestinatario1", new Date());
		Categoria nuovaCategoria = new Categoria("descrizione9", 14);
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);
		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testCercaOrdinePiuRecenteConCategoria fallito ");
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testCercaOrdinePiuRecenteConCategoria fallito ");
		Articolo articoloInstance = new Articolo("descrizione1", "numeroSeriale1", 14,
				new SimpleDateFormat("dd/MM/yyyy").parse("24/09/2019"));
		articoloInstance.setOrdine(nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(articoloInstance);
		if (articoloInstance.getId() == null)
			throw new RuntimeException("testCercaOrdinePiuRecenteConCategoria fallito ");
		articoloServiceInstance.collegaArticoloECategoria(articoloInstance, nuovaCategoria);

		if (!ordineServiceInstance.cercaOrdinePiuRecenteConCategoria(nuovaCategoria).getId()
				.equals(nuovoOrdine.getId())) {
			throw new RuntimeException("testCercaOrdinePiuRecenteConCategoria fallito ");
		}

		System.out.println(".......testCercaOrdinePiuRecenteConCategoria fine: PASSED.............");
	}

}
