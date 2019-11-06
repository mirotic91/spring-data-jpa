package me.mirotic.demo.post;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

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

    @Test
    void orderByLength() {
//        List<Post> posts = postRepository.findAllSort(Sort.by(Sort.Direction.DESC, "LENGTH(title)")); // @Query 정렬시 프로퍼티가 아니므로 에러 발생
        List<Post> posts = postRepository.findAllSort(JpaSort.unsafe(Sort.Direction.DESC, "LENGTH(title)")); // @Query 함수 정렬시 unsafe 사용
        assertThat(posts).isNotEmpty();

        int first = posts.get(0).getTitle().length();
        int last = posts.get(posts.size() - 1).getTitle().length();
        assertThat(first).isGreaterThanOrEqualTo(last);
    }
}