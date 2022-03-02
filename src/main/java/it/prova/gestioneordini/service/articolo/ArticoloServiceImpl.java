package it.prova.gestioneordini.service.articolo;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.exception.ArticoloConCategoriaException;
import it.prova.gestioneordini.exception.ArticoloSenzaOrdineException;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public class ArticoloServiceImpl implements ArticoloService {
	private ArticoloDAO articoloDAO;
	private CategoriaDAO categoriaDAO;

	@Override
	public List<Articolo> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Articolo caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Articolo caricaSingoloElementoEagerCategorieOrdini(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.findByIdFetchingCategorieOrdini(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void aggiorna(Articolo articoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			articoloDAO.update(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void inserisciNuovo(Articolo articoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			if (articoloInstance.getOrdine() == null || articoloInstance.getOrdine().getId() == null) {
				throw new ArticoloSenzaOrdineException("L'articolo " + articoloInstance.getId()
						+ " non ha un ordine associato, impossibile aggiungerlo");
			}

			// eseguo quello che realmente devo fare
			articoloDAO.insert(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Articolo articoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			Articolo articoloDaRimuovere = articoloDAO.findByIdFetchingCategorieOrdini(articoloInstance.getId());
			if (!articoloDaRimuovere.getCategorie().isEmpty()) {
				throw new ArticoloConCategoriaException("L'articolo " + articoloDaRimuovere.getId()
						+ " è associato ad una o più categorie, impossibile rimuoverlo");
			}

			// eseguo quello che realmente devo fare
			articoloDAO.delete(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void setArticoloDAO(ArticoloDAO articoloDAO) {
		this.articoloDAO = articoloDAO;

	}

	@Override
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;

	}

	@Override
	public void collegaArticoloECategoria(Articolo articoloInstance, Categoria categoriaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			articoloInstance = entityManager.merge(articoloInstance);

			categoriaInstance = entityManager.merge(categoriaInstance);

			articoloInstance.addToCategorie(categoriaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void scollegaArticoloECategoria(Long articoloId, Long categoriaId) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			articoloDAO.setEntityManager(entityManager);

			categoriaDAO.setEntityManager(entityManager);

			Articolo articoloInstance = articoloDAO.get(articoloId);

			Categoria categoriaInstance = categoriaDAO.get(categoriaId);

			articoloInstance.removeFromCategorie(categoriaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Long sommaPrezziPerCategoria(Categoria categoriaInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.sumAllByCategoria(categoriaInput);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Long sommaPrezziPernomeDestinatarioInput(String nomeDestinatarioInputInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.sumAllByDestinatario(nomeDestinatarioInputInput);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuoviForzatamente(Articolo articoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);
			categoriaDAO.setEntityManager(entityManager);

			Articolo articoloDaRimuovere = articoloDAO.findByIdFetchingCategorieOrdini(articoloInstance.getId());
			if (!articoloDaRimuovere.getCategorie().isEmpty()) {
				for (Categoria categoriaItem : articoloDaRimuovere.getCategorie()) {
					Articolo articoloDaRimuovereInstance = articoloDAO.get(articoloDaRimuovere.getId());

					Categoria categoriaInstance = categoriaDAO.get(categoriaItem.getId());

					articoloDaRimuovereInstance.removeFromCategorie(categoriaInstance);
				}
			}
			// eseguo quello che realmente devo fare
			articoloDAO.delete(articoloDaRimuovere);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
