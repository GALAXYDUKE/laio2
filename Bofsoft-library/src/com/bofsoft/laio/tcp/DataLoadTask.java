package com.bofsoft.laio.tcp;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.common.NetworkUtil;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.sdk.config.BaseApp;

public class DataLoadTask {
    protected MyLog mylog = new MyLog(this.getClass());
    private static DataLoadTask instance;

    private DataLoadTask() {
    }

    public static DataLoadTask getInstance() {
        synchronized (DataLoadTask.class) {
            if (instance == null) {
                instance = new DataLoadTask();
            }
            return instance;
        }
    }

    /**
     * 通用发送请求接口 send时会被设置为always=false
     *
     * @param commandid 命令码
     * @param content   请求发送内容
     * @param listener  回调界面
     */
    public void loadData(Short commandid, String content, IResponseListener listener) {
        loadData(commandid, content, -1, listener);
    }

    /**
     * 短消息请求发送专用 send时会被设置为always=true:一直发送直到成功
     *
     * @param commandid 命名码
     * @param content   请求发送内容
     * @param msg_id    消息在本地数据库ID
     * @param listener  回调界面
     */
    public void loadData(Short commandid, String content, Integer msg_id, IResponseListener listener) {
        if (BaseApp.context != null) {
            if (!NetworkUtil.isNetworkAvailable(BaseApp.context) && !cmdNotNeedShowNoNetWork(commandid)) {
                listener.messageBackFailed(ErrorCode.NETWORK_NOT_AVAILABLE, "无法连接到网络,请检查网络设置");
            }
        }
//    else{
//        listener.messageBackFailed(ErrorCode.NETWORK_NOT_AVAILABLE, "1");
//    }
        if (!ConfigAll.isLogin && !cmdNotNeedLogin(commandid)) {
//            listener.messageBackFailed(ErrorCode.E_NOT_LOGIN, "请先登录");
            return; // 这里直接返回，后期可以考虑自动完成登录并自动完成本次命令调用
        }
        if (null != DataCenter.getInstance()) {
            if (commandid == CommandCodeTS.GPS_GETGPSDATA || commandid == CommandCodeTS.GPS_GETGPSDATA_MONITOR) {
                DataCenter.getInstance().sendContentgps(commandid, content, msg_id, listener);
            } else {
                DataCenter.getInstance().sendContent(commandid, content, msg_id, listener);
            }

        } else {
            mylog.e("DataCenter is null when：" + "{" + commandid + "," + content + "," + msg_id + "}");
        }

    }

    /**
     * 命令码是否不需要登录
     *
     * @param commandid 命令码
     * @return 是否不需要登录
     */
    private boolean cmdNotNeedLogin(int commandid) {
        return (commandid >= 0x4000 && commandid < 0x4900) || commandid == CommandCodeTS.CMD_CHECK_VER
                || commandid == CommandCodeTS.CMD_MOBILE_LOGIN
                || commandid == CommandCodeTS.CMD_STU_GETCOACH_INFO
                || commandid == CommandCodeTS.CMD_STU_GETCOACH_DETAILINFO
                || commandid == CommandCodeTS.CMD_STU_GETCOACH_GROUND
                || commandid == CommandCodeTS.CMD_STU_GETCOACH_EVALUATE
                || commandid == CommandCodeTS.CMD_STU_GETCOACH_FEELIST
                || commandid == CommandCodeTS.CMD_STU_GETCOACHLIST
                || commandid == CommandCodeTS.CMD_STU_GETCOACHLIST_INMAP
                || commandid == CommandCodeTS.CMD_STU_SUBMITREGISTERINFO
                || commandid == CommandCodeTS.CMD_GETADVERTISEMENT_LIST
                || commandid == CommandCodeTS.CMD_GETADVERTISEMENT_LIST_NEW
                || commandid == CommandCodeTS.CMD_SUBMITACCOUNTREGINFO_INTF
                || commandid == CommandCodeTS.CMD_RESETACCOUNTPWD_INTF
                || commandid == CommandCodeTS.CMD_GETVERIFYCODE_INTF
                || commandid == CommandCodeTS.CMD_GETBASEDATAUPDATETIMELIST_INTF
                || commandid == CommandCodeTS.CMD_GETCOACHBASICINFO_INTF
                || commandid == CommandCodeTS.CMD_GETVERIFYCODEISVALID_INTF
                || commandid == CommandCodeTS.CMD_GETTEAEVALUATIONLIST_INTF
                || commandid == CommandCodeTS.CMD_PROREG_STANDARD
                || commandid == CommandCodeTS.CMD_PROONE_VIEW
                || commandid == CommandCodeTS.CMD_COACHLIST_STU
                || commandid == CommandCodeTS.CMD_ONEREGPRO_STU
                || commandid == CommandCodeTS.CMD_ONEDAYONECOACHTRAINPRO_STU
                || commandid == CommandCodeTS.CMD_GETBASEDATALIST_INTF
                || commandid == CommandCodeTS.CMD_COACH_GETAUTHOTHERINFO_INTF
                || commandid == CommandCodeTS.CMD_ORDERREGSTULIST_WEB
                || commandid == CommandCodeTS.CMD_GETAUTHTEAFILEURLLIST_INTF
                || commandid == CommandCodeTS.CMD_ONECOACHTRAINPROLIST_STU
                || commandid == CommandCodeTS.CMD_ONECOACHTRAINPROVIEW_STU
                || commandid == CommandCodeTS.CMD_PROTOCOL_REG
                || commandid == CommandCodeTS.CMD_PROTOCOL_TRAIN
                || commandid == CommandCodeTS.CMD_COACHLISTEX_STU
                || commandid == CommandCodeTS.CMD_GETSHAREINFOLIST_INTF
                || commandid == CommandCodeTS.CMD_STU_GETEXCELLENTCOACHLIST_INTF
                || commandid == CommandCodeTS.CMD_STU_GETAPPOINTMENTURL
                || commandid == CommandCodeTS.CMD_PRO_GUIDE
                || commandid == CommandCodeTS.CMD_GET_LOGOADVERTISEMENT
                || commandid == CommandCodeTS.CMD_GET_ACTIVITIESLIST
                || commandid == CommandCodeTS.CMD_GET_ARTICLESLIST
                || commandid == CommandCodeTS.CMD_GET_ACTIVITYFORINDEX
                || commandid == CommandCodeTS.CMD_GET_ADDUPREADED
                || commandid == CommandCodeTS.CMD_GET_FREEEXPERIENCEMESSAGE
                || commandid == CommandCodeTS.CMD_GET_APPLICANT_IDCARD_LIST
                || commandid == CommandCodeTS.CMD_GET_DAILY_PROMOTION_LIST
                || commandid == CommandCodeTS.CMD_GETAPPOINTWEBPAGEURL_INTF
                || commandid == CommandCodeTS.CMD_ORDERGROUP_STU
                || commandid == CommandCodeTS.CMD_GET_NEWHOMEFRAGMENTINFO
                || commandid == CommandCodeTS.CMD_GET_QRCODE
                || commandid == CommandCodeTS.CMD_GET_OLDSTUDENT_INVITE
                || commandid == CommandCodeTS.CMD_PRODUCTREG_HOMEREGCOMBOLIST_GO
                || commandid == CommandCodeTS.GPS_GETGPSDATA
                || commandid == CommandCodeTS.GPS_GETGPSDATA_MONITOR;
    }

