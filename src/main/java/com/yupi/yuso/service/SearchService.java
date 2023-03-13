package com.yupi.yuso.service;

import com.yupi.yuso.model.dto.search.SearchRequest;
import com.yupi.yuso.model.vo.SearchVO;

import javax.servlet.http.HttpServletRequest;

public interface SearchService {

    SearchVO search(SearchRequest searchRequest, HttpServletRequest request);
}
