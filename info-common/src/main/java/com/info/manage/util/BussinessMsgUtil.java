package com.info.manage.util;

/**
 * @author xxy
 * @ClassName BussinessMsgUtil
 * @Description todo
 * @Date 2019/1/29 14:40
 **/
public class BussinessMsgUtil {



    /**
     * 返回消息代码code 和 message
     *
     * @param bussinessCode 返回码
     * @return
     */
    public static BussinessMsg returnCodeMessage(BussinessCode bussinessCode) {
        return returnCodeMessage(bussinessCode, null);
    }

    /**
     * 返回消息代码和数据
     *
     * @param bussinessCode 返回码
     * @param returnData    返回数据
     * @return
     */
    public static BussinessMsg returnCodeMessage(BussinessCode bussinessCode, Object returnData) {
        BussinessMsg bussinessMsg = new BussinessMsg();
        bussinessMsg.setReturnCode(bussinessCode.getCode());
        bussinessMsg.setReturnMessage(bussinessCode.getMsg());
        bussinessMsg.setReturnData(returnData);
        return bussinessMsg;
    }

}
