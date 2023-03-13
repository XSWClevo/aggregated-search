package com.yupi.yuso.model.vo;

import com.yupi.yuso.model.entity.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Picture> pictureList;

    private List<UserVO> userList;

    private List<PostVO> postList;

}
