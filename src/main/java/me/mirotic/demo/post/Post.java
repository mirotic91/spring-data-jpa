package me.mirotic.demo.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString(exclude = "comments")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private Integer hit;

    private Boolean block;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    private String createdBy;

    private String updatedBy;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @Builder
    private Post(String title) {
        this.title = title;
        this.hit = 0;
        this.block = false;
    }

    @PrePersist
    private void prePersist() {
        this.createdBy = "creator";
        this.updatedBy = "creator";
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedBy = "updater";
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
