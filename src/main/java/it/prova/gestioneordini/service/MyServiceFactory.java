package it.prova.gestioneordini.service;

import it.prova.gestioneordini.dao.MyDaoFactory;
import it.prova.gestioneordini.service.articolo.ArticoloService;
import it.prova.gestioneordini.service.articolo.ArticoloServiceImpl;
import it.prova.gestioneordini.service.categoria.CategoriaService;
import it.prova.gestioneordini.service.categoria.CategoriaServiceImpl;
import it.prova.gestioneordini.service.ordine.OrdineService;
import it.prova.gestioneordini.service.ordine.OrdineServiceImpl;

public class MyServiceFactory {

	private static ArticoloService articoloServiceInstance = null;
	private static CategoriaService categoriaServiceInstance = null;
	private static OrdineService ordineServiceInstance = null;

	public static ArticoloService getArticoloServiceInstance() {
		if (articoloServiceInstance == null)
			articoloServiceInstance = new ArticoloServiceImpl();

		articoloServiceInstance.setArticoloDAO(MyDaoFactory.getArticoloDAOInstance());
		articoloServiceInstance.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());

		return articoloServiceInstance;
	}

	public static CategoriaService getCategoriaServiceInstance() {
		if (categoriaServiceInstance == null)
			categoriaServiceInstance = new CategoriaServiceImpl();

		categoriaServiceInstance.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());

		return categoriaServiceInstance;
	}

	public static OrdineService getOrdineServiceInstance() {
		if (ordineServiceInstance == null)
			ordineServiceInstance = new OrdineServiceImpl();

		ordineServiceInstance.setOrdineDAO(MyDaoFactory.getOrdineDAOInstance());
		ordineServiceInstance.setArticoloDAO(MyDaoFactory.getArticoloDAOInstance());
		ordineServiceInstance.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());

		return ordineServiceInstance;
	}

}
