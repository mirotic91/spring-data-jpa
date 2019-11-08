package me.mirotic.demo.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    private int up;

    private int down;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;

    @Builder
    private Comment(String content, int up, int down, Post post) {
        post.getComments().add(this);
        this.post = post;
        this.up = up;
        this.down = down;
        this.content = content;
    }
}
