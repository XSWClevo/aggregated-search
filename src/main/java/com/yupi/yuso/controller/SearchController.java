package com.yupi.yuso.controller;

import com.yupi.yuso.annotation.RateLimiter;
import com.yupi.yuso.common.BaseResponse;
import com.yupi.yuso.common.ResultUtils;
import com.yupi.yuso.model.dto.search.SearchRequest;
import com.yupi.yuso.model.enums.LimitType;
import com.yupi.yuso.model.vo.SearchVO;
import com.yupi.yuso.service.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @RateLimiter(time = 10, count = 3, limitType = LimitType.IP) //10秒内允许访问三次
    @PostMapping("/all")
    public BaseResponse<SearchVO> getPicture(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchService.search(searchRequest, request));
    }
}
