package me.mirotic.demo.post;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
    void init() {
        Post post = Post.builder().title("spring data").build();
        Comment.builder().content("test").post(post).build();

        postRepository.save(post);
    }

    @Test
    void relation() {
        Optional<Comment> comment = commentRepository.findByPost_Title("spring data");
        log.debug("{}", comment);

        assertThat(comment.isPresent()).isTrue();
        comment.ifPresent(x -> log.debug(x.getPost().getTitle()));
    }

    @Test
    void jpql() {
        Comment comment = commentRepository.findByContent("test").orElseThrow(RuntimeException::new);
        assertThat(comment).isNotNull();
    }

    @Test
    void querydsl() {
        QComment comment = QComment.comment;
        Predicate predicate = comment.content.startsWith("test")
                .or(comment.content.containsIgnoreCase("spring"));

        Optional<Comment> optionalComment = commentRepository.findOne(predicate);
        assertThat(optionalComment).isNotEmpty();
    }
}