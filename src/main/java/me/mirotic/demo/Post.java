package me.mirotic.demo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private Integer hit;

    private Boolean block;

    private LocalDateTime create;

    @Builder
    private Post(String title) {
        this.title = title;
        this.hit = 0;
        this.block = false;
        this.create = LocalDateTime.now();
    }
}
