package com.estoque.dao;

import com.estoque.model.Peca;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Data Access Object for Peca entity.
 * Handles all database operations for mechanical parts.
 */
public class PecaDAO {
    
    /**
     * Saves a new part or updates an existing one.
     * @param peca the part to save
     * @return the saved part with generated ID
     */
    public Peca save(Peca peca) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(peca);
            transaction.commit();
            return peca;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving part: " + e.getMessage(), e);
        }
    }
    
    /**
     * Finds a part by its ID.
     * @param id the part ID
     * @return the part or null if not found
     */
    public Peca findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Peca.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Error finding part by ID: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves all parts from the database.
     * @return list of all parts
     */
    public List<Peca> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Peca> query = session.createQuery("FROM Peca ORDER BY nome", Peca.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all parts: " + e.getMessage(), e);
        }
    }
    
    /**
     * Searches parts by name (case-insensitive partial match).
     * @param nome the name to search for
     * @return list of matching parts
     */
    public List<Peca> findByNome(String nome) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Peca> query = session.createQuery(
                "FROM Peca WHERE LOWER(nome) LIKE LOWER(:nome) ORDER BY nome", Peca.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error searching parts by name: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves parts with low stock (below minimum threshold).
     * @return list of parts with low stock
     */
    public List<Peca> findEstoqueBaixo() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Peca> query = session.createQuery(
                "FROM Peca WHERE quantidadeEstoque <= quantidadeMinima ORDER BY nome", Peca.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding low stock parts: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deletes a part from the database.
     * @param id the ID of the part to delete
     * @return true if deletion was successful
     */
    public boolean delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Peca peca = session.get(Peca.class, id);
            if (peca != null) {
                session.delete(peca);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting part: " + e.getMessage(), e);
        }
    }
    
    /**
     * Updates stock quantity for a specific part.
     * @param id the part ID
     * @param novaQuantidade the new stock quantity
     * @return true if update was successful
     */
    public boolean updateEstoque(Long id, Integer novaQuantidade) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Peca peca = session.get(Peca.class, id);
            if (peca != null) {
                peca.setQuantidadeEstoque(novaQuantidade);
                session.update(peca);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating stock: " + e.getMessage(), e);
        }
    }
} 