package com.bofsoft.laio.common;

public class CommandCodeTS {

    public static final short CMD_NONE = 0x0000;
    public static final short CMD_USER_LOGIN = 0x0100;
    public static final short CMD_CHANGE_USER_STATUS = 0x0101;
    public static final short CMD_UPDATE_USER_STATUS = 0x0102;
    public static final short CMD_MODIFY_USER_SET = 0x0103; //用户配置信息
    public static final short CMD_USER_FRD_FIND = 0x0104; //用户好友查找
    public static final short CMD_USER_FRD_INS = 0x0105; //用户好友添加
    public static final short CMD_USER_FRD_RECINS = 0x0106; //用户推送好友添加消息
    public static final short CMD_USER_FRD_MOD = 0x0107; //用户好友修改
    public static final short CMD_USER_FRD_DEL = 0x0108; //用户好友删除
    public static final short CMD_USER_FRDGROUP_INS = 0x0109; //用户好友组添加
    public static final short CMD_USER_FRDGROUP_MOD = 0x010A; //用户好友组修改
    public static final short CMD_USER_FRDGROUP_DEL = 0x010B; //用户好友组删除
    public static final short CMD_USER_FRDGROUP_FRD = 0x010C; //用户好友组添加好友
    public static final short CMD_USER_DIAGROUP_INS = 0x010D; //用户会话组创建
    public static final short CMD_USER_DIAGROUP_MOD = 0x010E; //用户会话组修改
    public static final short CMD_USER_DIAGROUP_QUIT = 0x010F; //退出用户会话组
    public static final short CMD_USER_DIAGROUP_FRD = 0x0110; //用户会话组添加成员
    public static final short CMD_USER_RECENT_INS = 0x0111; //将好友加入聊天历史记录列表
    public static final short CMD_USER_RECENT_DEL = 0x0112; //将好友从聊天历史记录列表中删除
    public static final short CMD_USER_REPEATLOGIN = 0x0113; //用户重复登陆
    public static final short CMD_USER_LOGOUT = 0x01FF;
    public static final short CMD_DPTEST_ERROR = 0x0200; //
    public static final short CMD_DPTEST_GETINFO = 0x0201; //
    public static final short CMD_DPTEST_GETTABLE = 0x0202; //
    public static final short CMD_DPTEST_GETPIC = 0x0203; //
    public static final short CMD_DPTEST_MYERROR = 0x0204; //
    public static final short CMD_DPTEST_GETPICE = 0x0205; //
    public static final short CMD_DPTEST_ADDERRDB = 0x0206; //
    public static final short CMD_DPTEST_EXPNUM = 0x0207; //
    public static final short CMD_DPTEST_GETPICS = 0x0208; //
    public static final short CMD_DPTEST_COMMIT = 0x0209; //
    public static final short CMD_DPTEST_GETHIS = 0x0210; //
    public static final short CMD_IM_SEND_TXT_MSG = 0x0300; //发送消息
    public static final short CMD_IM_GET_TXT_MSG = 0x0301; //接收消息
    public static final short CMD_IM_FILE_TRANSFER = 0x0302; //文件传输
    public static final short CMD_IM_SEND_FILE_INIT = 0x0312; //准备发送文件
    public static final short CMD_IM_RECV_FILE_INIT = 0x0303; //准备接收文件
    public static final short CMD_IM_SEND_FILE = 0x0304; //发送文件
    public static final short CMD_IM_RECV_FILE = 0x0305; //接收文件
    public static final short CMD_IM_SEND_FILE_FINI = 0x0306; //文件发送完毕
    public static final short CMD_IM_RECV_FILE_FINI = 0x0307; //文件接收完毕
    public static final short CMD_IM_SEND_FILE_CANCEL = 0x0308; //文件发送取消
    public static final short CMD_IM_RECV_FILE_CANCEL = 0x0309; //文件接收取消
    public static final short CMD_IM_SEND_FILE_INIT_R = 0x030A; //准备发送文件
    public static final short CMD_IM_RECV_FILE_INIT_R = 0x030B; //准备接收文件
    public static final short CMD_IM_SEND_FILE_R = 0x030C; //发送文件
    public static final short CMD_IM_RECV_FILE_R = 0x030D; //接收文件
    public static final short CMD_IM_SEND_FILE_FINI_R = 0x030E; //文件发送完毕
    public static final short CMD_IM_RECV_FILE_FINI_R = 0x030F; //文件接收完毕
    public static final short CMD_IM_SEND_FILE_CANCEL_R = 0x0310; //文件发送取消
    public static final short CMD_IM_RECV_FILE_CANCEL_R = 0x0311; //文件接收取消
    public static final short CMD_MOBILE_LOGIN = 0x1000; //手机客户端登录
    public static final short CMD_MOBILE_LOGOUT = 0x1001; //手机客户端登出
    public static final short CMD_MOBILE_UPDATEDPINFO = 0x1002; //更新DP账号信息
    public static final short CMD_CLIENT_SENDMSG = 0x1010; //客户端发送到服务器端消息
    public static final short CMD_SERVER_SENDMSG = 0x1011; //服务器端发送到客户端消息
    public static final short CMD_GET_MSGCOUNT = 0x1012; //得到未读的消息数目
    public static final short CMD_GET_MSGLIST = 0x1013; //得到未读的消息列表
    public static final short CMD_GET_NOTICELIST = 0x1014; //得到未读通知公告列表
    public static final short CMD_GET_NOTICEBODY = 0x1015; //得到通知公告内容
    public static final short CMD_GET_PROMOTIONLIST = 0x1017;//得到实时优惠促销列表
    public static final short CMD_MSGTYPE_JXYW = 0x0001; //1驾校业务消息
    public static final short CMD_MSGTYPE_LAIOMSG = 0x0002; //2来噢系统消息
    public static final short CMD_MSGTYPE_COACHMSG = 0x0003; //3教练（发送给学员）消息
    public static final short CMD_MSGTYPE_STUMSG = 0x0004; //4学员（发送给教练）消息
    public static final short CMD_MSGTYPE_JXNOTICE = 0x000a; //10驾校通知
    public static final short CMD_MSGTYPE_MYORDER = 0x000b; //11订单消息
    public static final short CMD_MSGTYPE_STEPAHEAD = 0x000c; //12抢单消息
    public static final short CMD_MSGTYPE_PROMOTION = 0x000d; //13抢单消息
    public static final short CMD_CHECK_VER = 0x1050; //客户端检查版本号
    public static final short CMD_STU_GETCOACH_INFO = 0x1100; //学员获取我的教练信息
    public static final short CMD_STU_GETCOACH_DETAILINFO = 0x1101; //学员获取我的教练信息-教练详细信息
    public static final short CMD_STU_GETCOACH_GROUND = 0x1102; //学员获取我的教练信息-训练场地
    public static final short CMD_STU_GETCOACH_EVALUATE = 0x1103; //学员获取我的教练信息-对教练的评价
    public static final short CMD_STU_GETCOACH_FEELIST = 0x1104; //学员获取我的教练信息-班次费用
    public static final short CMD_STU_GETCOACH_COACHLIST = 0x1105; //学员获取我的教练列表
    public static final short CMD_STU_GETCOACHEVALUATE_INFO = 0x1110; //教练评价-获取教练信息
    public static final short CMD_STU_SUBMITCOACHEVALUATE_INFO = 0x1111; //教练评价-提交评价数据
    public static final short CMD_STU_GETTRAINCOURSE = 0x1200; //学车历程－状态列表及当前状态
    public static final short CMD_STU_GETTRAINCOURSE_LIST = 0x1201; //学车历程－流程状态详细
    public static final short CMD_STU_CARDRIVINGLIST = 0x1203; //学员、教练获取学员学车历程

