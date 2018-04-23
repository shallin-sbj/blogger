package com.blogger.blogger.vo;

import com.blogger.blogger.domain.Catalog;
import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogVO implements Serializable {

    private String username;

    private Catalog catalog;

}
