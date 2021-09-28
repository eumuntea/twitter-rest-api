package com.twitter.dl;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Version
    private int version;
}
