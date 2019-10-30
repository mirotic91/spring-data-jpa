package me.mirotic.demo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@ToString
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(optional = false)
    private Post post;

    @Builder
    private Comment(String content, Post post) {
        this.content = content;
        this.post = post;
    }
}
