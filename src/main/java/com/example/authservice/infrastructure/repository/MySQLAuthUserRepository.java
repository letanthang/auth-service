package com.example.authservice.infrastructure.repository;

import com.example.authservice.domain.entity.AuthUser;
import com.example.authservice.domain.repository.AuthUserRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class MySQLAuthUserRepository implements AuthUserRepository {
    private final EntityManagerFactory emf;

    public MySQLAuthUserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Optional<AuthUser> getAuthUserById(Integer id) {
        try (var em = emf.createEntityManager()) {
            AuthUser authUser = em.find(AuthUser.class, id);
            return Optional.ofNullable(authUser);
        }
    }

    @Override
    public Optional<AuthUser> getAuthUserByEmail(String email) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<AuthUser> query = em.createQuery(
                    "SELECT u FROM AuthUser u WHERE u.email = :email", AuthUser.class);
            query.setParameter("email", email);

            var results = query.getResultList();
            return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
        }
    }

    @Override
    public void addAuthUser(AuthUser authUser) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(authUser);
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean deleteAuthUser(Integer id) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            AuthUser authUser = em.find(AuthUser.class, id);
            if (authUser != null) {
                em.remove(authUser);
                em.getTransaction().commit();
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean updateAuthUser(AuthUser updatedAuthUser) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(updatedAuthUser);
            em.getTransaction().commit();
            return true;
        }
    }
}
