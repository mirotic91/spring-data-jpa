package me.mirotic.demo.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository, JpaSpecificationExecutor<Post> {

    List<Post> findByTitleContainsOrderByCreatedDesc(String title);

    @Query("SELECT p FROM Post AS p")
    List<Post> findAllSort(Sort sort);

    @Query("SELECT p FROM Post AS p LEFT JOIN FETCH p.comments WHERE p.id = :id")
    Optional<Post> findFetchWithComments(Long id);

}
