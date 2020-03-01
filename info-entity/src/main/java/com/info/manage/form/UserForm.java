package com.info.manage.form;

public class UserForm {
    private String userName;
    private String sex;
    private String createTimeStr;
    private String createTimeEnd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    @Override
    public String toString(){
        return "userName:" + userName + "，sex:" + sex + "，createTimeStr:" + createTimeStr+"，createTimeEnd:" + createTimeEnd;
    }

}
