package nettydemo.spring;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class Message {

    public static final int HEADER_LEN = 23;// 包头长度
    public static final int MSGLEN_POSITION = 8;// msgLen字段position偏移量
    public static final String HEADTAG = "#XRPC#";// 包起始符
    public static final int HEADTAG_LEN = 6;// 包起始符长度

    /*** 6字节 默认：#XRPC# */
    private String headTag = HEADTAG;
    /*** 协议主版本号 */
    private byte mainVersion;
    /*** 协议子版本号 */
    private byte subVersion;
    /*** 包体总长度，cookie+消息体 */
    private short msgLen;
    /*** 命令号 */
    private short commandID;
    /*** 序列号 */
    private int sequeeceNo;
    /*** 加密方式: 0不加密, 其他值为约定的加密方式 */
    private byte encType;
    /*** cookie长度,cookie从包头结束位置开始 */
    private short cookieLen;
    /**
     * 保留字段
     */
    private int reserve;
    /*** cookie */
    private String cookie;
    /*** 包体 */
    private JSONObject json;

    // -----以下为本地使用的字段
    /*** serverID记录该包需要发给哪个服务器 */
    private int serverID = -1;
    /*** 创建时间 */
    private Date createTime;

    public Message() {
        this.createTime = new Date();
    }

    public Message(short commandID, JSONObject jsonObject) {
        this.mainVersion = 1;
        this.subVersion = 1;
        this.sequeeceNo = (int) (System.currentTimeMillis() / 1000);
        this.encType = 0;
        this.cookieLen = 0;
        this.reserve = 0;
        this.msgLen = 0;
        this.commandID = commandID;
        this.json = jsonObject;
        this.cookie = "";
        this.createTime = new Date();
    }

    public Message(short commandID, String cookie, JSONObject jsonObject) {
        this.mainVersion = 1;
        this.subVersion = 1;
        this.sequeeceNo = (int) (System.currentTimeMillis() / 1000);
        this.encType = 0;
        this.cookieLen = 0;
        this.reserve = 0;
        this.msgLen = 0;
        this.commandID = commandID;
        this.json = jsonObject;
        this.cookie = cookie;
        this.createTime = new Date();
    }

    /**
     * @return the headTag
     */
    public String getHeadTag() {
        return headTag;
    }

    /**
     * @param headTag the headTag to set
     */
    public void setHeadTag(String headTag) {
        this.headTag = headTag;
    }

    /**
     * @return the mainVersion
     */
    public byte getMainVersion() {
        return mainVersion;
    }

    /**
     * @param mainVersion the mainVersion to set
     */
    public void setMainVersion(byte mainVersion) {
        this.mainVersion = mainVersion;
    }

    /**
     * @return the subVersion
     */
    public byte getSubVersion() {
        return subVersion;
    }

    /**
     * @param subVersion the subVersion to set
     */
    public void setSubVersion(byte subVersion) {
        this.subVersion = subVersion;
    }

    /**
     * @return the msgLen
     */
    public short getMsgLen() {
        return msgLen;
    }

    /**
     * @param msgLen the msgLen to set
     */
    public void setMsgLen(short msgLen) {
        this.msgLen = msgLen;
    }

    /**
     * @return the commandID
     */
    public short getCommandID() {
        return commandID;
    }

    /**
     * @param commandID the commandID to set
     */
    public void setCommandID(short commandID) {
        this.commandID = commandID;
    }

    /**
     * @return the sequeeceNo
     */
    public int getSequeeceNo() {
        return sequeeceNo;
    }

    /**
     * @param sequeeceNo the sequeeceNo to set
     */
    public void setSequeeceNo(int sequeeceNo) {
        this.sequeeceNo = sequeeceNo;
    }

    /**
     * @return the encType
     */
    public byte getEncType() {
        return encType;
    }

    /**
     * @param encType the encType to set
     */
    public void setEncType(byte encType) {
        this.encType = encType;
    }

    /**
     * @return the cookieLen
     */
    public short getCookieLen() {
        return cookieLen;
    }

    /**
     * @param cookieLen the cookieLen to set
     */
    public void setCookieLen(short cookieLen) {
        this.cookieLen = cookieLen;
    }

    /**
     * @return the reserve
     */
    public int getReserve() {
        return reserve;
    }

    /**
     * @param reserve the reserve to set
     */
    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    /**
     * @return the cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * @param cookie the cookie to set
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * @return the json
     */
    public JSONObject getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(JSONObject json) {
        this.json = json;
    }

    /**
     * @return the serverID
     */
    public int getServerID() {
        return serverID;
    }

    /**
     * @param serverID the serverID to set
     */
    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 拷贝一个新的message对象
     *
     * @param msg
     * @return
     */
    public Message copyNewMessage() {
        Message message = new Message();
        message.mainVersion = this.mainVersion;
        message.subVersion = this.subVersion;
        message.msgLen = this.msgLen;
        message.commandID = this.commandID;
        message.sequeeceNo = this.sequeeceNo;
        message.encType = this.encType;
        message.cookieLen = this.cookieLen;
        message.reserve = this.reserve;
        message.cookie = new String(this.cookie);
        message.json = JSONObject.parseObject(this.json.toJSONString());
        message.serverID = this.serverID;
        message.createTime = new Date(this.createTime.getTime());
        return message;
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(this.headTag).append(",").append(this.mainVersion).append(",").append(this.subVersion).append(",").append(this.msgLen).append(",")
                .append(Integer.toHexString(this.commandID)).append(",").append(this.sequeeceNo).append(",").append(this.encType).append(",")
                .append(this.cookieLen).append(",").append(this.reserve).append(",").append(this.cookie).append(",");
        if (this.json != null) {
            out.append(this.json.toJSONString());
        }
        return out.toString();
    }
}
