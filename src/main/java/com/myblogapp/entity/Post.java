package com.myblogapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;// when we use @Lob then it can be more than 5000 words otherwise
    // in string only 255 character.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
   private Set<Comment> comments = new HashSet<>();
}
