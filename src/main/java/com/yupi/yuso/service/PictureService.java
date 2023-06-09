package com.yupi.yuso.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.model.entity.Picture;

public interface PictureService {

    /**
     * 通过URL获取图片与图片title
     * @return
     */
    Page<Picture> searchPicture(long current, long pageSize, String searchText);
}
