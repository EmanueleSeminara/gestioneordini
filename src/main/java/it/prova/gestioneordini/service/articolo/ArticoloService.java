package it.prova.gestioneordini.service.articolo;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface ArticoloService {
	public List<Articolo> listAll() throws Exception;

	public Articolo caricaSingoloElemento(Long id) throws Exception;

	public Articolo caricaSingoloElementoEagerCategorieOrdini(Long id) throws Exception;

	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void rimuovi(Articolo articoloInstance) throws Exception;

	public void collegaArticoloECategoria(Articolo articoloInstance, Categoria categoriaInstance) throws Exception;

	public void scollegaArticoloECategoria(Long articoloId, Long categoriaId) throws Exception;

	public Long sommaPrezziPerCategoria(Categoria categoriaInput) throws Exception;

	public Long sommaPrezziPernomeDestinatarioInput(String nomeDestinatarioInputInput) throws Exception;

	public void rimuoviForzatamente(Articolo articoloInstance) throws Exception;

	// per injection
	public void setArticoloDAO(ArticoloDAO articoloDAO);

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
}
