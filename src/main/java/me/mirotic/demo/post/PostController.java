package me.mirotic.demo.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("/posts")
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping("/posts/hateoas")
    public PagedModel<EntityModel<Post>> findAll(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toModel(postRepository.findAll(pageable));
    }

    @GetMapping("/posts/{id}")
    public String getTitle(@PathVariable("id") Post post) {
        return post.getTitle();
    }
}