    public static final short CMD_STU_GETTRAINSCHEDULE = 0x1220; //培训进度
    public static final short CMD_STU_GETEXAMINFOLIST = 0x1230; //考试情况查询
    public static final short CMD_STU_GETAPPOINTMENTURL = 0x1240; //在线预约URL
    public static final short CMD_STU_GETEXCELLENTCOACHLIST_INTF = 0x1251; //学员首页获取优秀教练列表
    public static final short CMD_STU_GETCOACHLIST = 0x1301; //找教练－获取教练信息列表
    public static final short CMD_STU_GETCOACHLIST_INMAP = 0x1302; //找教练－地图显示
    public static final short CMD_STU_SUBMITENROLLINFO = 0x1303; //找教练－提交报名表
    public static final short CMD_STU_GETVERIFYSTATUS = 0x1401; //申请驾校认证－获取认证状态
    public static final short CMD_STU_SUBMITVERIFYINFO = 0x1402; //申请驾校认证－提交认证信息
    public static final short CMD_STU_GETMYORDERLIST = 0x1411; //我的订单（列表）
    public static final short CMD_STU_SUBMITEMAILVERIFY = 0x1421; //邮箱绑定
    public static final short CMD_STU_SUBMITMODIFYPASSWORD = 0x1422; //修改密码
    public static final short CMD_STU_SUBMITSUGGEST = 0x1431; //投诉咨询建议提交（1，投诉，2咨询，3建议）
    public static final short CMD_STU_GETSUGGESTLIST = 0x1432; //获取投诉咨询建议列表
    public static final short CMD_STU_GETABOUTUSURL = 0x1461; //关于我们URL
    public static final short CMD_STU_SUBMITAUTHINFO_INTF = 0x1471; //学员提交认证信息
    public static final short CMD_STU_GETAUTHINFO_INTF = 0x1472; //学员获取认证信息
    public static final short CMD_STU_SUBMITAUTHFILE_INTF = 0x1475; //学员提交认证证件图片
    public static final short CMD_STU_GETAUTHFILE_INTF = 0x1476; //学员获取认证证件图片
    public static final short CMD_STU_SUBMITCONFIRMAUTH_INTF = 0x1479; //学员认证信息确认提交
    public static final short CMD_STU_SUBMITREGISTERINFO = 0x1501; //学员注册提交
    public static final short CMD_GETADVERTISEMENT_LIST = 0x1530; //获取广告列表
    public static final short CMD_GETADVERTISEMENT_LIST_NEW = 0x1531; //获取广告列表
    public static final short USER_SUBMITFEEDBACKINFO = 0x1550; //用户提交反馈
    public static final short USER_GETFEEDBACKLIST = 0x1555; //用户获取反馈列表
    public static final short CMD_SUBMITACCOUNTREGINFO_INTF = 0x1505; //提交账号注册信息
    public static final short CMD_COACHSUBMITMOBILEBIND_INTF = 0x1506; //ERP账号验证并绑定手机号码
    public static final short CMD_RESETACCOUNTPWD_INTF = 0x1507; //找回账号登录密码
    public static final short CMD_GETVERIFYCODE_INTF = 0x1510; //获取验证码
    public static final short CMD_STU_SUBMITFAVORITE_INTF = 0x1541; //学员提交收藏的教练信息
    public static final short CMD_STU_GETFAVORITELIST_INTF = 0x1543; //学员获取已收藏教练列表
    public static final short CMD_STU_GETFAVORITETEAUUIDLIST_INTF = 0x1545; //学员获取已收藏教练UUID列表
    public static final short CMD_COACH_GETSTUDENTTOTALLIST_EX = 0x1546; //教练获取分员状态分类统计列表
    public static final short CMD_COACH_GETSTUDENTDETAILLIST_EX = 0x1547; //教练获取学员详细列表
    public static final short CMD_STUSUBMITCOACHLIST_INTF = 0x1560; //学员添加教练列表
    public static final short CMD_STUSUBMITCOACHINFO_INTF = 0x1561; //学员添加或修改单个教练
    public static final short CMD_STUGETMYCOACHLIST_INTF = 0x1563; //学员获取我的教练列表
    public static final short CMD_STUGETMYCOACHINFO_INTF = 0x1564; //学员获取我的教练详情
    public static final short CMD_COACH_GETCARLIST = 0x2101; //教练获取车辆列表
    public static final short CMD_COACH_GETCARINFO = 0x2102; //教练获取车辆信息
    public static final short CMD_SET_DEFAULT_CITY = 0x2143; //设置默认城市信息
    public static final short CMD_GET_DEFAULT_CITY = 0x2144; //获取默认城市信息
    public static final short CMD_COACH_GETSTUTRAININGINFO_LIST = 0x2110; //教练获取学员培训信息
    public static final short CMD_COACH_GETSTUDENTTOTALLIST = 0x2201; //教练获取分员状态分类统计列表
    public static final short CMD_COACH_GETSTUDENTDETAILLIST = 0x2202; //教练获取学员详细列表
    public static final short CMD_COACH_GETSTUDENTBASEINFO = 0x2203; //教练获取学员基本信息
    public static final short CMD_COACH_GETSTUDENTFLOWINFO = 0x2204; //教练获取学员流程信息
    public static final short CMD_COACH_GETSTUDENTFINANCEINFO = 0x2205; //教练获取学员财务信息
    public static final short CMD_COACH_GETSTUDENTEXAMINFO = 0x2206; //教练获取学员考试记录
    public static final short CMD_COACH_GETSTUDENTTRAININGINFO = 0x2207; //教练获取学员培训记录
    public static final short CMD_COACH_GETSTUDENTREACHMARKINFO = 0x2208; //教练获取学员达标信息
    public static final short CMD_COACH_GETREGCARTYPELIST = 0x2209; //教练获取学员报名登记可选择的报考车型
    public static final short CMD_COACH_STUDENTINFOREG = 0x2210; //教练登记学员报名信息
    public static final short CMD_COACH_GETREGSTULIST_INTF = 0x2211; //获取来噢学员(招生类)列表
    public static final short CMD_COACH_GETSTUORDERLIST_INTF = 0x2212; //教练获取来噢学员明细订单列表
    public static final short CMD_COACH_GETREGSTUSUM_INTF = 0x2213; //获取来噢学员(招生类)总数
    public static final short CMD_COACH_GETPRODUCTTOTAL_INTF = 0x2221; //教练首页获取在售产品数量
    public static final short CMD_COACH_JUDGECGBESPEAKCANOPERATE = 0x2301; //判断教练是否可操作车管预约功能模块
    public static final short CMD_COACH_GETCGBESPEAKBATCHINFO = 0x2302; //教练获取车管预约批次信息
    public static final short CMD_COACH_GETCGBESPEAKSTUDENTLIST = 0x2303; //教练获取车管预约学员列表
    public static final short CMD_COACH_CGBESPEAKOPERATE = 0x2304; //教练车管预约/取消预约接口
    public static final short CMD_COACH_GETYUEKAOBATCHINFO = 0x2401; //教练获取约考批次信息
    public static final short CMD_COACH_GETYUEKAOSTUDENTLIST = 0x2402; //教练获取约考学员列表
    public static final short CMD_COACH_YUEKAOOPERATE = 0x2403; //教练约考学员/取消约考接口
    public static final short CMD_COACH_GETYUEKAOINQUIRYLIST_SUB = 0x2404; //教练获取约考查询按科目统计列表
    public static final short CMD_COACH_GETYUEKAOINQUIRYLIST_GROUND = 0x2405; //教练获取约考查询按考场统计列表
    public static final short CMD_COACH_GETYUEKAOINQUIRYLIST_STUDENT = 0x2406; //教练获取约考查询学员明细列表
    public static final short CMD_COACH_SUBMITFEEDBACKINFO = 0x2510; //教练提交反馈
    public static final short CMD_COACH_GETFEEDBACKLIST = 0x2511; //教练获取反馈列表
    public static final short CMD_COACH_GETMYORDERLIST = 0x2520; //教练获取我的订单（列表）
    public static final short CMD_COACH_GETCOACH_INFO = 0x2530; //教练获取我的教练信息（网络招生信息）
    public static final short CMD_COACH_GETCOACH_DETAILINFO = 0x2531; //教练获取我的教练信息-详细介绍（网络招生信息）
    public static final short CMD_COACH_GETCOACH_GROUND = 0x2532; //教练获取我的教练信息-训练场地（网络招生信息）
    public static final short CMD_COACH_GETCOACH_FEELIST = 0x2533; //教练获取我的教练信息-班次费用（网络招生信息）
    public static final short CMD_COACH_GETPHYSICALDUELIST_CG = 0x2601; //教练获取报开学体检到期学员列表
    public static final short CMD_COACH_GETPHYSICALDUELIST_HB = 0x2602; //教练获取合表体检到期学员列表
    public static final short CMD_COACH_GETCGREGLIST = 0x2603; //教练获取车管注册成功学员列表
    public static final short CMD_COACH_GETCGREGFAILEDLIST = 0x2604; //教练获取报开学失败学员列表
    public static final short CMD_COACH_SUBMITAUTHINFO_INTF = 0x2701; //教练提交认证信息
    public static final short CMD_COACH_GETAUTHINFO_INTF = 0x2702; //教练获取认证信息
    public static final short CMD_COACH_SUBMITAUTHFILE_INTF = 0x2705; //教练提交认证证件图片
    public static final short CMD_COACH_GETAUTHFILE_INTF = 0x2706; //教练获取认证证件图片
    public static final short CMD_GETAUTHFILESTATUSLIST_INTF = 0x2707; //获取认证证件图片状态列表
    public static final short CMD_COACH_SUBMITCONFIRMAUTH_INTF = 0x2708; //教练商家入住确认提交
    public static final short CMD_COACH_SUBMITAUTHOTHERINFO_INTF = 0x2709; //教练提交其它信息
    public static final short CMD_COACH_GETAUTHOTHERINFO_INTF = 0x2710; //教练获取其它信息
    public static final short CMD_COACH_GETAUTHSTATUS_INTF = 0x2711; //教练获取认证状态
    public static final short CMD_COACH_SUBMITERPACCOUNTBIND_INTF = 0x2715; //教练绑定ERP账号
    public static final short CMD_COACH_GETERPACCOUNTBINDINFO_INTF = 0x2716; //教练获取绑定的ERP账号信息
    public static final short CMD_COACH_SUBMITNEWERPACCOUNTBIND_INTF = 0x2717; //教练绑定新ERP账号（更换ERP账号绑定）
    public static final short CMD_GETCOACHBASICINFO_INTF = 0x2721; //获取教练基本信息
    public static final short CMD_GETCARBASICINFO_INTF = 0x2723; //获取车辆基本信息
    public static final short CMD_GETSTUBASICINFO_INTF = 0x2727; //获取学员基本信息
    public static final short CMD_GETBASEDATALIST_INTF = 0x2740; //按类型获取基础数据
    public static final short CMD_GETBASEDATAUPDATETIMELIST_INTF = 0x2741; //获取基础数据表的最后更新时间列表
    public static final short CMD_COACHGETCLASSTRAINSETLIST_INTF = 0x2745; //教练获取培训班次列表
    public static final short CMD_COACHSUBMITCLASSTRAINSET_INTF = 0x2746; //教练提交培训班次
    public static final short CMD_COACHGETCLASSTIMESETLIST_INTF = 0x2749; //教练获取培训时段列表
    public static final short CMD_COACHSUBMITCLASSTIMESET_INTF = 0x2750; //教练提交培训时段
    public static final short CMD_COACHGETENROLLADDRSETLIST_INTF = 0x2754; //教练获取招生地址列表
    public static final short CMD_COACHSUBMITENROLLADDRSET_INTF = 0x2755; //教练提交招生地址
    public static final short CMD_COACHGETSTARTADDRSETLIST_INTF = 0x2757; //教练获取接送地址列表
    public static final short CMD_COACHSUBMITSTARTADDRSET_INTF = 0x2758; //教练提交接送地址
    public static final short CMD_COACHSUBMITTRAINFEESET_INTF = 0x2761; //教练提交收费标准设置
    public static final short CMD_COACHGETTRAINFEESETLIST_INTF = 0x2764; //教练获取收费标准设置列表
    public static final short CMD_COACHGETTRAINFEESET_INTF = 0x2765; //教练获取单个收费标准设置
    public static final short CMD_COACHGETERPACCOUNTLIST_INTF = 0x2811; //教练获取已添加的ERP账号列表
    public static final short CMD_COACHADDERPACCOUNT_INTF = 0x2813; //教练添加ERP账号
    public static final short CMD_COACHDELERPACCOUNT_INTF = 0x2815; //教练删除ERP账号
    public static final short CMD_COACHGETLAIOACCOUNTLIST_INTF = 0x2817; //获取教练来噢账户信息列表
    public static final short CMD_COACHSUBMITSTULIST_INTF = 0x2820; //教练添加学员列表
    public static final short CMD_COACHSUBMITSTUINFO_INTF = 0x2821; //教练添加或修改单个学员
    public static final short CMD_COACHGETMYSTUINFO_INTF = 0x2823; //教练或学员获取学员基本信息
    public static final short CMD_SUBMITMODIFYLOGINPWD_INTF = 0x2121; //修改登录密码
    public static final short CMD_GETVERIFYCODEISVALID_INTF = 0x2123; //验证验证码是否正确
    public static final short CMD_GETLOGINPWDISVALID_INTF = 0x2125; //验证登录密码是否正确
    public static final short CMD_SUBMITACCOUNTMOBILEBIND_INTF = 0x2127; //绑定手机号码
    public static final short CMD_SUBMITNEWACCOUNTMOBILEBIND_INTF = 0x2129; //绑定新手机号码
    public static final short CMD_GETPAYPWDISVALID_INTF = 0x2131; //验证支付密码是否正确
    public static final short CMD_SUBMITPAYPWD_INTF = 0x2132; //设置支付密码
    public static final short CMD_SUBMITMODIFYPAYPWD_INTF = 0x2134; //修改支付密码
    public static final short CMD_RESETACCOUNTPAYPWD_INTF = 0x2135; //重置（找回）支付密码
    public static final short CMD_GETACCOUNTSTATUS_INTF = 0x2141; //获取账号状态信息
    public static final short CMD_STU_SUBMITTEAEVALUATION_INTF = 0x2451; //学员提交教练评价
    public static final short CMD_GETTEAEVALUATIONLIST_INTF = 0x2453; //获取教练评价列表
    public static final short CMD_COACH_SUBMITSTUEVALUATION_INTF = 0x2455; //教练提交学员评价
    public static final short CMD_GETSTUEVALUATIONLIST_INTF = 0x2457; //获取学员评价列表
    public static final short CMD_GETACCOUNTBALANCE_INTF = 0x2461; //获取账户余额
    public static final short CMD_GETACCOUNTASSETINOUTLIST_INTF = 0x2463; //获取账户收支明细列表
    public static final short CMD_GETACCOUNTASSETINOUTDETAIL_INTF = 0x2465; //获取账户收支明细详情
    public static final short CMD_SUBMITACCOUNTBALANCETRANSFER_INTF = 0x2467; //提交账户余额转出
    public static final short CMD_SUBMITAPLIPAYACCOUNTINFO_INTF = 0x2468; //提交余额转出到的支付宝账户信息
    public static final short CMD_GETAPLIPAYACCOUNTINFO_INTF = 0x2469; //获取余额转出到的支付宝账户信息
    public static final short CMD_GETAUTHTEAFILEURLLIST_INTF = 0x2471; //获取教练认证证件图片列表
    public static final short CMD_GETSHAREINFOLIST_INTF = 0x2475; //获取社会化分享内容列表
    public static final short CMD_GETAPPOINTWEBPAGEURL_INTF = 0x2476; //获取指定页面的URL
    public static final short CMD_DELETEMYFRIEND_INTF = 0x2480; //学员或教练删除我的关联记录
    public static final short CMD_INVITEMYFRIENDBYSMS_INTF = 0x2481; //学员或教练向未注册账号用户发送短信邀请
    public static final short CMD_GETPRODUCT_VAS = 0x2901; //产品增值服务列表
    public static final short CMD_GETRULE_URL = 0x2902; //产品规则URL
    public static final short CMD_PROREG_RELEASE = 0x2903; //招生类产品预览、发布
    public static final short CMD_PROTRAIN_RELEASE = 0x2904; //培训类产品预览、发布
    public static final short CMD_GETPRICE_REG = 0x2905; //招生产品价格列表
    public static final short CMD_PROTOCOL_REG = 0x2906; //招生产品相关协议
    public static final short CMD_PROTOCOL_TRAIN = 0x2907; //学时产品相关协议
    public static final short CMD_PROTRAIN_RELEASE4DPJS = 0x2908; //DP计时培训类产品预览、发布
    public static final short CMD_PROTRAIN_CHANGE4DPJS = 0x2909; //DP计时培训类产品部分学时取消
    public static final short CMD_PROREG_STANDARD = 0x2910; //获取招生产品费用内容与服务标准
    public static final short CMD_GETPROLIST_COACH = 0x2911; //教练产品列表（含招生、培训）
    public static final short CMD_PROONE_VIEW = 0x2912; //查看单个产品详情（含招生、培训）
    public static final short CMD_PRODROPS_COACH = 0x2913; //产品下架（含招生、培训）
    public static final short CMD_SALETRAIN_COACH = 0x2914; //教练端_学时产品销售情况
    public static final short CMD_SENDLAIOMSG4DPJS = 0x2916; //DP端_给指定手机号发送来噢消息
    public static final short CMD_COACHLIST_STU = 0x2921; //学员端_找教练(用默认Key加解密)
    public static final short CMD_ONEREGPRO_STU = 0x2922; //学员端_查看单个报名产品(用默认Key加解密)
    public static final short CMD_ONEDAYONECOACHTRAINPRO_STU = 0x2923; //学员端_查看某教练的培训产品(用默认Key加解密)
    public static final short CMD_ORDERREG_STU = 0x2924; //学员端_提交报名订单
    public static final short CMD_ORDERTRAIN_STU = 0x2925; //学员端_提交学时订单
    public static final short CMD_ONECOACHTRAINPROLIST_STU = 0x2926; //学员端_查看某教练某类可购买产品按日期的分布列表(用默认Key加解密)
    public static final short CMD_ONECOACHTRAINPROVIEW_STU = 0x2927; //学员端_查看某教练某类可购买产品按日期产品详情预览(用默认Key加解密)
    public static final short CMD_COACHLISTEX_STU = 0x2928; //web_教练精品课程(用默认Key加解密)
    public static final short CMD_ORDERTRAIN4TT_STU = 0x2929; //学员端_通过课表提交学时订单
    public static final short CMD_ORDERPAY_STU = 0x2931; //学员端_订单支付
    public static final short CMD_ORDERPAYBACK_STU = 0x2932; //学员端_支付完成反馈
    public static final short CMD_ORDERAFFIRM_STU = 0x2933; //学员端_支付确认
    public static final short CMD_ORDERCANCEL_STU = 0x2934; //学员端_取消订单
    public static final short CMD_ORDERREFUND_STU = 0x2935; //学员端_退款申请
    public static final short CMD_ORDERREFUNDAFFIRM_COACH = 0x2936; //教练端_学员退款申请确认
    public static final short CMD_ORDERCANCELAGREEMENT_STU = 0x2937; //学员端_取消订单扣费标准协议
    public static final short CMD_ORDERREFUNDLIST = 0x2938; //申请返款记录明细
    public static final short CMD_ORDERREFUNDREQUEST_STU = 0x2939; //学员端_退款申请请求
    public static final short CMD_ORDERCANCELREASONLIST_STU = 0x2940; //学员端_取消订单原因列表
    public static final short CMD_ORDERGROUP_STU = 0x2941; //学员端_订单数汇总列表
    public static final short CMD_ORDERGROUP_COACH = 0x2942; //教练端_订单数汇总列表
    public static final short CMD_ORDERLIST_STU = 0x2943; //学员端_订单详情列表
    public static final short CMD_ORDERLIST_COACH = 0x2944; //教练端_订单详情列表
    public static final short CMD_ORDERREGSTULIST_WEB = 0x2945; //网页_最近报名成功学员列表(用默认Key加解密)
    public static final short CMD_ORDERVIEW = 0x2951; //查看单张订单详情
    public static final short CMD_TRAINSTART_STU = 0x2952; //学员端_开始培训
    public static final short CMD_TRAINSTART_COACH = 0x2953; //教练端_确认开始培训
    public static final short CMD_TRAINFINISH_STU = 0x2954; //学员端_完成培训
    public static final short CMD_TRAINBREACH_COACH = 0x2955; //教练端_教练标记违约学员
    public static final short CMD_TRAINREMAINMIN = 0x2956; //请求当前在训订单剩余时间（教练、学员同一接口）
    public static final short CMD_REGFEEBACKLIST_COACH = 0x2961; //教练端_可申请返点学员列表
    public static final short CMD_REQUESTREGFEEBACK_COACH = 0x2962; //教练端_申请返款
    public static final short CMD_REQUESTREGFEELIST_COACH = 0x2963; //教练端_请求返款记录
    public static final short CMD_SALESSTATISTICAL_COACH = 0x2971; //教练端_销量统计
    public static final short CMD_ORDERPAYGATE_STU = 0x2981; //学员端_来噢收银台
    public static final short CMD_ORDERPAYGATE_PAY_STU = 0x2982; //学员端_来噢收银台立即支付
    public static final short CMD_ORDER_LAIOTICKET_LIST_STU = 0x2985; //学员端_订单来噢券列表
    public static final short CMD_LAIOTICKET_LIST_STU = 0x2986; //学员端_账户余额来噢券列表
    public static final short CMD_LAIOTICKET_INTRO_STU = 0x2988; //学员端_获取单张来噢券使用说明
    public static final short CMD_SENDTICKETCONFIG_COACH = 0x2990; //教练端_获取发券配置参数
    public static final short CMD_SENDTICKET_COACH = 0x2991; //教练端_给学员发放特定类型的来噢券
    public static final short CMD_ORDERTRAINFREE_STU = 0x2995; //学员端_学员免费体验学车
    public static final short CMD_ORDERTRAINEASY_STU = 0x2996; //学员端_快速提交学时订单
    public static final short CMD_ORDERREGEASY_STU = 0x2997; //学员端_快速提交报名订单
    public static final short CMD_GETTESTSUBINFO_STU = 0x2850; //学员端WEB登录获取培训进度详情基础数据
    public static final short CMD_RENEWTRAINTIMES_STU = 0x2851; //学员端更新协商结果学时
    public static final short CMD_ORDERCANCELFORDP_STU = 0x2852; //学员端取消DP投放计时培训产品订单
    public static final short CMD_COMMITTESTSUBINFO_STU = 0x2853; //学员端提交订单培训详情
    public static final short CMD_GETTESTSUBINFO = 0x2854; //获取学员培训进度详情
    public static final short CMD_CONFIRMORDER_COACH = 0x2855; //教练端_受理学员订单
    public static final short CMD_FUTUREORDERCOUNT = 0x2856; //教练、学员端_获取待处理订单数
    public static final short CMD_PROTRAIN_EDIT_COACH = 0x2860; //教练端_课表设置培训产品预览，发布
    public static final short CMD_PROTRAIN_UNDO_COACH = 0x2861; //教练端_课表节点下架
    public static final short CMD_PROTRAIN_TIMETABLE_COACH = 0x2862; //获取课表列表
    public static final short CMD_PROTRAIN_TIMETABLE_DETAIL = 0x2863; //教练、学员获取课程节点详情
    public static final short CMD_RECRUIT_TRAINING = 0x2864; //教练端_获取招生产品或培训产品可发布产品类型
    public static final short CMD_PROTRAIN_LEAVELIST_COACH = 0x2865; //教练其他安排默认列表
    public static final short CMD_TRAINSITE_COACH = 0x2868; //教练端_添加、设置默认、删除我的培训场地
    public static final short CMD_TRAINSITELIST_COACH = 0x2869; //教练端_获取我的培训场地列表
    public static final short CMD_ENROLLDEMAND_STU = 0x2870; //学员端_发布报名需求
    public static final short CMD_TRAINDEMAND_STU = 0x2871; //学员端_发布培训、陪练需求
    public static final short CMD_DEMANDLIST = 0x2872; //学员、教练端_获取需求列表
    public static final short CMD_COACHSEEKLIST_STU = 0x2873; //学员端_获取教练已抢单列表
    public static final short CMD_CANCELDEMAND_STU = 0x2874; //学员端_取消需求
    public static final short CMD_DEMANDINFO_COACH = 0x2875; //教练端_获取待抢单需求信息
    public static final short CMD_SEEKDEMAND_COACH = 0x2876; //教练端_抢单
    public static final short CMD_DELDEMAND_COACH = 0x2877; //教练端_删除抢单失败的记录
    public static final short CMD_ORGANIZATION_GETADDRESSLIST = 0x3000; //机构查询招生/接送地址列表
    public static final short CMD_ORGANIZATION_ADDRESSOPERATE = 0x3001; //机构招生/接送地址操作
    public static final short CMD_ORGANIZATION_GETTEACHERENROLLCARTYPE = 0x3002; //机构获取教练允许招生的车型
    public static final short CMD_ORGANIZATION_GETBASESETINFO = 0x3003; //机构获取基础设置配置
    public static final short CMD_ORGANIZATION_GETTRAINPRODUCTTIMEAREA = 0x3004; //机构获取培训产品时间范围
    public static final short CMD_ORGANIZATION_GETTRAINPRODUCTLIST = 0x3005; //机构获取培训产品列表
    public static final short CMD_ORGANIZATION_GETORDERINFO = 0x3006; //机构获取订单信息
    public static final short CMD_ORGANIZATION_FUNDSMGR = 0x3007; //机构账户管理接口
    public static final short CMD_WEB_INDEX = 0x4001; //Index.html
    public static final short CMD_WEB_CHECKCODE = 0x4011; //随机验证码
    public static final short CMD_WEB_EMAILRESETPWD = 0x4013; //邮件找回登录密码
    public static final short CMD_WEB_TRAINSTATUS = 0x4101; //train/trainstatus.html
    public static final short CMD_WEB_RETRAINDETAIL = 0x4105; //train/retraindetail.html
    public static final short CMD_WEB_AUTHINFO = 0x4107; //train/authinfo.html
    public static final short CMD_WEB_MYLAIO = 0x4201; //account/mylaio.html
    public static final short CMD_WEB_MYINFO = 0x4203; //account/myinfo.html
    public static final short CMD_WEB_ACCOUNT = 0x4205; //account/account.html
    public static final short CMD_WEB_ORDERTRAINING = 0x4207; //account/ordertraining.html
    public static final short CMD_WEB_FAVSHARE = 0x4211; //account/favshare.html
    public static final short CMD_WEB_ORDERVERIFY = 0x4221; //订单验证
    public static final short CMD_WEB_PAYCOMPLETE = 0x4222; //订单支付完成
    public static final short CMD_WEB_PAYSUCCESS = 0x4223; //订单支付成功页面
    public static final short CMD_WEB_PAYFAILURE = 0x4225; //订单支付失败页面
    public static final short CMD_WEB_GETUSERNAME = 0x4231; //静态页面获取已登录的用户名
    public static final short CMD_WEB_MOBILE_PAYCALL = 0x4301; //支付宝手机支付网页回调
    public static final short CMD_WEB_WEB_PAYCALL = 0x4302; //支付宝网站支付网页回调
    public static final short CMD_WEB_WEB_NeedPaySum = 0x4305; //网站计算还需支付金额（抵用来噢劵，来噢余额后）
    public static final short CMD_WEB_DPSITE_SITELIST = 0x4901; //
    public static final short CMD_WEB_DPSITE_STUINFO = 0x4903; //
    public static final short CMD_SITE_TESTSUBLIST = 0x5101; //获取考场可预约科目列表
    public static final short CMD_SITE_TESTDATESLIST = 0x5102; //获取考场某可预约科目对应可预约日期列表
    public static final short CMD_SITE_TESTTIMESLIST = 0x5103; //获取考场某可预约科目对应可预约时段列表
    public static final short CMD_SITE_GEGCOACHMSG = 0x5104; //通过教练手机号获取教练相关信息
    public static final short CMD_SITE_COMMITSTUREGISTER = 0x5105; //提交学员预约信息
    public static final short CMD_SITE_GETTIMESPRICE = 0x5106; //获取学时价格
    public static final short CMD_SITE_CANCELCHARGESORDER = 0x5107; //学员端取消考场已支付预约订单
    public static final short CMD_PRO_GUIDE = 0x1253; //学员我要报名找教练引导
    public static final short CMD_TRAINSITE_EDIT = 0x2867; //教练端_提交自定义训练场地
    public static final short CMD_SHARECODEANDINFO = 0x2477; //获取我的优惠码及分享信息
    public static final short CMD_GET_MYIMCOME = 0x2462; //学员端获取我的积分（收益）
    public static final short CMD_IMCOME_SHIFT_INTO_SUM = 0x2466; //(学员)将收益转入来噢余额
    public static final short CMD_GET_LOGOADVERTISEMENT = 0x1532; //获取启动界面闪屏信息(用默认Key加解密)
    public static final short CMD_ONLINEAPPLY_CLASSIFY = 0x2965;//教练端_线上报名学员分类
    public static final short CMD_ONLINEAPPLY_LIST = 0x2966;//教练端_线上报名学员列表
    public static final short CMD_ONLINEAPPLY_DETAIL = 0x2967;//教练端_获取线上报名学员各项款项明细
    public static final short CMD_ONLINEAPPLY_TOPCASHBACK = 0x2968;//教练端_提交线上报名学员尾款返款
    public static final short CDM_GET_ALIPAYACCOUNT_LIST = 0x2470;//获取有效的支付宝账号列表
    public static final short CDM_SET_AUTONOMOUSLY_ORDER = 0x2483;//提交自主预约账号信息
    public static final short CDM_DEL_AUTONOMOUSLY_ORDER = 0x2484;//删除自主预约账号信息
    public static final short CDM_GET_AUTONOMOUSLY_ORDER = 0x2485;//获取自主预约账号信息列表
    public static final short CMD_GET_ACTIVITIESLIST = 0x1021;//获取来噢活动列表（用默认Key加解密）
    public static final short CMD_GET_ARTICLESLIST = 0x1023;//获取驾考文章列表（用默认Key加解密）
    public static final short CMD_GET_ACTIVITYFORINDEX = 0x1025;//获取来噢最新活动，用于首页显示（用默认Key加解密）
    public static final short CMD_GET_ADDUPREADED = 0x1027;//查看文章详情请求，用于累计阅读量（用默认Key加解密）
    public static final short CMD_GET_FREEEXPERIENCEMESSAGE = 0x2998;//学员端_获取学员免费体验学车介绍信息（用默认Key加解密）
    public static final short CMD_SUBMIT_FREEEXPERIENCE = 0x2999;//学员端_提交学员免费体验学车
    public static final short CMD_SUBMIT_APPLICANT_IDCARD = 0x2881;//提交投保人身份证正、背面图片
    public static final short CMD_GET_APPLICANT_IDCARD = 0x2882;//获取投保人身份证正、背面图片
    public static final short CMD_GET_APPLICANT_IDCARD_LIST = 0x2883;//获取来噢学车保险投保人列表
    public static final short CMD_GET_DAILY_PROMOTION_LIST = 0x1018;//获取来噢学车每日推荐列表 2016.7.25新增
    public static final short CMD_GET_NEWHOMEFRAGMENTINFO = 0x2222;//教练App首页(用默认Key加解密)
    public static final short CMD_GET_QRCODE = 0x2223;//教练获取我的二维码(用默认Key加解密)
    public static final short CMD_GET_RECRUITSTUDENTS = 0x2229;//教练获取老学员招生Banner列表
    public static final short CMD_GET_RECRUITSTUDENTS_GET = 0x2228;//教练获取老学员招生设置
    public static final short CMD_GET_RECRUITSTUDENTS_SET = 0x2225;//教练设置老学员招生
    public static final short CMD_GET_RECRUITSTUDENTS_FORWARD = 0x2226;//教练转发老学员招生邀请
    public static final short CMD_GET_ENROLLMENTSTATISTICS = 0x2227;//教练，学员获取老学员招生统计
    public static final short CMD_MODIFY_ORDER_AMOUNT = 0x2230;//教练 修改订单金额
    public static final short CMD_GET_MYINFO = 0x1600;//学员获取我的信息
    public static final short CMD_GET_ORDERQRCODE = 0x1611;//学员获取订单消费码的二维码
    public static final short CMD_GET_EVALUATE = 0x1613;//学员端获取评价标签列表
    public static final short CMD_PRODUCTREG_HOMEREGCOMBOLIST_GO = 0x1621; //学员首页学车套餐列表
    public static final short CMD_TRAINPICKUP_STUGETCOACHGPS_GO = 0x1622; //学员获取教练的GPS位置信息

