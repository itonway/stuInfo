package com.info.manage.util;

import java.io.Serializable;

/**
 * @author xxy
 * @ClassName BussinessMsg
 * @Description todo
 * @Date 2019/1/29 14:39
 **/
public class BussinessMsg implements Serializable {
    private String returnCode;
    private String returnMessage;
    private Object returnData;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }


}
