package it.prova.gestioneordini.service.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface CategoriaService {
	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public Categoria caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Categoria categoriaInstance) throws Exception;

	public List<Categoria> cercaTutteLeCategorieDistinteConOrdine(Ordine ordineInput) throws Exception;

	public List<Integer> cercaTuttiICodiciDistintiPerData(Date dateInput) throws Exception;

	// per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
}
