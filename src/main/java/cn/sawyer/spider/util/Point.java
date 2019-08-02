package cn.sawyer.spider.util;

import java.io.Serializable;

/**
 * Description: 坐标点
 *
 * @author JourWon
 * @date Created on 2018年6月19日
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 3584864663880053897L;

    public Point(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    /**
     * 经度
     */
    private double lng;

    /**
     * 纬度
     */
    private double lat;


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}