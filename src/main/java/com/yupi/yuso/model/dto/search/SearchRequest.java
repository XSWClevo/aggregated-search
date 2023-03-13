package com.yupi.yuso.model.dto.search;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRequest implements Serializable {

    private String searchText;

    private Integer current;

    private Integer pageSize;

    private static final long serialVersionUID = 1L;

}
