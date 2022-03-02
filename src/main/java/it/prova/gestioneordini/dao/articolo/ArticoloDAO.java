package it.prova.gestioneordini.dao.articolo;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {
	public Articolo findByIdFetchingCategorieOrdini(Long id) throws Exception;

	public Long sumAllByCategoria(Categoria categoriaInput) throws Exception;
}
