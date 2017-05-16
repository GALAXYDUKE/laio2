package com.bofsoft.laio.customerservice.DataClass.Order;

import java.util.ArrayList;

/**
 * Created by jason.xu on 2017/3/28.
 */

public class OrderDetailRsp {
    private Integer Id;
    private String Num;
    private String CustomerCode;
    private String Name;
    private Float DealSum;
    private String View;
    private String ViewOrder;
    private String ViewPro;
    private String CoachTel;
    private Integer SendMsg;
    private String OrderTime;
    private String CoachUserUUID;
    private String CoachName;
    private String ProPicUrl;
    private String BuyerUUID;
    private String BuyerName;
    private String BuyerTel;
    private Integer OrderType;
    private Integer TrainType;
    private Integer Status;
    private Integer StatusTrain;
    private Integer CanCancel;
    private Integer PayDeposit;
    private Integer TrainRemainMin;
    private Integer StatusAppraise;
    private Integer StatusAccepted;
    private Double CancelFine;
    private String CancelTime;
    private String CancelReason;
    private String StartTime;
    private String EndTime;
    private Integer RefundStatus;
    private Double RefundSum;
    private String DeadTime;
    private String ConfirmTime;
    private String StartAddr;
    private String DistrictMC;
    private String TrainSite;
    private String SiteAddr;
    private Integer Amount;
    private Integer TestSubId;
    private Double DepositFee;
    private Integer DepositStatus;
    private Double FinalFee;
    private Integer FinalStatus;
    private String RefundRequestTime;
    private String FirstStuName;
    private String FirstStuMobile;
    private String FirstStuUUID;
    private String FirstStuAppointMsg;
    private ArrayList<Timelist> TimeList = new ArrayList<>();
    private ArrayList<Addrlist> AddrList = new ArrayList<>();
    private ArrayList<Vaslist> VasList = new ArrayList<>();
    private String ModOrder;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Float getDealSum() {
        return DealSum;
    }

    public void setDealSum(Float dealSum) {
        DealSum = dealSum;
    }

    public String getView() {
        return View;
    }

    public void setView(String view) {
        View = view;
    }

    public String getViewOrder() {
        return ViewOrder;
    }

    public void setViewOrder(String viewOrder) {
        ViewOrder = viewOrder;
    }

    public String getViewPro() {
        return ViewPro;
    }

    public void setViewPro(String viewPro) {
        ViewPro = viewPro;
    }

    public String getCoachTel() {
        return CoachTel;
    }

    public void setCoachTel(String coachTel) {
        CoachTel = coachTel;
    }

    public Integer getSendMsg() {
        return SendMsg;
    }

