package cn.sawyer.spider.chongqing.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseInfo {
    public static List<String> areas;
    public static String originUrl;
    public static String reshoufangUrl;
    public static String quarterUrl;
    public static List<String> quarterUrlList = new ArrayList<String>();
    public static String GaodeApiKey;
    public static String GaodeLocationConvertApiUrl;
    public static String[] ua = {
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
            "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
            "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"
    };

    static {
        areas = new ArrayList<String>();
        GaodeLocationConvertApiUrl = "https://restapi.amap.com/v3/geocode/geo?";
        reshoufangUrl = "https://cq.lianjia.com/ershoufang";
        originUrl = "https://cq.lianjia.com";
        quarterUrl = "https://cq.lianjia.com/xiaoqu";
        GaodeApiKey = "fa131e8784932121cd34e182a8b8596b";
        Document doc = null;
        try {
            doc = Jsoup.connect(reshoufangUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String selector = "body > div:nth-child(12) > div > div.position > dl:nth-child(2) > dd > div:nth-child(1) > div > a:nth-child";
        String open = "(";
        String close = ")";
        String[] areaSelectors = new String[28];

        for (int i = 0; i < 28; i++) {
            areaSelectors[i] = selector + open + (i + 1) + close;
        }
        assert doc != null;
//        for (String areaSelector : areaSelectors) {
//            assert !areaSelector.equals("");
//            String href = doc.select(areaSelector).attr("href");
//            areas.add(href.split("/")[2]);
//            quarterUrlList.add(quarterUrl + "/" + href.split("/")[2]);
//        }
        System.out.println("init");
    }
}
