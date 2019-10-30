package me.mirotic.demo;

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
        Comment comment = Comment.builder().content("test").post(post).build();

        postRepository.save(post);
        commentRepository.save(comment);
    }

    @Test
    void relation() {
        Optional<Comment> comment = commentRepository.findByPost_Title("spring data");
        log.debug("{}", comment);

        assertThat(comment.isPresent()).isTrue();
        comment.ifPresent(x -> log.debug(x.getPost().getTitle()));
    }
}