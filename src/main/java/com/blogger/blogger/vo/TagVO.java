package com.blogger.blogger.vo;

import lombok.Data;

@Data
public class TagVO {

    private static final long serialVersionUID = 1L;

    private String name;

    private Long count;

    public TagVO(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
