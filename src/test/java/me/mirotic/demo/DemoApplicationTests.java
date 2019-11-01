package me.mirotic.demo;

import lombok.extern.slf4j.Slf4j;
import me.mirotic.demo.post.Comment;
import me.mirotic.demo.post.Post;
import me.mirotic.demo.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void init() {
        Post post = Post.builder().title("spring data").build();
        for (int i = 1; i <= 5; i++) {
            Comment.builder().content("test " + i).post(post).build();
        }
        postRepository.save(post);
    }

    @Test
    void fetch() {
        Post post = postRepository.findFetchWithComments(1L).orElseThrow(RuntimeException::new);
        Set<Comment> comments = post.getComments();

        comments.forEach(comment -> log.debug("{}", comment));
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(5);
    }

}
