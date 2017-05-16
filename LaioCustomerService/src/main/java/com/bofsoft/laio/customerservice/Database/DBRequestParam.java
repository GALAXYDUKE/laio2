package com.bofsoft.laio.customerservice.Database;

/**
 * @author admin
 */
public class DBRequestParam<T> {
    /**
     * @author yedong
     */

    public final static int One_PENDING = 0x01; // 初始状态
    public final static int TWO_GETTING_UPDATE_LIST = 0x02; // 获取服务器数据库更新时间
    public final static int THREE_CHECK_IS_UPDATE = 0x03; // 比较本地更新时间
    public final static int FOUR_GETTING_UPDATE = 0x04; // 获取更新的数据
    public final static int FIVE_FINISH = 0x05; // 更新完成


    // private int id = 0;
    /**
     * 是否有超时等问题（true-不更新本地更新时间）
     */
    private boolean isHasException = false;
    private int curStatus = One_PENDING;

    public DBCallBackImp<T> dbCallBack;

    /**
     * 请求的表的Id
     */
    private int tableNum;

    /**
     * 请求的表名
     */
    private String tableName;
    /**
     * 请求的数据类型
     */
    private Class<T> dataClass;
    /**
     * 查询的条件
     */
    private String selection;
    /**
     * 查询的条件参数
     */
    private String[] selectionArgs;
    /**
     * 分组
     */
    private String groupBy;
    /**
     * 过滤
     */
    private String having;
    /**
     * 排序
     */
    private String orderBy;

    public DBRequestParam(int tableNum, Class<T> dataClass, String selection, String[] selectionArgs,
                          String groupBy, String having, String orderBy, DBCallBackImp<T> callBack) {
        this.tableNum = tableNum;
        this.tableName = TableManager.getNameByNum(tableNum);
        this.dataClass = dataClass;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.groupBy = groupBy;
        this.having = having;
        this.orderBy = orderBy;
        this.dbCallBack = callBack;
        // this.id = AtomicIntegerUtil.getIncrementID();
    }

    public DBRequestParam(String tableName, Class<T> dataClass, String selection,
                          String[] selectionArgs, String groupBy, String having, String orderBy,
                          DBCallBackImp<T> callBack) {
        this.tableNum = TableManager.getNumByName(tableName);
        this.tableName = tableName;
        this.dataClass = dataClass;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.groupBy = groupBy;
        this.having = having;
        this.orderBy = orderBy;
        this.dbCallBack = callBack;
        // this.id = AtomicIntegerUtil.getIncrementID();
    }

    /**
     * 获取是否有超时等问题
     *
     * @return
     */
    public boolean isHasException() {
        return isHasException;
    }

    /**
     * 设置是否有超时等问题
     *
     * @return
     */
    public void setHasException(boolean isHasException) {
        this.isHasException = isHasException;
    }

    /**
     * 获取当前请求的状态
     *
     * @return
     */
    public int getCurStatus() {
        return curStatus;
    }

    /**
     * 设置当前请求的状态
     *
     * @return
     */
    public void setCurStatus(int curStatus) {
        this.curStatus = curStatus;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class<T> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<T> dataClass) {
        this.dataClass = dataClass;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String[] getSelectionArgs() {
        return selectionArgs;
    }

    public void setSelectionArgs(String[] selectionArgs) {
        this.selectionArgs = selectionArgs;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getHaving() {
        return having;
    }

    public void setHaving(String having) {
        this.having = having;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
