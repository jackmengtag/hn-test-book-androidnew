package com.yangna.lbdsp.common.swiperefreshLayout;

/**
 * @author jingbin
 */
public class DataItemBean {

    private String title;
    private String content;
    private String des;

    private String type;
    private Class<?> cls;

    public DataItemBean() {
    }

    public DataItemBean(String title, Class<?> cls) {
        this.title = title;
        this.cls = cls;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public String getTitle() {
        return title;
    }

    public DataItemBean setTitle(String title) {
        this.title = title;
        return this;
    }
}
