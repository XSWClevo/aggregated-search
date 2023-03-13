package com.yupi.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.common.BaseResponse;
import com.yupi.yuso.common.ErrorCode;
import com.yupi.yuso.common.ResultUtils;
import com.yupi.yuso.exception.ThrowUtils;
import com.yupi.yuso.model.dto.picture.PictureRequest;
import com.yupi.yuso.model.entity.Picture;
import com.yupi.yuso.service.PictureService;
import com.yupi.yuso.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private PictureService pictureService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> getPicture(PictureRequest pictureRequest, HttpServletRequest request) {
        long current = pictureRequest.getCurrent();
        long pageSize = pictureRequest.getPageSize();
        String searchText = pictureRequest.getSearchText();
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = pictureService.searchPicture(current, pageSize, searchText);
        return ResultUtils.success(picturePage);

    }
}
