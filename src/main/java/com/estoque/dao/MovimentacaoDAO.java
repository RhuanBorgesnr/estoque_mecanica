package com.estoque.dao;

import com.estoque.model.Movimentacao;
import com.estoque.model.Peca;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Access Object for Movimentacao entity.
 * Handles all database operations for stock movements.
 */
public class MovimentacaoDAO {
    
    /**
     * Saves a new stock movement.
     * @param movimentacao the movement to save
     * @return the saved movement with generated ID
     */
    public Movimentacao save(Movimentacao movimentacao) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(movimentacao);
            transaction.commit();
            return movimentacao;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving movement: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves all movements for a specific part.
     * @param peca the part to get movements for
     * @return list of movements for the part
     */
    public List<Movimentacao> findByPeca(Peca peca) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movimentacao> query = session.createQuery(
                "FROM Movimentacao m JOIN FETCH m.peca WHERE m.peca = :peca ORDER BY m.dataMovimentacao DESC", Movimentacao.class);
            query.setParameter("peca", peca);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding movements by part: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves movements within a date range.
     * @param dataInicio start date
     * @param dataFim end date
     * @return list of movements in the date range
     */
    public List<Movimentacao> findByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDateTime inicio = dataInicio.atStartOfDay();
            LocalDateTime fim = dataFim.atTime(LocalTime.MAX);
            
            Query<Movimentacao> query = session.createQuery(
                "FROM Movimentacao m JOIN FETCH m.peca WHERE m.dataMovimentacao BETWEEN :inicio AND :fim ORDER BY m.dataMovimentacao DESC", 
                Movimentacao.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding movements by period: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves all movements ordered by date (most recent first).
     * @return list of all movements
     */
    public List<Movimentacao> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movimentacao> query = session.createQuery(
                "FROM Movimentacao m JOIN FETCH m.peca ORDER BY m.dataMovimentacao DESC", Movimentacao.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all movements: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves recent movements (last 50).
     * @return list of recent movements
     */
    public List<Movimentacao> findRecent() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movimentacao> query = session.createQuery(
                "FROM Movimentacao m JOIN FETCH m.peca ORDER BY m.dataMovimentacao DESC", Movimentacao.class);
            query.setMaxResults(50);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving recent movements: " + e.getMessage(), e);
        }
    }
} 