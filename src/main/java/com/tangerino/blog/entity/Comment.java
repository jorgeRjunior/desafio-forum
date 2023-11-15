package com.tangerino.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tb_comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Post post;

    private Date dataInsert = new Date();

}
