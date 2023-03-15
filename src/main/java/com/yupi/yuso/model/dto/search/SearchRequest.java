package com.yupi.yuso.model.dto.search;

import com.yupi.yuso.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRequest implements Serializable {

    private String searchText;

    private Integer pageNum;

    private Integer pageSize;

    private static final long serialVersionUID = 1L;

}
