package cn.sawyer.spider.chongqing;

import cn.sawyer.spider.chongqing.pojo.QuarterInfo;
import cn.sawyer.spider.chongqing.spider.Spider;
import cn.sawyer.spider.chongqing.util.BaseInfo;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Spider spider = new Spider();
//        spider.run("https://cq.lianjia.com/xiaoqu/3611057154710/", null, 1);
        spider.getAllQuartInfo();
    }
}