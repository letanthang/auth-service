package com.example.authservice.infrastructure.repository;

import com.example.authservice.domain.entity.Token;
import com.example.authservice.domain.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.Instant;
import java.util.Optional;

public class MySQLTokenRepository implements TokenRepository {
    private final EntityManagerFactory emf;

    public MySQLTokenRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Optional<Token> getTokenById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Token token = em.find(Token.class, id);
            return Optional.ofNullable(token);
        }
    }

    @Override
    public Optional<Token> getTokenByToken(String token) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Token> query = em.createQuery(
                    "SELECT u FROM Token u WHERE u.token = :token", Token.class);
            query.setParameter("token", token);

            var results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        }
    }

    @Override
    public Optional<Token> getTokenByEmail(String email) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Token> query = em.createQuery(
                    "SELECT u FROM Token u WHERE u.email = :email", Token.class);
            query.setParameter("email", email);

            var token = query.getSingleResult();
            return Optional.ofNullable(token);
        }
    }

    @Override
    public void addToken(Token token) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(token);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean deleteToken(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Token authUser = em.find(Token.class, id);
            if (authUser != null) {
                em.remove(authUser);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public int deleteExpiredTokens() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Token u WHERE u.expired_at < :now");
            query.setParameter("now", Instant.now());
            int rowsDeleted = query.executeUpdate();

            em.getTransaction().commit();
            return rowsDeleted;
        }
    }
}
