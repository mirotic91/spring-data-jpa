package me.mirotic.demo.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPost_Title(String postTitle);

    @Query("SELECT c FROM Comment AS c WHERE c.content = :content")
    Optional<Comment> findByContent(String content);

}
