package me.mirotic.demo.post;

import lombok.extern.slf4j.Slf4j;
import me.mirotic.demo.post.Post;
import me.mirotic.demo.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void init() {
        for (int i = 1; i <= 10; i++) {
            Post post = Post.builder().title("spring data " + i).build();
            postRepository.save(post);
        }
    }

    @Test
    void naming() {
        List<Post> posts = postRepository.findByTitleContainsOrderByCreateDesc("spring data 1");

        assertThat(posts).isNotEmpty();
        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    void page() {
        Page<Post> posts = postRepository.findAll(PageRequest.of(2, 3));

        assertAll(
                () -> assertThat(posts.getTotalElements()).isEqualTo(10),
                () -> assertThat(posts.getTotalPages()).isEqualTo(4),
                () -> assertThat(posts.getNumber()).isEqualTo(2),
                () -> assertThat(posts.getNumberOfElements()).isEqualTo(3),
                () -> assertThat(posts.getSize()).isEqualTo(3)
        );
    }

    @Test
    void custom() {
        List<Post> posts = postRepository.findAllCustom();
        assertThat(posts.size()).isEqualTo(10);
    }
}