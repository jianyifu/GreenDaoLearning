package com.fjy.greendaolearning.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by fujianyi on 2017/5/4.
 */
@Entity(
        // Whether an all properties constructor should be generated.
        // A no-args constructor is always required.
        generateConstructors = true,

        // Whether getters and setters for properties should be generated if missing.
        generateGettersSetters = true)
public class PushMsg {
    @Id(autoincrement = true)
    private Long id;
//    @Unique
    private String title;
//    @Index(unique = true)
    private String description;
    private Boolean isRead;
    private Integer type;
    private Date createTime;
    @Generated(hash = 138216280)
    public PushMsg(Long id, String title, String description, Boolean isRead, Integer type,
            Date createTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isRead = isRead;
        this.type = type;
        this.createTime = createTime;
    }

    @Generated(hash = 868472971)
    public PushMsg() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
