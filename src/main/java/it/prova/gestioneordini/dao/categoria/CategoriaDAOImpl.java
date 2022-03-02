package it.prova.gestioneordini.dao.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public class CategoriaDAOImpl implements CategoriaDAO {
	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public Categoria findByIdFetchingArticoli(Long idInput) throws Exception {
		TypedQuery<Categoria> query = entityManager
				.createQuery("select c from Categoria c left join fetch c.articoli a where c.id= :idCategoria",
						Categoria.class)
				.setParameter("idCategoria", idInput);

		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Categoria> findAllDistinctCategoryByOrdine(Ordine ordineInput) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Categoria c join c.articoli a join a.ordine o where o.id= :idOrdine",
				Categoria.class).setParameter("idOrdine", ordineInput.getId());

		return query.getResultList();
	}

}
