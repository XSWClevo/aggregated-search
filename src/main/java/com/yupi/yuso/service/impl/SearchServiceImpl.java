package com.yupi.yuso.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.common.ErrorCode;
import com.yupi.yuso.datasource.AggDataSource;
import com.yupi.yuso.datasource.DataSourceRegistry;
import com.yupi.yuso.exception.BusinessException;
import com.yupi.yuso.model.dto.post.PostQueryRequest;
import com.yupi.yuso.model.dto.search.SearchRequest;
import com.yupi.yuso.model.dto.user.UserQueryRequest;
import com.yupi.yuso.model.entity.Picture;
import com.yupi.yuso.model.entity.Post;
import com.yupi.yuso.model.enums.SearchTypeEnum;
import com.yupi.yuso.model.vo.PostVO;
import com.yupi.yuso.model.vo.SearchVO;
import com.yupi.yuso.model.vo.UserVO;
import com.yupi.yuso.service.PictureService;
import com.yupi.yuso.service.PostService;
import com.yupi.yuso.service.SearchService;
import com.yupi.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private PictureService pictureService;

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    @Override
    public SearchVO search(SearchRequest searchRequest, HttpServletRequest request) {
        log.info("查询参数为：{}", JSONUtil.toJsonStr(searchRequest));
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        String searchText = searchRequest.getSearchText();
        Integer current = searchRequest.getPageNum();
        Integer pageSize = searchRequest.getPageSize();
        // 为空就代表查询所有的
        if (ObjectUtil.isEmpty(searchTypeEnum)) {
            CompletableFuture<Page<Picture>> pictureFuture = CompletableFuture.supplyAsync(
                    () -> pictureService.searchPicture(current, pageSize, searchText)
            );

            CompletableFuture<Page<UserVO>> userVOFuture = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                userQueryRequest.setCurrent(current);
                userQueryRequest.setPageSize(pageSize);
                return userService.listUserByPage(userQueryRequest);
            });

            CompletableFuture<Page<PostVO>> postVOFuture = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                postQueryRequest.setCurrent(current);
                postQueryRequest.setPageSize(pageSize);
                Page<Post> postPage = postService.searchFromEs(postQueryRequest);
                return postService.getPostVOPage(postPage, request);
                // return postService.listPostByPage(postQueryRequest, request);
            });
            // 等待所有任务执行完毕
            CompletableFuture.allOf(pictureFuture, userVOFuture, postVOFuture).join();
            try {
                List<Picture> pictureList = pictureFuture.get().getRecords();
                List<UserVO> userVOList = userVOFuture.get().getRecords();
                List<PostVO> postVOList = postVOFuture.get().getRecords();
                return new SearchVO(pictureList, userVOList, postVOList);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        }

        // 逻辑走到这里就代表type不为空
        SearchVO searchVO = new SearchVO();
        AggDataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);
        Page<?> page = dataSource.doSearch(searchText, current, pageSize);
        searchVO.setDataList(page.getRecords());
        searchVO.setType(type);
        return searchVO;
    }
}
