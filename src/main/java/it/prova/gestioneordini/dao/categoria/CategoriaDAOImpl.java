package it.prova.gestioneordini.dao.categoria;

import java.util.Calendar;
import java.util.Date;
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

	@Override
	public List<Integer> findAllCodiceByDate(Date dateInput) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateInput);
		TypedQuery<Integer> query = entityManager.createQuery(
				"select distinct c.codice from Categoria c join c.articoli a join a.ordine o where EXTRACT(YEAR from o.dataSpedizione) =:annoInput and EXTRACT(MONTH from o.dataSpedizione) =:meseInput",
				Integer.class);
		query.setParameter("annoInput", cal.get(Calendar.YEAR));
		query.setParameter("meseInput", cal.get(Calendar.MONTH) + 1);

		return query.getResultList();
	}

}
