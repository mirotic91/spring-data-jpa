package me.mirotic.demo.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString(exclude = "comments")
@NoArgsConstructor
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private Integer hit;

    private Boolean block;

    private LocalDateTime create;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @Builder
    private Post(String title) {
        this.title = title;
        this.hit = 0;
        this.block = false;
        this.create = LocalDateTime.now();
    }
}
