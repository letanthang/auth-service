package com.example.authservice.repository;

import com.example.authservice.domain.Token;
import com.example.authservice.exception.NotFoundTokenException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class MySQLTokenRepository implements TokenRepository {
    private final EntityManagerFactory emf;

    public MySQLTokenRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void addToken(Token token) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(token);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Token> getTokenById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Token token = em.find(Token.class, id);
            return Optional.ofNullable(token);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Token> getTokenByToken(String token) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Token> query = em.createQuery(
                    "SELECT u FROM Token u WHERE u.token = :token", Token.class);
            query.setParameter("token", token);

            var tokenObj = query.getSingleResult();
            return Optional.ofNullable(tokenObj);
        } catch (NoResultException e) {
            throw new NotFoundTokenException("token not found");
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Token> getTokenByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Token> query = em.createQuery(
                    "SELECT u FROM Token u WHERE u.email = :email", Token.class);
            query.setParameter("email", email);

            var token = query.getSingleResult();
            return Optional.ofNullable(token);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteToken(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Token authUser = em.find(Token.class, id);
            if (authUser != null) {
                em.remove(authUser);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
}
