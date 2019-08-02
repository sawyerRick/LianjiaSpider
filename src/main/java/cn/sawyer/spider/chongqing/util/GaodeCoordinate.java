package cn.sawyer.spider.chongqing.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import top.jfunc.json.impl.JSONArray;
import top.jfunc.json.impl.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * @program: myspider
 * @description: Convert loaction to Gaode coordinate
 * @author: sawyer
 * @create: 2019-07-31 18:13
 **/
public class GaodeCoordinate {
    public static String convert(String city, String location) {
        Document doc;
        String gaodeLocation = null;
        JSONObject jsonObject;
        try {
            doc =  Jsoup.connect(fillUrl(BaseInfo.GaodeLocationConvertApiUrl, location, city, BaseInfo.GaodeApiKey)).ignoreContentType(true)
                    .get();
            System.out.println(doc.text());
            jsonObject = new JSONObject(doc.text());
            gaodeLocation = new JSONObject(new JSONArray((com.alibaba.fastjson.JSONArray) jsonObject.get("geocodes")).get(0).toString()).get("location").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gaodeLocation;
    }

    public static String fillUrl(String url, String addr, String city, String key) {
//        key=您的key&address=方恒国际中心A座&city=北京
        System.out.println(url + String.format("key=%s&address=%s&city=%s", key, addr, city));
        return url + String.format("key=%s&address=%s&city=%s", key, addr, city);
    }
}
