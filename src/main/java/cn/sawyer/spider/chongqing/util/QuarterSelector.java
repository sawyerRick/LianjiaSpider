package cn.sawyer.spider.chongqing.util;

/**
 * @program: myspider
 * @description: quarter's css selector
 * @author: sawyer
 * @create: 2019-07-30 14:50
 **/
public class QuarterSelector {
    public static String nameSelector;

    public static String avgPriceSelector;

    public static String propertyPriceSelector;

    public static String companySelector;

    public static String locationSelector;

    public static String ageSelector;

    public static String houseSellingNumsSelector;

    public static String sellingInfoSelector;

    public static String quarterNumsSelector;

    static {
        quarterNumsSelector = "body > div.content > div.leftContent > div.resultDes.clear > h2 > span";
        nameSelector = "body > div.xiaoquDetailHeader > div > div.detailHeader.fl > h1";
        avgPriceSelector = "body > div.xiaoquOverview > div.xiaoquDescribe.fr > div.xiaoquPrice.clear > div > span.xiaoquUnitPrice";
        propertyPriceSelector = "body > div.xiaoquOverview > div.xiaoquDescribe.fr > div.xiaoquInfo > div:nth-child(3) > span.xiaoquInfoContent";
        companySelector = "body > div.xiaoquOverview > div.xiaoquDescribe.fr > div.xiaoquInfo > div:nth-child(4) > span.xiaoquInfoContent";
        ageSelector = "body > div.xiaoquOverview > div.xiaoquDescribe.fr > div.xiaoquInfo > div:nth-child(1) > span.xiaoquInfoContent";
        locationSelector = "body > div.xiaoquDetailHeader > div > div.detailHeader.fl > div";
        houseSellingNumsSelector = "#content > div.leftContent > div.resultDes.clear > h2 > span";
        sellingInfoSelector = "#goodSell > div > a";
    }
}
