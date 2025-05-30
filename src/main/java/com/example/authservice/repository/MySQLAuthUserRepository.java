package com.example.authservice.repository;

import com.example.authservice.domain.AuthUser;
import com.example.authservice.exception.NotFoundUserException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class MySQLAuthUserRepository implements AuthUserRepository {
    private final EntityManagerFactory emf;

    public MySQLAuthUserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void addAuthUser(AuthUser authUser) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(authUser);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<AuthUser> getAuthUserById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            AuthUser authUser = em.find(AuthUser.class, id);
            return Optional.ofNullable(authUser);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<AuthUser> getAuthUserByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<AuthUser> query = em.createQuery(
                    "SELECT u FROM AuthUser u WHERE u.email = :email", AuthUser.class);
            query.setParameter("email", email);

            var authUser = query.getSingleResult();
            return Optional.ofNullable(authUser);
        } catch (NoResultException e) {
            throw new NotFoundUserException("user with email not existed");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteAuthUser(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            AuthUser authUser = em.find(AuthUser.class, id);
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

    @Override
    public boolean updateAuthUser(Integer id, AuthUser updatedAuthUser) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            AuthUser existingAuthUser = em.find(AuthUser.class, id);
            if (existingAuthUser != null) {
                existingAuthUser.setEmail(updatedAuthUser.getEmail());
                existingAuthUser.setPassword(updatedAuthUser.getPassword());
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
