package com.bofsoft.laio.tcp;

import java.io.Serializable;

public class TcpHeader implements Serializable {

    private static final long serialVersionUID = -4912618742324608997L;

    // 头信息
    public byte[] headName = new byte[4]; // 4 头文件LAIO
    public short commandid = 0; // 2 通讯协议命令编号
    public short crc = 0; // 2 校验
    public int length = 0; // 4 通讯总计长度
    public int index = 0; // 4 通讯自编序号
    public short codeNum = 0; // 2 协议版本号
    public byte[] session = new byte[16]; // 16 session
    private static final int headerLen = 4 + 2 + 2 + 4 + 4 + 2 + 16;

    public short getCommendid() {
        return commandid;
    }

    public void setCommendid(short commandid) {
        this.commandid = commandid;
    }

    public void setCrc(short crc) {
        this.crc = crc;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCodeNum(short codeNum) {
        this.codeNum = codeNum;
    }

    public static int getHeaderlen() {
        return headerLen;
    }
}