    public void setSendMsg(Integer sendMsg) {
        SendMsg = sendMsg;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getCoachUserUUID() {
        return CoachUserUUID;
    }

    public void setCoachUserUUID(String coachUserUUID) {
        CoachUserUUID = coachUserUUID;
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public String getProPicUrl() {
        return ProPicUrl;
    }

    public void setProPicUrl(String proPicUrl) {
        ProPicUrl = proPicUrl;
    }

    public String getBuyerUUID() {
        return BuyerUUID;
    }

    public void setBuyerUUID(String buyerUUID) {
        BuyerUUID = buyerUUID;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getBuyerTel() {
        return BuyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        BuyerTel = buyerTel;
    }

    public Integer getOrderType() {
        return OrderType;
    }

    public void setOrderType(Integer orderType) {
        OrderType = orderType;
    }

    public Integer getTrainType() {
        return TrainType;
    }

    public void setTrainType(Integer trainType) {
        TrainType = trainType;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getStatusTrain() {
        return StatusTrain;
    }

    public void setStatusTrain(Integer statusTrain) {
        StatusTrain = statusTrain;
    }

    public Integer getCanCancel() {
        return CanCancel;
    }

    public void setCanCancel(Integer canCancel) {
        CanCancel = canCancel;
    }

    public Integer getPayDeposit() {
        return PayDeposit;
    }

    public void setPayDeposit(Integer payDeposit) {
        PayDeposit = payDeposit;
    }

    public Integer getTrainRemainMin() {
        return TrainRemainMin;
    }

    public void setTrainRemainMin(Integer trainRemainMin) {
        TrainRemainMin = trainRemainMin;
    }

    public Integer getStatusAppraise() {
        return StatusAppraise;
    }

    public void setStatusAppraise(Integer statusAppraise) {
        StatusAppraise = statusAppraise;
    }

    public Integer getStatusAccepted() {
        return StatusAccepted;
    }

    public void setStatusAccepted(Integer statusAccepted) {
        StatusAccepted = statusAccepted;
    }

    public Double getCancelFine() {
        return CancelFine;
    }

    public void setCancelFine(Double cancelFine) {
        CancelFine = cancelFine;
    }

    public String getCancelTime() {
        return CancelTime;
    }

    public void setCancelTime(String cancelTime) {
        CancelTime = cancelTime;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String cancelReason) {
        CancelReason = cancelReason;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Integer getRefundStatus() {
        return RefundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        RefundStatus = refundStatus;
    }

    public Double getRefundSum() {
        return RefundSum;
    }

    public void setRefundSum(Double refundSum) {
        RefundSum = refundSum;
    }

    public String getDeadTime() {
        return DeadTime;
    }

    public void setDeadTime(String deadTime) {
        DeadTime = deadTime;
    }

    public String getConfirmTime() {
        return ConfirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        ConfirmTime = confirmTime;
    }

    public String getStartAddr() {
        return StartAddr;
    }

    public void setStartAddr(String startAddr) {
        StartAddr = startAddr;
    }

    public String getDistrictMC() {
        return DistrictMC;
    }

    public void setDistrictMC(String districtMC) {
        DistrictMC = districtMC;
    }

    public String getTrainSite() {
        return TrainSite;
    }

    public void setTrainSite(String trainSite) {
        TrainSite = trainSite;
    }

    public String getSiteAddr() {
        return SiteAddr;
    }

    public void setSiteAddr(String siteAddr) {
        SiteAddr = siteAddr;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Integer getTestSubId() {
        return TestSubId;
    }

    public void setTestSubId(Integer testSubId) {
        TestSubId = testSubId;
    }

    public Double getDepositFee() {
        return DepositFee;
    }

    public void setDepositFee(Double depositFee) {
        DepositFee = depositFee;
    }

    public Integer getDepositStatus() {
        return DepositStatus;
    }

    public void setDepositStatus(Integer depositStatus) {
        DepositStatus = depositStatus;
    }

    public Double getFinalFee() {
        return FinalFee;
    }

    public void setFinalFee(Double finalFee) {
        FinalFee = finalFee;
    }

    public Integer getFinalStatus() {
        return FinalStatus;
    }

    public void setFinalStatus(Integer finalStatus) {
        FinalStatus = finalStatus;
    }

    public String getRefundRequestTime() {
        return RefundRequestTime;
    }

    public void setRefundRequestTime(String refundRequestTime) {
        RefundRequestTime = refundRequestTime;
    }

    public String getFirstStuName() {
        return FirstStuName;
    }

    public void setFirstStuName(String firstStuName) {
        FirstStuName = firstStuName;
    }

    public String getFirstStuMobile() {
        return FirstStuMobile;
    }

    public void setFirstStuMobile(String firstStuMobile) {
        FirstStuMobile = firstStuMobile;
    }

    public String getFirstStuUUID() {
        return FirstStuUUID;
    }

    public void setFirstStuUUID(String firstStuUUID) {
        FirstStuUUID = firstStuUUID;
    }

    public String getFirstStuAppointMsg() {
        return FirstStuAppointMsg;
    }

    public void setFirstStuAppointMsg(String firstStuAppointMsg) {
        FirstStuAppointMsg = firstStuAppointMsg;
    }

    public ArrayList<Timelist> getTimeList() {
        return TimeList;
    }

    public void setTimeList(ArrayList<Timelist> timeList) {
        TimeList = timeList;
    }

    public ArrayList<Addrlist> getAddrList() {
        return AddrList;
    }

    public void setAddrList(ArrayList<Addrlist> addrList) {
        AddrList = addrList;
    }

    public ArrayList<Vaslist> getVasList() {
        return VasList;
    }

    public void setVasList(ArrayList<Vaslist> vasList) {
        VasList = vasList;
    }

    public String getModOrder() {
        return ModOrder;
    }

    public void setModOrder(String modOrder) {
        ModOrder = modOrder;
    }

    public static class Timelist {
        private String Name;
        private String ClassType;
        private String Times;
        private Integer Count;
        private double Price;
        private double Total;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getClassType() {
            return ClassType;
        }

        public void setClassType(String classType) {
            ClassType = classType;
        }

        public String getTimes() {
            return Times;
        }

        public void setTimes(String times) {
            Times = times;
        }

        public Integer getCount() {
            return Count;
        }

        public void setCount(Integer count) {
            Count = count;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public double getTotal() {
            return Total;
        }

        public void setTotal(double total) {
            Total = total;
        }
    }

    public static class Vaslist {
        private String Id;
        private Integer VasType;
        private String Name;
        private Integer VasAmount;
        private double VasPrice;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public Integer getVasType() {
            return VasType;
        }

        public void setVasType(Integer vasType) {
            VasType = vasType;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Integer getVasAmount() {
            return VasAmount;
        }

        public void setVasAmount(Integer vasAmount) {
            VasAmount = vasAmount;
        }

        public double getVasPrice() {
            return VasPrice;
        }

        public void setVasPrice(double vasPrice) {
            VasPrice = vasPrice;
        }
    }

    public static class Addrlist {
        public String getAddr() {
            return Addr;
        }

        public void setAddr(String addr) {
            Addr = addr;
        }

        private String Addr;
    }
}
