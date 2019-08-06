package cn.sawyer.spider;

import cn.sawyer.spider.thread.QuarterInfoThread;
import cn.sawyer.spider.util.QuarterBaseUrls;
import cn.sawyer.spider.util.QuarterSelector;
import cn.sawyer.spider.util.CoordinateTransformUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cn.sawyer.spider.util.BaseInfo.ua;

/**
 * @program: myspider
 * @description: main spider
 * @author: sawyer
 * @create: 2019-07-30 15:23
 **/
public class QuarterSpider {
    private String key = "1ae99ce40ada46d98e078024f24967bb";
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<org.bson.Document> quarterUrlCol;
    private MongoCollection<org.bson.Document> quarterInfoCol;

    public QuarterSpider(String db, String quarterUrlCol, String quarterInfoCol) {
        try {
            this.mongoClient = new MongoClient( "localhost" , 27017 );
            this.mongoDatabase = mongoClient.getDatabase(db);
//            if (this.mongoDatabase.getCollection(quarterUrlCol).count() == 0) {
//                mongoDatabase.createCollection(quarterUrlCol);
//            }
//            if (this.mongoDatabase.getCollection(quarterInfoCol).count() == 0) {
//                mongoDatabase.createCollection(quarterInfoCol);
//            }
            this.quarterUrlCol = mongoDatabase.getCollection(quarterUrlCol);
            this.quarterInfoCol = mongoDatabase.getCollection(quarterInfoCol);
        } catch (Exception e) {
            System.out.println("mongodb 连接失败...");
            e.printStackTrace();
        }
    }

    public MongoCollection<org.bson.Document> getQuarterUrlCol() {
        return quarterUrlCol;
    }

    public void setQuarterUrlCol(String quarterUrlCol) {
        if (this.mongoDatabase.getCollection(quarterUrlCol).count() == 0) {
            mongoDatabase.createCollection(quarterUrlCol);
        }
        this.quarterUrlCol = mongoDatabase.getCollection(quarterUrlCol);
    }

    public MongoCollection<org.bson.Document> getQuarterInfoCol() {
        return quarterInfoCol;
    }


    public void writeToCsv(String[] header, String collection, String filename) throws IOException{
        initHeader(header, filename);
        File outFile = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
        MongoCollection<org.bson.Document> mongoCollection = this.mongoDatabase.getCollection(collection);
        for (org.bson.Document document : mongoCollection.find()) {
//            String x = document.get("x").toString();
//            String y = document.get("y").toString();
//            document.put("wgs84x", Double.toString(CoordinateTransformUtils.gcj02ToWgs84(Double.parseDouble(x), Double.parseDouble(y)).getLng()));
//            document.put("wgs84y", Double.toString(CoordinateTransformUtils.gcj02ToWgs84(Double.parseDouble(x), Double.parseDouble(y)).getLat()));
            for (int i = 0; i < header.length; i++) {
                writer.write(document.get(header[i]).toString() + ",");
            }
            writer.write("\n");
            System.out.println(document);
        }
        writer.close();
        System.out.println(mongoCollection.count());
    }

    public void initHeader(String[] header, String filename) throws IOException{
        File outFile = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        for (int i = 0; i < header.length; i++) {
            writer.write(header[i] + ",");
        }
        writer.write("\n");
        writer.close();
    }

    public String getProxy(String key) {
        String template = String.format("http://mvip.piping.mogumiao.com/proxy/api/get_ip_al?appKey=%s&count=20&expiryDate=0&format=2&newLine=2", key);
        return null;
    }

    public void getAllQuartInfo() {
        List<String> list = new ArrayList<>();

        for (org.bson.Document document : this.quarterUrlCol.find()) {
            list.add(document.get("hrefNum").toString());
        }
        System.out.println("size:" + list.size());
        Thread[] jobs = new Thread[4];
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(new QuarterInfoThread(list.subList(i * list.size() / 4, (i + 1) * list.size() / 4), quarterInfoCol, quarterUrlCol, "成都"));
            thread.start();
            jobs[i] = thread;
        }
        for (Thread job : jobs) {
            try {
                job.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public void fixdb() {
//        for (org.bson.Document document : quarterInfoCol.find()) {
//            String x = document.get("x").toString();
//            String y = document.get("y").toString();
//            System.out.println(x);
//            document.put("wgs84x", Double.toString(CoordinateTransformUtils.gcj02ToWgs84(Double.parseDouble(x), Double.parseDouble(y)).getLng()));
//            document.put("wgs84y", Double.toString(CoordinateTransformUtils.gcj02ToWgs84(Double.parseDouble(x), Double.parseDouble(y)).getLat()));
//        }
//    }
    
    public void getAllQuartUrls() {
        String prefix = "body > div.content > div.leftContent > ul > li:nth-child(";
        String suffix = ") > div.info > div.title > a";
        for (String s : QuarterBaseUrls.chengduUrlList) {
            try {
                int quarterNums = Integer.parseInt(Jsoup.connect(s).userAgent(ua[new Random().nextInt(ua.length)]).get().select(QuarterSelector.quarterNumsSelector).text());
                System.out.println("小区数量:" + quarterNums);
                int pageNums = quarterNums / 20;
                for (int i = 1; i <= pageNums; i++) {
                    String url = s + "pg" + i;
                    System.out.println("url:" + url);
                    Document doc = Jsoup.connect(url).userAgent(ua[new Random().nextInt(ua.length)]).get();
                    for (int j = 1; j <= 20; j++) {
                        String selector = prefix + j + suffix;
                        String href = doc.select(selector).attr("href");
                        String numSelector = String.format("li.clear:nth-child(%d) > div:nth-child(3) > div:nth-child(2) > a:nth-child(1) > span:nth-child(1)", j);
                        if (href.equals("")) {
                            System.out.println("此区域结束...");
                            break;
                        } else {
                            int nums = Integer.parseInt(doc.select(numSelector).text());
                            System.out.println("href:" + href + " 套数：" + nums);

                            this.quarterUrlCol.insertOne(new org.bson.Document("hrefNum", href + " " + nums));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param url 地址
     * @param proxy 代理ip
     * @param port 代理端口
     */
    public void run(String url, String proxy, int port) {

    }
}