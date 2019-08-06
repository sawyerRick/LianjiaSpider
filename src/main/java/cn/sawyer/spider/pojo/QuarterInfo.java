package cn.sawyer.spider.pojo;

public class QuarterInfo {

//    需求:
//    小区名称
//    均价（元/平方米）	直接写数字，不用加符号和单位，如10010元/平方米，则写10010
//    建筑年代	直接写数字，不用加符号和单位，如2000年，则写2000
//    物业费用(元)	直接写数字，不用加符号和单位，如10010元/平方米，则写10010
//    物业公司
//    小区经度	高德地图直接爬取的经度
//    小区纬度	高德地图直接爬取的纬度
//    wgsx	经坐标系转换以后，wgs84坐标系下的经度
//    wgsy	经坐标系转换以后，wgs84坐标系下的纬度
//    在售房源套数

    /**
     * 小区名称
     */
    private String name;

    /**
     * 均价
     */
    private String avgPrice;

    /**
     * 建筑年代
     */
    private String age;

    /**
     * 物业费用
     */
    private String propertyPrice;

    /**
     * 物业公司
     */
    private String company;

    /**
     * 小区位置
     */
    private String location;

    /**
     * 高德地图经度
     */
    private String longitude;

    /**
     * 高德地图纬度
     */
    private String latitude;

    /**
     * wgs84格式经度
     */
    private String wgsx;

    /**
     * wgs84格式纬度
     */
    private String wgsy;

    /**
     * 在售房源数
     */
    private String houseSellingNums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getWgsx() {
        return wgsx;
    }

    public void setWgsx(String wgsx) {
        this.wgsx = wgsx;
    }

    public String getWgsy() {
        return wgsy;
    }

    public void setWgsy(String wgsy) {
        this.wgsy = wgsy;
    }

    public String getHouseSellingNums() {
        return houseSellingNums;
    }

    public void setHouseSellingNums(String houseSellingNums) {
        this.houseSellingNums = houseSellingNums;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