    /**
     * 命令码是否不需要显示无网络图(根据页面决定命令码是否加入此方法)
     *
     * @param commandid 命令码
     * @return 是否不显示无网络图
     */
    private boolean cmdNotNeedShowNoNetWork(int commandid) {
        return (commandid >= 0x4000 && commandid < 0x4900)
                || commandid == CommandCodeTS.CMD_CHECK_VER
                || commandid == CommandCodeTS.CMD_GETACCOUNTSTATUS_INTF
                || commandid == CommandCodeTS.CMD_STU_GETFAVORITETEAUUIDLIST_INTF
                || commandid == CommandCodeTS.CMD_STU_GETVERIFYSTATUS
                || commandid == CommandCodeTS.CMD_STU_GETCOACH_COACHLIST
                || commandid == CommandCodeTS.CMD_GET_LOGOADVERTISEMENT
                || commandid == CommandCodeTS.CMD_FUTUREORDERCOUNT
                || commandid == CommandCodeTS.CMD_TRAINREMAINMIN
                || commandid == CommandCodeTS.CMD_GET_DEFAULT_CITY
                || commandid == CommandCodeTS.CMD_SET_DEFAULT_CITY
                || commandid == CommandCodeTS.CMD_GETADVERTISEMENT_LIST_NEW
                || commandid == CommandCodeTS.CMD_STU_GETEXCELLENTCOACHLIST_INTF
                || commandid == CommandCodeTS.CMD_GET_DAILY_PROMOTION_LIST
                || commandid == CommandCodeTS.CMD_GETSTUBASICINFO_INTF
                || commandid == CommandCodeTS.CMD_GET_ACTIVITYFORINDEX
                || commandid == CommandCodeTS.CMD_STUGETMYCOACHLIST_INTF
                || commandid == CommandCodeTS.CMD_DELETEMYFRIEND_INTF
                || commandid == CommandCodeTS.CMD_INVITEMYFRIENDBYSMS_INTF
                || commandid == CommandCodeTS.CMD_STUSUBMITCOACHINFO_INTF
                || commandid == CommandCodeTS.CMD_ENROLLDEMAND_STU
                || commandid == CommandCodeTS.CMD_TRAINDEMAND_STU
                || commandid == CommandCodeTS.CMD_ORDERGROUP_STU
                || commandid == CommandCodeTS.CMD_STU_GETVERIFYSTATUS
                || commandid == CommandCodeTS.CMD_GET_MYINFO
                || commandid == CommandCodeTS.CMD_GETVERIFYCODE_INTF
                || commandid == CommandCodeTS.CMD_MOBILE_LOGIN;
    }

}
