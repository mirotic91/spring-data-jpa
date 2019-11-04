package me.mirotic.demo.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    void findAll() throws Exception {
        String title = "spring data";
        Post post = Post.builder().title(title).build();
        postRepository.save(post);

        mockMvc.perform(get("/posts")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "title,desc")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value(title));
    }

    @Test
    void hateoas() throws Exception {
        createPost();

        mockMvc.perform(get("/posts/hateoas")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,desc")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.postList[0].title").value("spring data 100"))
                .andExpect(jsonPath("$._links.first.href").value("http://localhost/posts/hateoas?page=0&size=10&sort=id,desc"))
                .andExpect(jsonPath("$.page.number").value(0));
    }

    private void createPost() {
        for (int i = 1; i <= 100; i++) {
            Post post = Post.builder().title("spring data " + i).build();
            postRepository.save(post);
        }
    }

    @Test
    void getTitle() throws Exception {
        String title = "spring data";
        Post post = Post.builder().title(title).build();
        postRepository.save(post);

        mockMvc.perform(get("/posts/" + post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(title));
    }
}