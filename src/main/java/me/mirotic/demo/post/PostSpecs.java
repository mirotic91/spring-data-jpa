package me.mirotic.demo.post;

import org.springframework.data.jpa.domain.Specification;

public class PostSpecs {

    public static Specification<Post> isBlock() {
        return (Specification<Post>) (root, query, builder)
                -> builder.isTrue(root.get(Post_.block));
    }
}
