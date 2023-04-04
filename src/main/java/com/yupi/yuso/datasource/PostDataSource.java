package com.yupi.yuso.datasource;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.common.ErrorCode;
import com.yupi.yuso.exception.BusinessException;
import com.yupi.yuso.model.dto.post.PostQueryRequest;
import com.yupi.yuso.model.entity.Post;
import com.yupi.yuso.model.vo.PostVO;
import com.yupi.yuso.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class PostDataSource implements AggDataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, Integer pageNum, Integer pageSize) {
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjUtil.isEmpty(servletRequestAttributes) || ObjUtil.isEmpty(Objects.requireNonNull(servletRequestAttributes).getRequest())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();

        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);

        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage, request);
    }
}
