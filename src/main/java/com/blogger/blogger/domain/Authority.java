package com.blogger.blogger.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 权限
 */
@Entity
@Data
public class Authority {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 用户的唯一标识

    @Column(nullable = false)
    private String name;

}
