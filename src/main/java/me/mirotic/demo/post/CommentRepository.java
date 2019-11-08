package me.mirotic.demo.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {

    Optional<Comment> findByPost_Title(String postTitle);

    @Query("SELECT c FROM Comment AS c WHERE c.content = :content")
    Optional<Comment> findByContent(String content);

    @EntityGraph(attributePaths = "post")
    Optional<Comment> getByContent(String content);

    <T> List<T> findByPost_Id(Long postId, Class<T> type);
}
