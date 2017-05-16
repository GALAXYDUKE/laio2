package com.bofsoft.laio.tcp;

import com.bofsoft.laio.common.AES;
import com.bofsoft.laio.common.AtomicIntegerUtil;
import com.bofsoft.laio.common.CRC16;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet {

    private int id = AtomicIntegerUtil.getIncrementID();
    private byte[] data;
    private TcpHeader tcpHeader = new TcpHeader();
    private int index = 0;
    private byte[] content = null;
    private short commandid;

    /**
     * 解数据包的头部
     */
    public static TcpHeader unpackHeader(byte[] data) throws IOException {
        int dr;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(data));
        TcpHeader tcpHeader = new TcpHeader();
        dr = dataInputStream.read(tcpHeader.headName);
        if (0 == dr) {
            return null;
        }
        tcpHeader.setCommendid(dataInputStream.readShort());
        tcpHeader.setCrc(dataInputStream.readShort());
        tcpHeader.setLength(dataInputStream.readInt());
        tcpHeader.setIndex(dataInputStream.readInt());
        tcpHeader.setCodeNum(dataInputStream.readShort());
        dr = dataInputStream.read(tcpHeader.session);
        if (0 == dr) {
            return null;
        }
        return tcpHeader;
    }

    public short getCommandid() {
        return commandid;
    }

    public int initPack(short cid, byte[] request) {
        commandid = cid;
        content = request;
        return getIndex();
    }

    public int getId() {
        return index;
    }

    public int pack() throws IOException {
        byte[] tcpContent = null;

        // 加密内容部分
        if (content != null) {
            if (commandid == CommandCodeTS.CMD_MOBILE_LOGIN || commandid == CommandCodeTS.GPS_GETGPSDATA || commandid == CommandCodeTS.GPS_GETGPSDATA_MONITOR) {
                tcpContent = AES.encrypt(content, ConfigAll.initKey.getBytes());
            } else {
                tcpContent = AES.encrypt(content, ConfigAll.Key.getBytes());
            }
        }

        Integer index = getIndex();
        short crc = 0;
        int len = TcpHeader.getHeaderlen();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        tcpHeader.headName = ConfigAll.headerName.getBytes();
        tcpHeader.commandid = commandid;
        dos.write(ConfigAll.headerName.getBytes());
        dos.writeShort(commandid);
        if (null != tcpContent) {
            crc = CRC16.crcTable(tcpContent);
            len = tcpContent.length + TcpHeader.getHeaderlen();
        }
        tcpHeader.crc = crc;
        tcpHeader.length = len;
        tcpHeader.index = index;
        tcpHeader.codeNum = ConfigAll.CodeNum;
        tcpHeader.session = ConfigAll.getSession();

        dos.writeShort(crc);
        dos.writeInt(len);
        dos.writeInt(index);
        dos.writeShort(ConfigAll.CodeNum);
        dos.write(ConfigAll.getSession());
        if (null != tcpContent)
            dos.write(tcpContent);
        dos.flush();
        data = bos.toByteArray();
        return index;
    }

    /**
     * @param data 待解包数据，包含header和data
     */
    public boolean unpack(byte[] data) throws IOException {
        int dr;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(data));
        tcpHeader = new TcpHeader();
        dr = dataInputStream.read(tcpHeader.headName);
        if (0 == dr) {
            return false;
        }
        tcpHeader.setCommendid(dataInputStream.readShort());
        tcpHeader.setCrc(dataInputStream.readShort());
        tcpHeader.setLength(dataInputStream.readInt());
        tcpHeader.setIndex(dataInputStream.readInt());
        tcpHeader.setCodeNum(dataInputStream.readShort());
        dr = dataInputStream.read(tcpHeader.session);
        if (0 == dr) {
            return false;
        }
        if (tcpHeader.length > data.length)
            return false;
        byte[] bytes = new byte[tcpHeader.getLength() - TcpHeader.getHeaderlen()];
        dr = dataInputStream.read(bytes);
        if (0 == dr) {
            return false;
        }

        // 解密数据内容
        String str;
        if (tcpHeader.commandid == CommandCodeTS.CMD_MOBILE_LOGIN || tcpHeader.commandid == CommandCodeTS.GPS_GETGPSDATA || tcpHeader.commandid == CommandCodeTS.GPS_GETGPSDATA_MONITOR) {
            str = AES.decrypt(bytes, ConfigAll.initKey.getBytes());
        } else {
            str = AES.decrypt(bytes, ConfigAll.Key.getBytes());
        }
        if (null != str) {
            this.data = str.getBytes();
        }
        return true;
    }

    public byte[] getPacket() {
        return data;
    }

    public TcpHeader getTcpHeader() {
        return tcpHeader;
    }

    public int getIndex() {
        index = id;
        return index;
    }
}
