package com.hn.book.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

/**
 * @Description: 书籍
 * @Author: jeecg-boot
 * @Date:   2024-03-09
 * @Version: V1.0
 */

public class HnBook implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    private String id;
	/**创建人*/
    private String createBy;
	/**创建日期*/
    private Date createTime;
	/**更新人*/
    private String updateBy;
	/**更新日期*/
    private Date updateTime;
	/**所属部门*/
    private String sysOrgCode;
	/**标题*/
    private String title;
	/**作者*/
    private String author;
	/**出版年份*/
    private Date publishtime;
	/**SBN*/
    private String sbn;
	/**书名*/
    private String bookname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSysOrgCode() {
        return sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getSbn() {
        return sbn;
    }

    public void setSbn(String sbn) {
        this.sbn = sbn;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
