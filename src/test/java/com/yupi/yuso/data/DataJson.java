package com.yupi.yuso.data;

import cn.hutool.json.JSONUtil;
import com.yupi.yuso.model.entity.Picture;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@SpringBootTest
public class DataJson {

    //    @Test
    public static void main(String[] args) throws IOException {
        int current = 1;
        String url = String.format("https://cn.bing.com/images/search?q=鸡你太美&first=%d", current);
        Document doc = Jsoup.connect(url).get();
        Elements newsHeadlines = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : newsHeadlines) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            System.out.println(title);
            System.out.println(murl);
            Picture picture = new Picture(title, murl);
            pictureList.add(picture);
        }
    }

//    public static void main(String[] args) {
//        String paramJson = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
//        String url = "https://www.code-nav.cn/api/post/search/page/vo";
//        String result = HttpRequest.post(url).body(paramJson).execute().body();
//        Map map = JSONUtil.toBean(result, Map.class);
//        Integer code = (Integer) map.get("code");
//        if (code != 0) {
//            throw new RuntimeException("请求响应异常");
//        }
//        JSONObject data = (JSONObject) map.get("data");
//        if (ObjUtil.isEmpty(data)) {
//            throw new NullPointerException();
//        }
//        JSONArray jsonArray = (JSONArray) data.get("records");
//        if (CollUtil.isEmpty(jsonArray)) {
//            throw new NullPointerException();
//        }
//        List<Post> postList = new ArrayList<>();
//        for (Object json : jsonArray) {
//            JSONObject tempRecord = (JSONObject) json;
//            JSONArray tags = (JSONArray) tempRecord.get("tags");
//            List<String> tagList = tags.toList(String.class);
//            Post post = new Post();
//            if (CollUtil.isNotEmpty(tagList)) {
//                post.setTags(JSONUtil.toJsonStr(tagList));
//            }
//            String content = tempRecord.getStr("content");
//
//            post.setUserId(999L);
//            String title = tempRecord.getStr("title");
//            if (StrUtil.isNotEmpty(title)) {
//                post.setTitle(title);
//            }
//            postList.add(post);
//        }
//
//        System.out.println(map);
//    }
}