    public static final short CMD_GET_OLDSTUDENT_INVITE = 0x2886;//学员端_获取老学员招生邀请的分享信息
    public static final short CMD_TRAINPICKUP_STUUPGPS_GO = 0x2830; //学员上传GPS位置信息
    public static final short CMD_TRAINPICKUP_ADDSTU_GO = 0x2831; //教练添加接送学员
    public static final short CMD_TRAINPICKUP_DELSTU_GO = 0x2832; //教练删除已添加的接送学员
    public static final short CMD_TRAINPICKUP_STUABOARD_GO = 0x2833; //教练操作学员上车
    public static final short CMD_TRAINPICKUP_STULIST_GO = 0x2835; //教练获取当天接送学员列表

    public static final short CMD_ORDERTRAIN_TRAINSTULIST_GO = 0x2837; //教练获取当天待培训学员列表
    public static final short CMD_ORDERTRAIN_TRAINTYPESPEC_GO = 0x2838; //教练获取培训班型属性列表
    public static final short CMD_ORDERTRAIN4QUICK_COACH = 0x2839; //教练端_手动添加学员快速生成订单
    public static final short CMD_ORDERTRAIN_TRAINOPERATESTART_GO = 0x2840; //教练操作学员开始培训
    public static final short CMD_ORDERTRAIN_TRAINOPERATEABOARD_GO = 0x2841; //教练操作学员暂停培训(下车)、继续培训(上车)
    public static final short CMD_ORDERTRAIN_TRAINOPERATEFINISH_GO = 0x2843; //教练操作学员结束培训并填写培训内容
    public static final short CMD_ORDERTRAIN_STUAPPOINTTRAINORDERLIST_GO = 0x2845; //学员我的预约培训列表
    public static final short CMD_ORDERTRAIN_STUTRAINCOMPLAINTLIST_GO = 0x2846; //学员培训订单获取投诉事项列表
    public static final short CMD_ORDERTRAIN_STUSUBMITTRAINCOMPLAINT_GO = 0x2847; //学员提交培训订单投诉
    public static final short CMD_ORDERTRAIN_STUGETCURRENTTRAINORDER_GO = 0x2849; //学员根据开始培训消息获取简要培训信息

    public static final short GPS_GETCARLIST = 0x2101;//监控获取车辆列表
    public static final short GPS_GETGPSDATA = (short) 0x8001;//通过设备号获取gps数据
    public static final short GPS_GETGPSDATA_MONITOR = (short) 0x8002;//通过设备号监听gps数据

}
