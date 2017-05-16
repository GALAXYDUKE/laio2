package com.bofsoft.laio.customerservice.Database;

/**
 * 根据定位的经纬度查询城市
 *
 * @author admin
 */
public class SqlUtils {
    /**
     * 根据定位的经纬度查询城市
     */
    public static String DistrictSql = "select laio_city.*,(abs(lat-?) + abs(lng-?)) " + "as aaa "
            + "from laio_city_gps " + "left join laio_city" + " on laio_city.id=laio_city_gps.CityId "
            + "where (abs(lat-?) + abs(lng-?))<0.2 " + "and laio_city.proid!=1 " + "order by aaa";
    public static String DistrictSql1 = "select laio_city.*,? " + "from laio_city " + "where laio_city.MC=? ";

    // public static String DistrictSql =
    // "select laio_city.*,(abs(lat-?) + abs(lng-?)) "
    // + "as aaa "
    // + "from laio_city_gps "
    // + "left join laio_city"
    // + " on laio_city.id=laio_city_gps.CityId "
    // + "where (abs(lat-?) + abs(lng-?))<0.1 "
    // + "and laio_city.proid!=1 "
    // + "order by aaa";

}
