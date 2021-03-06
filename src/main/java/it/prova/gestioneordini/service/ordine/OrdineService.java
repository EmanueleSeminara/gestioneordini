package it.prova.gestioneordini.service.ordine;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.dao.ordine.OrdineDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface OrdineService {
	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public Ordine caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;

	public void collegaOrdineEArticolo(Articolo articoloInstance, Ordine ordineInstance) throws Exception;

	public void scollegaOrdineEArticolo(Long ordineId) throws Exception;

	public void rimuoviForzatamente(Ordine ordineInstance) throws Exception;

	public List<Ordine> cercaTuttiGliOrdiniConCategoria(Categoria categoriaInput) throws Exception;

	public Ordine cercaOrdinePiuRecenteConCategoria(Categoria categoriaInput) throws Exception;

	public List<String> cercaTuttiGliIndirizziConSerialeCheContiene(String serialeInput) throws Exception;

	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);

	public void setArticoloDAO(ArticoloDAO articoloDAO);

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

}
