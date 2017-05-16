package com.bofsoft.laio.tcp;

public interface ResponseListenerImp {
    /**
     * @param code   commandid
     * @param result
     */
    void messageBack(int code, String result);

    /**
     * @param code      commandid
     * @param lenght    获取数据长度
     * @param tcplenght 请求通讯总长度
     * @param result
     */
    void messageBack(int code, int lenght, int tcplenght);
}
