package cn.sawyer.spider.chongqing.thread;

import cn.sawyer.spider.chongqing.pojo.QuarterInfo;
import cn.sawyer.spider.chongqing.util.GaodeCoordinate;
import cn.sawyer.spider.chongqing.util.QuarterSelector;
import cn.sawyer.spider.util.CoordinateTransformUtils;
import cn.sawyer.spider.util.Point;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static cn.sawyer.spider.chongqing.util.BaseInfo.ua;

/**
 * @program: myspider
 * @description: 爬取小区信息的线程
 * @author: sawyer
 * @create: 2019-08-02 15:43
 **/
public class QuarterInfoThread extends Thread {
    private List<String> list;
    private MongoCollection<org.bson.Document> collection;
    private MongoCollection<org.bson.Document> urlCollection;

    public QuarterInfoThread(List<String> list, MongoCollection<org.bson.Document> collection, MongoCollection<org.bson.Document> urlCollection) {
        this.list = list;
        this.collection = collection;
        this.urlCollection = urlCollection;
    }

    @Override
    public void run() {
        double complete = 0;
        for (String item : list) {
            System.out.println(item);
            System.out.println(Thread.currentThread() + "is " + String.format("%.2f %% completed", (complete++ / ((double) list.size())) * 100 ));
            String url = item.split(" ")[0];
            String nums = item.split(" ")[1];
            try {
                Document doc = Jsoup.connect(url).userAgent(ua[new Random().nextInt(ua.length)]).get();
                QuarterInfo quarterInfo = new QuarterInfo();
                quarterInfo.setAge(doc.select(QuarterSelector.ageSelector).text().split("年")[0]);
                quarterInfo.setPropertyPrice(doc.select(QuarterSelector.propertyPriceSelector).text().split("元")[0]);
                quarterInfo.setLocation(doc.select(QuarterSelector.locationSelector).text());
                quarterInfo.setAvgPrice(doc.select(QuarterSelector.avgPriceSelector).text().split("元")[0]);
                quarterInfo.setCompany(doc.select(QuarterSelector.companySelector).text());
                quarterInfo.setName(doc.select(QuarterSelector.nameSelector).text());
                quarterInfo.setHouseSellingNums(nums);
                if (!quarterInfo.getName().isEmpty()) {
                    String location = GaodeCoordinate.convert("重庆", quarterInfo.getLocation());
                    String x = location.split(",")[0];
                    String y = location.split(",")[1];
                    quarterInfo.setLongitude(x);
                    quarterInfo.setLatitude(y);
                    Point point = CoordinateTransformUtils.gcj02ToWgs84(Double.parseDouble(x), Double.parseDouble(y));
                    quarterInfo.setWgsx(Double.toString(point.getLng()));
                    quarterInfo.setWgsy(Double.toString(point.getLat()));

                    System.out.println("小区名: " + quarterInfo.getName());
                    System.out.println("在售房源数量: " + quarterInfo.getHouseSellingNums());
                    System.out.println("年份: " + quarterInfo.getAge());
                    System.out.println("物业公司: " + quarterInfo.getCompany());
                    System.out.println("均价: " + quarterInfo.getAvgPrice());
                    System.out.println("位置: " + quarterInfo.getLocation());
                    System.out.println("物业费用: " + quarterInfo.getPropertyPrice());
                    System.out.println("x:" + quarterInfo.getLongitude());
                    System.out.println("y:" + quarterInfo.getLatitude());
                    System.out.println("wgs84x:" + quarterInfo.getWgsx());
                    System.out.println("wgs84x:" + quarterInfo.getWgsy());
                    org.bson.Document document = new org.bson.Document("title", "MongoDB")
                            .append("小区名", quarterInfo.getName())
                            .append("在售房源数量", quarterInfo.getHouseSellingNums())
                            .append("年份:", quarterInfo.getAge())
                            .append("物业公司", quarterInfo.getCompany())
                            .append("均价", quarterInfo.getAvgPrice())
                            .append("位置", quarterInfo.getLocation())
                            .append("物业费用", quarterInfo.getPropertyPrice())
                            .append("x", quarterInfo.getLongitude())
                            .append("y",quarterInfo.getLatitude())
                            .append("wgs84x", quarterInfo.getWgsx())
                            .append("wgs84x", quarterInfo.getWgsy());
                    collection.insertOne(document);
                    urlCollection.deleteOne(Filters.eq("herfNum", item));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
