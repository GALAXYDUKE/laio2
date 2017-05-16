package com.bofsoft.laio.tcp;

/**
 * 数据回调类
 *
 * @author yedong
 */
public class ResponseListener implements IResponseListener {
    /**
     * @param code   commandId
     * @param result
     */
    public void messageBack(int code, String result) {
    }

    /**
     * @param code      commandId
     * @param lenght    获取数据长度
     * @param tcplenght 请求通讯总长度
     */
    public void messageBack(int code, int lenght, int tcplenght) {
    }

    /**
     * @param errorCode 异常代码
     * @param error     异常信息
     */
    public void messageBackFailed(int errorCode, String error) {
    }
}
