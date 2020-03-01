package com.info.manage.form;

import java.util.List;

/**
 * @author xxy
 * @ClassName MenuCardForm
 * @Description todo
 * @Date 2019/2/10 11:46
 **/
public class MenuCardForm {
    private Long id;

    private String name;

    private Long pId;

    private String url;

    private String permission;

    private String icon;

    private List<MenuCardForm> children;

    public List<MenuCardForm> getChildren() {
        return children;
    }

    public void setChildren(List<MenuCardForm> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
