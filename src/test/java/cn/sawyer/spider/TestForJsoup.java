package cn.sawyer.spider;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import cn.sawyer.spider.util.BaseInfo;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestForJsoup {

    @Test
    public void testParse() throws IOException {
        Document doc = Jsoup.connect(BaseInfo.reshoufangUrl).get();
        Element element = doc.select("#content > div.leftContent > ul > li:nth-child(2) > div.info.clear > div.title > a").first();

        String selector = "body > div:nth-child(12) > div > div.position > dl:nth-child(2) > dd > div:nth-child(1) > div > a:nth-child";
        String open = "(";
        String close = ")";
        String[] areaSelectors = new String[29];

        for (int i = 0; i < 29; i++) {
            areaSelectors[i] = selector + open + (i + 1) + close;
        }
        for (String areaSelector : areaSelectors) {
            System.out.println(doc.select(areaSelector).attr("href"));
        }
    }

    @Test
    public void testMongo() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("lianjiaDB");
            System.out.println("Connect to database successfully");

            MongoCollection<org.bson.Document> collection = mongoDatabase.getCollection("quarterInfoCol");
            System.out.println("urlNum: " + collection.count());
            for (org.bson.Document document : collection.find()) {
                System.out.println(document.get("x").toString().split("\\.")[1].substring(0, 6));
            }
//            List<String> list = new ArrayList<String>();
//            for (org.bson.Document document : collection.find()) {
//                System.out.println(document.toString());
//            }
//            System.out.println("集合 test 选择成功");
//            //插入文档
//            /**
//             * 1. 创建文档 org.bson.Document 参数为key-value的格式
//             * 2. 创建文档集合List<Document>
//             * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
//             * */
//            org.bson.Document document = new org.bson.Document("title", "MongoDB").
//                    append("description", "database").
//                    append("likes", 100).
//                    append("by", "Fly");
//            List<org.bson.Document> documents = new ArrayList<org.bson.Document>();
//            documents.add(document);
//            collection.insertMany(documents);
//            System.out.println("文档插入成功");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Test
    public void writeToCsv() {
        String[] header = {"1", "2", "3"};
        File outFile = new File("outfile.csv");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
            for (int i = 0; i < header.length; i++) {
                writer.write(header[i] + ",");
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkChengdu() {
        // 连接到 mongodb 服务
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("lianjiaDB");
        System.out.println("Connect to database successfully");

        MongoCollection<org.bson.Document> infoCollection = mongoDatabase.getCollection("chengduQuarterInfoCol");
        MongoCollection<org.bson.Document> urlCollection = mongoDatabase.getCollection("chengduQuarterUrlCol");
        System.out.println("info num: " + infoCollection.count());
        System.out.println("url num: " + urlCollection.count());
    }
}