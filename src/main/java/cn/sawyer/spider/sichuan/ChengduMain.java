package cn.sawyer.spider.sichuan;

import cn.sawyer.spider.QuarterSpider;

import java.io.IOException;

public class ChengduMain {
    public static void main(String[] args) {
        QuarterSpider spider = new QuarterSpider("lianjiaDB", "chengduQuarterUrlCol", "chengduQuarterInfoCol");
//        spider.getAllQuartUrls();
//        spider.getAllQuartInfo();
        String[] header = {"小区名", "在售房源数量", "年份", "物业公司", "均价", "物业费用", "x", "y", "wgs84x", "wgs84y"};
        try {
            spider.writeToCsv(header, "chengduQuarterInfoCol", "ChengduQuarterInfo.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}