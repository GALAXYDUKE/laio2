package com.bofsoft.laio.customerservice.DataClass.Order;

import android.text.TextUtils;

import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.data.BaseData;

import java.util.Calendar;

/**
 * 培训产品选中时间段Data
 *
 * @author yedong
 */
public class TrainCheckTimesData extends BaseData {
    private MyLog myLog = new MyLog(getClass());
    private String timeName = "";
    private String startTime = "";
    private String endTime = "";
    private int TimeSpace;

    // public int Id; //Integer 时间点ID，即产品Id
    // public String GUID; //String
    // 投放批次GUID，在购买选择学时时，只有GUID相同的才能一起购买，且如果是多个学时学时必须连续。
    // public String Name; //String 时间点，如：7:00~8:00
    // public int SpecId; //Integer 培训产品规格ID，区分不同规格的产品，同一规格的产品价格体系一样，即可执行相同套餐价格。
    // public int TimeSpace; //间隔时长，单位分钟，默认为0

    public TrainCheckTimesData(String timeName, int timeSpace) {
        this.timeName = timeName;
        this.TimeSpace = timeSpace;
        startTime = getStartTimeformString(timeName);
        endTime = getEndTimeformString(timeName);
        myLog.i("timeName = " + timeName + ">>>timeSpace = " + timeSpace);
    }

    public String getModStartTime() {
        String time = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(getHourformTime(startTime)));
        cal.set(Calendar.MINUTE, Integer.valueOf(getMinuteformTime(startTime)));
        // cal.add(Calendar.MINUTE,TimeSpace);
        time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        myLog.i("modStartTime = " + time);
        return time;
    }

    public String getModEndTime() {
        String time = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(getHourformTime(endTime)));
        cal.set(Calendar.MINUTE, Integer.valueOf(getMinuteformTime(endTime)));
        cal.add(Calendar.MINUTE, TimeSpace);
        time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
        myLog.i("modEndTime = " + time);
        return time;
    }

    private String getHourformTime(String timeString) {
        String time = "";
        if (!TextUtils.isEmpty(timeString)) {
            time = timeString.substring(0, timeString.indexOf(":")).trim();
        }
        myLog.i("Hour = " + time);
        return time;
    }

    private String getMinuteformTime(String timeString) {
        String time = "";
        if (!TextUtils.isEmpty(timeString)) {
            time = timeString.substring(timeString.indexOf(":") + 1).trim();
        }
        myLog.i("Minute = " + time);
        return time;
    }

    private String getStartTimeformString(String timeString) {
        String time = "";
        if (!TextUtils.isEmpty(timeString)) {
            time = timeString.substring(0, timeString.indexOf("~")).trim();
        }
        myLog.i("StartTime = " + time);
        return time;
    }

    private String getEndTimeformString(String timeString) {
        String time = "";
        if (!TextUtils.isEmpty(timeString)) {
            time = timeString.substring(timeString.indexOf("~") + 1).trim();
        }
        myLog.i("EndTime = " + time);
        return time;
    }

}
