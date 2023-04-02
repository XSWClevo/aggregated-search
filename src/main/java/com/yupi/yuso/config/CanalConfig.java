package com.yupi.yuso.config;

import com.yupi.yuso.esdao.PostEsDao;
import com.yupi.yuso.model.dto.post.PostEsDTO;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

import javax.annotation.Resource;

//@Component
//@CanalTable("post")
public class CanalConfig implements EntryHandler<PostEsDTO> {

    @Resource
    private PostEsDao postEsDao;

    /**
     * mysql中数据有新增时自动执行
     * @param post 新增的数据
     */
    @Override
    public void insert(PostEsDTO post) {
        //把新增数据hotel,添加到ES即可
        postEsDao.save(post);
    }

    /**
     * mysql中数据有修改时自动执行
     * @param before 修改前的数据
     * @param after 修改后的数据
     */
    @Override
    public void update(PostEsDTO before, PostEsDTO after) {
        //把修改数据,更新到ES即可
    }

    /**
     * mysql中数据有删除时自动执行
     * @param post 要删除的数据
     */
    @Override
    public void delete(PostEsDTO post) {
        //把要删除的数据hotel,从ES删除即可
    }
}
