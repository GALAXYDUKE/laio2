package com.bofsoft.laio.laiovehiclegps.Tools;

import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    private static final int COUNT = 10;
    private static final int TOTALPAGE = 0;


    public static List<DummyItem> generyData(int page, GPSInfoList carListData) {
        int start = page * COUNT;
        int end = TOTALPAGE == page ? start + COUNT : start + COUNT;
        List<DummyItem> items = new ArrayList<DummyItem>();
        for (int i = start; i < end; i++) {
            if (i>=carListData.InfoList.size())
                break;
            items.add(createDummyItem(i,carListData));
        }
        return items;
    }

    /**
     * 是否还有更多
     *
     * @param page
     * @return
     */
    public static boolean hasMore(int page) {
        return page < TOTALPAGE;
    }

    private static DummyItem createDummyItem(int position, GPSInfoList carListData) {
        return new DummyItem(carListData.InfoList.get(position).License,//车牌号
                carListData.InfoList.get(position).Deviceid,
                carListData.InfoList.get(position).Online,
                carListData.InfoList.get(position).Latitude,
                carListData.InfoList.get(position).Longitude,
                carListData.InfoList.get(position).Speed,
                carListData.InfoList.get(position).Direction,
                carListData.InfoList.get(position).Datetime,
                carListData.InfoList.get(position).Status,
                carListData.InfoList.get(position).StatusContent,
                carListData.InfoList.get(position).Address);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        int count = position % 3;
        for (int i = 0; i < count; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String License;//	String	车牌号
        public String Deviceid;//	String	设备号
        public boolean Online;//	Bool	是否在线，如果设备正好连接到服务器上，返回true，如果不是返回false
        public double Latitude;//	Double	纬度
        public double Longitude;//	Double	经度
        public int Speed;//	Int	速度
        public int Direction;//	Int	方向角
        public String Datetime;//	String	数据发生的时间，格式为2017-01-01 01:02:03
        public int Status;//	Int	0表示正常，1表示有问题
        public String StatusContent;//	String	如果status为1，就会有该字段文字提示
        public String Address;//地址

        public DummyItem(String License, String Deviceid, boolean Online,double Latitude,double Longitude, int Speed,int Direction,String Datetime,int Status,String StatusContent,String Address) {
            this.License = License;
            this.Deviceid = Deviceid;
            this.Online = Online;
            this.Latitude = Latitude;
            this.Longitude = Longitude;
            this.Speed = Speed;
            this.Direction = Direction;
            this.Datetime = Datetime;
            this.Status = Status;
            this.StatusContent = StatusContent;
            this.Address = Address;
        }

        @Override
        public String toString() {
            return License;
        }
    }
}
