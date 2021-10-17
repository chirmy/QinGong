package com.example.qingong2.share;

import android.widget.ImageView;

public class Share {
    private int headImgId;//头像ID

    private String name;//昵称

    private String time;//发布时间

    private String textcontent;//内容

    private int conImgId;//内容图片ID

    public Share(int headImgId, String name, String time, String textcontent, int conImgId){
        this.headImgId = headImgId;
        this.name = name;
        this.time = time;
        this.textcontent = textcontent;
        this.conImgId = conImgId;
    }

    public int getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(int headImgId) {
        this.headImgId = headImgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTextcontent() {
        return textcontent;
    }

    public void setTextcontent(String textcontent) {
        this.textcontent = textcontent;
    }

    public int getConImgId() {
        return conImgId;
    }

    public void setConImgId(int conImgId) {
        this.conImgId = conImgId;
    }
}
