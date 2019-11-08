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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;

    @Builder
    private Comment(String content, Post post) {
        post.getComments().add(this);
        this.post = post;
        this.content = content;
    }
}
