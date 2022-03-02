package it.prova.gestioneordini.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {
	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();

	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);

	}

	@Override
	public void update(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Ordine input) throws Exception {
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
	public Ordine findByIdFetchingArticoli(Long idInput) throws Exception {
		TypedQuery<Ordine> query = entityManager
				.createQuery("select o from Ordine o left join fetch o.articoli a where o.id= :idOrdine", Ordine.class)
				.setParameter("idOrdine", idInput);

		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> findAllByCategoria(Categoria categoriaInput) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select distinct o from Ordine o join o.articoli a join a.categorie c where c.id= :idCategoria",
				Ordine.class).setParameter("idCategoria", categoriaInput.getId());

		return query.getResultList();
	}

	@Override
	public Ordine findMostRecentByCategoria(Categoria categoriaInput) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o join o.articoli a join a.categorie c where c.id= :idCategoria order by o.dataSpedizione asc",
				Ordine.class).setParameter("idCategoria", categoriaInput.getId());

		return query.getResultList().stream().findFirst().orElse(null);
	}

}
