package com.yupi.yuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuso.model.entity.Picture;
import com.yupi.yuso.service.PictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PictureDataSource implements AggDataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, Integer pageNum, Integer pageSize) {
        return pictureService.searchPicture(pageNum, pageSize, searchText);
    }
}
