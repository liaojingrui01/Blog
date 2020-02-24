package com.ljr.blog.po;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_letter")
public class Letter {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;
    private String email;
    private String content;

    private boolean viewed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Letter(String nickname, String email, String content) {
        this.nickname = nickname;
        this.email = email;
        this.content = content;
    }

    public Letter() {
    }

    public Letter(String nickname, String email, String content, boolean viewed, Date createTime) {
        this.nickname = nickname;
        this.email = email;
        this.content = content;
        this.viewed = viewed;
        this.createTime = createTime;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Letter{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
