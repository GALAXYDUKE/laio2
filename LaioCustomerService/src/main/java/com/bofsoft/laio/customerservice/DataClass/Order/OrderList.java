package com.bofsoft.laio.customerservice.DataClass.Order;

import java.util.ArrayList;

/**
 * Created by jason.xu on 2017/3/27.
 */

public class OrderList {
    private ArrayList<Order> OrderList = new ArrayList<Order>();
    private Integer TotalCount;
    private boolean More;

    public ArrayList<Order> getOrderList() {
        return OrderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        OrderList = orderList;
    }

    public Integer getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(Integer totalCount) {
        TotalCount = totalCount;
    }

    public boolean isMore() {
        return More;
    }

    public void setMore(boolean more) {
        More = more;
    }

    public static class Order {
        private Integer Id;
        private String Num;
        private String Name;
        private String View;
        private Integer GroupDM;
        private Integer OrderType;
        private Integer Status;
        private Integer StatusTrain;
        private Integer CanCancel;
        private Integer PayDeposit;
        private Integer StatusAppraise;
        private Integer StatusAccepted;
        private Integer RefundStatus;
        private String DeadTime;
        private String ConfirmTime;
        private ArrayList<OrderVasList> VasList = new ArrayList<>();
        private String OrderTime;
        private String CoachName;
        private String CarType;
        private Double Deposit;
        private Double DealSum;
        private Integer RegType;
        private Integer TrainType;
        private Integer TestSubId;
        private String TestSub;
        private String CarModel;
        private String TrainStartTime;
        private String TrainEndTime;
        private String CustomerCode;
        private String BuyerName;
        private String BuyerTel;
        private String ProPicUrl;
        private Integer InviteCanRegister;


        public String getProPicUrl() {
            return ProPicUrl;
        }

        public void setProPicUrl(String proPicUrl) {
            ProPicUrl = proPicUrl;
        }

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

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getView() {
            return View;
        }

        public void setView(String view) {
            View = view;
        }

        public Integer getGroupDM() {
            return GroupDM;
        }

        public void setGroupDM(Integer groupDM) {
            GroupDM = groupDM;
        }

        public Integer getOrderType() {
            return OrderType;
        }

        public void setOrderType(Integer orderType) {
            OrderType = orderType;
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

        public Integer getRefundStatus() {
            return RefundStatus;
        }

        public void setRefundStatus(Integer refundStatus) {
            RefundStatus = refundStatus;
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

        public ArrayList<OrderVasList> getVasList() {
            return VasList;
        }

        public void setVasList(ArrayList<OrderVasList> vasList) {
            VasList = vasList;
        }

        public String getOrderTime() {
            return OrderTime;
        }

        public void setOrderTime(String orderTime) {
            OrderTime = orderTime;
        }

        public String getCoachName() {
            return CoachName;
        }

        public void setCoachName(String coachName) {
            CoachName = coachName;
        }

        public String getCarType() {
            return CarType;
        }

        public void setCarType(String carType) {
            CarType = carType;
        }

        public Double getDeposit() {
            return Deposit;
        }

        public void setDeposit(Double deposit) {
            Deposit = deposit;
        }

        public Double getDealSum() {
            return DealSum;
        }

        public void setDealSum(Double dealSum) {
            DealSum = dealSum;
        }

        public Integer getRegType() {
            return RegType;
        }

        public void setRegType(Integer regType) {
            RegType = regType;
        }

        public Integer getTrainType() {
            return TrainType;
        }

        public void setTrainType(Integer trainType) {
            TrainType = trainType;
        }

        public Integer getTestSubId() {
            return TestSubId;
        }

        public void setTestSubId(Integer testSubId) {
            TestSubId = testSubId;
        }

        public String getTestSub() {
            return TestSub;
        }

        public void setTestSub(String testSub) {
            TestSub = testSub;
        }

        public String getCarModel() {
            return CarModel;
        }

        public void setCarModel(String carModel) {
            CarModel = carModel;
        }

        public String getTrainStartTime() {
            return TrainStartTime;
        }

        public void setTrainStartTime(String trainStartTime) {
            TrainStartTime = trainStartTime;
        }

        public String getTrainEndTime() {
            return TrainEndTime;
        }

        public void setTrainEndTime(String trainEndTime) {
            TrainEndTime = trainEndTime;
        }

        public String getCustomerCode() {
            return CustomerCode;
        }

        public void setCustomerCode(String customerCode) {
            CustomerCode = customerCode;
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

        public Integer getInviteCanRegister() {
            return InviteCanRegister;
        }

        public void setInviteCanRegister(Integer inviteCanRegister) {
            InviteCanRegister = inviteCanRegister;
        }

    }

    public static class OrderVasList {
        private String Id;
        private Integer VasType;
        private String Name;
        private Integer VasAmount;
        private Double VasPrice;

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

        public Double getVasPrice() {
            return VasPrice;
        }

        public void setVasPrice(Double vasPrice) {
            VasPrice = vasPrice;
        }

        public Double getVasTotal() {
            return VasTotal;
        }

        public void setVasTotal(Double vasTotal) {
            VasTotal = vasTotal;
        }

        private Double VasTotal;

    }
}
