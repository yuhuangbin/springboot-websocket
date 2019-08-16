package com.yuhb.websocket.entity;

/**
 * Description:
 * author: yu.hb
 * Date: 2019-08-16
 */
public class ResponseMessage {
    private String returnMsg;

    public ResponseMessage(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
