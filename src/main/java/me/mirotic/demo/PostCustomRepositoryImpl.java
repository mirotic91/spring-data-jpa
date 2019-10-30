package me.mirotic.demo;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class PostCustomRepositoryImpl implements PostCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Post> findAllCustom() {
        return entityManager.createQuery("SELECT p FROM Post AS p").getResultList();
    }
}
